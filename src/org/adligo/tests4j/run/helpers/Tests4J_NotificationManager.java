package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.metadata.I_TestMetadata;
import org.adligo.tests4j.models.shared.metadata.TestMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadataMutant;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.console.TextReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * This class handles event notification
 * to the I_TrailRunListener.
 * 
 * @author scott
 *
 */
public class Tests4J_NotificationManager {
	private AtomicBoolean doneDescribeingTrials = new AtomicBoolean(false);
	private Tests4J_Memory memory;
	private I_Tests4J_Reporter reporter;
	private I_TrialRunListener listener;
	private AtomicLong startTime = new AtomicLong();
	private AtomicLong assertionCount = new AtomicLong();
	private AtomicLong uniqueAssertionCount = new AtomicLong();
	private AtomicLong testCount = new AtomicLong();
	private AtomicLong testFailureCount = new AtomicLong();
	private AtomicInteger trialFailures = new AtomicInteger();
	private AtomicInteger trials = new AtomicInteger();
	
	public Tests4J_NotificationManager(Tests4J_Memory pMem, I_Tests4J_Reporter pLog, I_TrialRunListener pListener) {
		memory = pMem;
		reporter = pLog;
		listener = pListener;
		long now = System.currentTimeMillis();
		startTime.set(now);
	}
	
	/**
	 * in diagram Overview.seq
	 */
	public void checkDoneDescribingTrials() {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class.getName())) {
			reporter.log("checking if done describing trials.");
		}
		synchronized (doneDescribeingTrials) {
			if (doneDescribeingTrials.get()) {
				if (reporter.isLogEnabled(Tests4J_NotificationManager.class.getName())) {
					reporter.log("done describing trials.");
				}
				return;
			}
			int trialDescriptions = memory.getDescriptionCount();
			int trialCount = memory.getTrialCount();
			
			if (trialCount == trialDescriptions) {
				if (reporter.isLogEnabled(Tests4J_NotificationManager.class.getName())) {
					reporter.log("DescribingTrials is Done calling onTrialDefinitionsDone.");
				}
				doneDescribeingTrials.set(true);
				onTrialDefinitionsDone();
			}
		}
		
	}

	/**
	 * in diagram Overview.seq
	 */
	private void onTrialDefinitionsDone() {
		sendMetadata();
		
		int classDefFailures = memory.getFailureResultsSize();
		if (classDefFailures >= 1) {
			I_TrialResult result = memory.pollFailureResults();
			while (result != null) {
				trialDoneInternal(result);
				result = memory.pollFailureResults();
			}
		}
	}


	/**
	 * in diagram Overview.seq
	 */
	public void sendMetadata() {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class.getName())) {
			reporter.log("sendingMetadata. " + memory.getDescriptionCount());
		}
		Iterator<TrialDescription> it = memory.getAllTrialDescriptions();
		TrialRunMetadataMutant trmm = new TrialRunMetadataMutant();
		
		while (it.hasNext()) {
			TrialDescription td = it.next();
			TrialMetadataMutant tmm = new TrialMetadataMutant();
			tmm.setTrialName(td.getTrialName());
			Method before = td.getBeforeTrialMethod();
			if (before != null) {
				tmm.setBeforeTrialMethodName(before.getName());
			}
			boolean ignored = td.isIgnored();
			tmm.setSkipped(ignored);
			long timeout = td.getTimeout();
			tmm.setTimeout(timeout);
			
			if (td.getTestMethodsSize() >= 1) {
				Iterator<TestDescription> iit = td.getTestMethods();
				
				if (iit != null) {
					List<I_TestMetadata> testMetas = new ArrayList<I_TestMetadata>();
					while (iit.hasNext()) {
						TestDescription tm = iit.next();
						TestMetadataMutant testMeta = new TestMetadataMutant();
						Method method = tm.getMethod();
						testMeta.setTestName(method.getName());
						long testTimeout = tm.getTimeoutMillis();
						testMeta.setTimeout(testTimeout);
						testMetas.add(testMeta);
					}
					tmm.setTests(testMetas);
				}
			}
			Method after = td.getAfterTrialMethod();
			if (after != null) {
				tmm.setBeforeTrialMethodName(after.getName());
			}
			trmm.addTrial(tmm);
		}
		TrialRunMetadata toSend = new TrialRunMetadata(trmm);
		
		reporter.onMetadataCalculated(toSend);
		if (listener != null) {
			listener.onMetadataCalculated(toSend);
		}
	}

	public void startingTrial(String name) {
		reporter.onStartingTrail(name);
		if (listener != null) {
			listener.onStartingTrail(name);
		}
	}
	
	public void startingTest(String trialName, String testName) {
		reporter.onStartingTest(trialName, testName);
		if (listener != null) {
			listener.onStartingTest(trialName, testName);
		}
	}
	
	public void onTestCompleted(String trialName, String testName, boolean passed) {
		reporter.onTestCompleted(trialName, testName, passed);
		if (listener != null) {
			listener.onTestCompleted(trialName, testName, passed);
		}
	}
	
	/**
	 * diagrammed in Overview.seq
	 * 
	 * @param result
	 */
	public void trialDone(I_TrialResult result) {
		trialDoneInternal(result);
	}

	/**
	 * diagrammed in Overview.seq
	 * 
	 * @param result
	 */

	public void trialDoneInternal(I_TrialResult result) {
		reporter.onTrialCompleted(result);
		if (listener != null) {
			listener.onTrialCompleted(result);
		}
		assertionCount.addAndGet(result.getAssertionCount());
		uniqueAssertionCount.addAndGet(result.getUniqueAssertionCount());
		testCount.addAndGet(result.getTestCount());
		testFailureCount.addAndGet(result.getTestFailureCount());
		if (!result.isPassed()) {
			trialFailures.addAndGet(1);
		}
		trials.addAndGet(1);
	}
	
	/**
	 * Check to see if all of the trials are done running
	 * by comparing the trialsWhichCanRun 
	 * from the memory's RunnableTrialDescriptions 
	 * and the count of trials from the trialDone method calls.
	 * 
	 * diagrammed in Overview.seq
	 */
	public void checkDoneRunningTrials() {
		int trialsWhichCanRun = memory.getRunnableTrialDescriptions();
		
		if (trials.get() == trialsWhichCanRun) {
			onDoneRunningTrials();
		}
	}

	/**
	 * All the trials are finished running,
	 * so notify the listener and cleanup.
	 * 
	 * diagrammed in Overview.seq
	 */
	private void onDoneRunningTrials() {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class.getName())) {
			reporter.log("DoneRunningTrials.");
		}
		TrialRunResultMutant runResult = new TrialRunResultMutant();
		runResult.setStartTime(startTime.get());
		runResult.setAsserts(assertionCount.get());
		runResult.setUniqueAsserts(uniqueAssertionCount.get());
		runResult.setTestFailures(testFailureCount.get());
		runResult.setTests(testCount.get());
		runResult.setTrialFailures(trialFailures.get());
		runResult.setTrials(trials.get());
		
		I_CoverageRecorder allCoverageRecorder = memory.getMainRecorder();
		if (allCoverageRecorder != null) {
			//@diagram Overview.seq sync on 5/1/2014 'stopRecordingTrialsRun'
			allCoverageRecorder.stopRecording();
			List<I_PackageCoverage> packageCoverage = allCoverageRecorder.getCoverage();
			runResult.setCoverage(packageCoverage);
		}
		
		long end = System.currentTimeMillis();
		runResult.setRunTime(end - runResult.getStartTime());
		
		TrialRunResult endResult = new TrialRunResult(runResult);
		if (listener != null) {
			listener.onRunCompleted(endResult);
		}
		reporter.onRunCompleted(endResult);
		
		ExecutorService runService =  memory.getRunService();
		runService.shutdownNow();
		if (memory.isSystemExit()) {
			System.exit(0);
		}
	}
	
	public synchronized void onDescibeTrialError() {
		ExecutorService runService =  memory.getRunService();
		runService.shutdownNow();
		if (memory.isSystemExit()) {
			System.exit(0);
		}
	}
	
	
}
