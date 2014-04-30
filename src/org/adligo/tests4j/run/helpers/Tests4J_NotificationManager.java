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
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.console.TextReporter;

/**
 * this class handles event notification
 * @author scott
 *
 */
public class Tests4J_NotificationManager {
	private AtomicBoolean doneDescribeingTrials = new AtomicBoolean(false);
	private Tests4J_Memory memory;
	private I_Tests4J_Logger log;
	private I_TrialRunListener listener;
	private TextReporter reporter;
	private AtomicLong startTime = new AtomicLong();
	private AtomicLong assertionCount = new AtomicLong();
	private AtomicLong uniqueAssertionCount = new AtomicLong();
	private AtomicLong testCount = new AtomicLong();
	private AtomicLong testFailureCount = new AtomicLong();
	private AtomicInteger trialFailures = new AtomicInteger();
	private AtomicInteger trials = new AtomicInteger();
	
	public Tests4J_NotificationManager(Tests4J_Memory pMem, I_Tests4J_Logger pLog, I_TrialRunListener pListener) {
		memory = pMem;
		log = pLog;
		listener = pListener;
		reporter = new TextReporter(log);
		long now = System.currentTimeMillis();
		startTime.set(now);
	}
	
	/**
	 * in diagram Overview.seq
	 */
	public synchronized void checkDoneDescribingTrials() {
		if (log.isEnabled()) {
			log.log("checking if done describing trials.");
		}
		synchronized (doneDescribeingTrials) {
			if (doneDescribeingTrials.get()) {
				if (log.isEnabled()) {
					log.log("done describing trials.");
				}
				return;
			}
			int trialDescriptions = memory.getDescriptionCount();
			int trialCount = memory.getTrialCount();
			
			if (trialCount == trialDescriptions) {
				onTrialDefinitionsDone();
			}
		}
		
	}

	/**
	 * in diagram Overview.seq
	 */
	private void onTrialDefinitionsDone() {
		synchronized (doneDescribeingTrials) {
			if (log.isEnabled()) {
				logPrivate("DescribingTrials is Done.");
			}
			doneDescribeingTrials.set(true);
			
			if (listener != null) {
				sendMetadata();
			}
			
			int classDefFailures = memory.getFailureResultsSize();
			if (classDefFailures >= 1) {
				I_TrialResult result = memory.pollFailureResults();
				while (result != null) {
					trialDoneInternal(result);
					result = memory.pollFailureResults();
				}
			}
		}
		
		
	}


	/**
	 * in diagram Overview.seq
	 */
	public void sendMetadata() {
		synchronized (listener) {
			if (log.isEnabled()) {
				log.log("sendingMetadata. " + memory.getDescriptionCount());
			}
			Iterator<TrialDescription> it = memory.getAllDescriptions();
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
						if (log.isEnabled()) {
							log.log("sendingMetadata trial " +tmm.getTrialName() + 
									" has " + testMetas.size() + " tests.");
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
			
			listener.onMetadataCalculated(toSend);
		}
	}

	public synchronized void startingTrial(String name) {
		if (log.isEnabled()) {
			logPrivate("startingTrial " + name);
		}
	}
	
	public synchronized void startingTest(String name) {
		if (log.isEnabled()) {
			logPrivate("startingTest " + name);
		}
	}
	
	public synchronized void trialDone(I_TrialResult result) {
		trialDoneInternal(result);
	}
	
	public void trialDoneInternal(I_TrialResult result) {
		if (log.isEnabled()) {
			reporter.printTestCompleted(result);
		}
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
	
	public synchronized void checkDoneRunningTrials() {
		int trialsWhichCanRun = memory.getRunnableTrialDescriptions();
		
		if (trials.get() == trialsWhichCanRun) {
			onDoneRunningTrials();
		}
	}

	private void onDoneRunningTrials() {
		if (log.isEnabled()) {
			logPrivate("DoneRunningTrials.");
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
			List<I_PackageCoverage> packageCoverage = allCoverageRecorder.getCoverage();
			runResult.setCoverage(packageCoverage);
		}
		
		long end = System.currentTimeMillis();
		runResult.setRunTime(end - runResult.getStartTime());
		
		TrialRunResult endResult = new TrialRunResult(runResult);
		if (listener != null) {
			listener.onRunCompleted(endResult);
		}
		if (log.isEnabled()) {
			reporter.onRunCompleted(endResult);
		}
		
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
	
	public boolean isLogEnabled() {
		return log.isEnabled();
	}
	
	public synchronized void log(String p) {
		logPrivate(p);
	}
	
	private void logPrivate(String p) {
		log.log(p);
	}
}
