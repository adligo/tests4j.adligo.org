package org.adligo.tests4j.run.helpers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageDelegator;
import org.adligo.tests4j.models.shared.metadata.I_TestMetadata;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.SourceInfoMutant;
import org.adligo.tests4j.models.shared.metadata.TestMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadataMutant;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.models.shared.results.feedback.I_ApiTrial_TestsResults;
import org.adligo.tests4j.models.shared.results.feedback.I_SourceFileTrial_TestsResults;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.TrialRunListenerDelegate;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;
import org.adligo.tests4j.models.shared.system.report.Tests4jReporterDelegate;
import org.adligo.tests4j.run.discovery.ClassDiscovery;

/**
 * This class handles event notification
 * to the I_TrailRunListener.
 * 
 * @author scott
 *
 */
public class Tests4J_NotificationManager {
	private AtomicBoolean doneDescribeingTrials = new AtomicBoolean(false);
	private AtomicBoolean doneRunningTrials = new AtomicBoolean(false);
	private Tests4J_Memory memory;
	private Tests4J_ThreadManager threadManager;
	/**
	 * always call reporter first then call listener
	 */
	private I_Tests4J_Reporter reporter;
	/**
	 * always call reporter first then call listener
	 */
	private I_TrialRunListener listener;
	private AtomicLong startTime = new AtomicLong();
	private AtomicLong assertionCount = new AtomicLong();
	private AtomicLong uniqueAssertionCount = new AtomicLong();
	private AtomicLong testCount = new AtomicLong();
	private AtomicLong testFailureCount = new AtomicLong();
	private AtomicInteger trialFailures = new AtomicInteger();
	private AtomicInteger trials = new AtomicInteger();
	private AtomicInteger trialClassDefFailures = new AtomicInteger();
	private Set<String> trialPackageNames = new CopyOnWriteArraySet<String>();
	private AtomicBoolean running = new AtomicBoolean(true);
	private volatile I_TrialRunMetadata metadata = null;
	
	public Tests4J_NotificationManager(Tests4J_Memory pMem) {
		memory = pMem;
		threadManager = pMem.getThreadManager();
		reporter = new Tests4jReporterDelegate(pMem.getReporter());
		I_TrialRunListener pListener = pMem.getListener();
		if (pListener != null) {
			listener = new TrialRunListenerDelegate(pListener);
		}
		
		long now = System.currentTimeMillis();
		startTime.set(now);
	}
	
	/**
	 * @digram_sync on 5/26/2014 with Overview.seq
	 */
	public void checkDoneDescribingTrials() {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
			reporter.log("checkDoneDescribingTrials()");
		}
		if (doneDescribeingTrials.get()) {
			if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
				reporter.log("done describing trials.");
			}
			return;
		}
		int trialDescriptions = memory.getDescriptionCount();
		int trialCount = memory.getAllTrialCount();
		
		if (trialCount == trialDescriptions) {
			synchronized (doneDescribeingTrials) {
				if (!doneDescribeingTrials.get()) {
					doneDescribeingTrials.set(true);
					if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
						reporter.log("DescribingTrials is Done calling onTrialDefinitionsDone.");
					}
					onTrialDefinitionsDone();
				}	
			}
		}
		
	}

	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 */
	private void onTrialDefinitionsDone() {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
			reporter.log("onTrialDefinitionsDone()");
		}
		sendMetadata();
		
		int defFailures = memory.getFailureResultsSize();
		trialClassDefFailures.set(defFailures);
		Iterator<TrialDescription> it = memory.getAllTrialDescriptions();
		while (it.hasNext()) {
			TrialDescription desc = it.next();
			
			String name = desc.getTrialName();
			int lastDot = name.lastIndexOf(".");
			String trialPkg = name.substring(0, lastDot);
			trialPackageNames.add(trialPkg);
		}
		if (defFailures >= 1) {
			I_TrialResult result = memory.pollFailureResults();
			while (result != null) {
				onTrialCompetedInternal(result);
				result = memory.pollFailureResults();
			}
		}
	}


	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 */
	public void sendMetadata() {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
			reporter.log("sendingMetadata. " + memory.getDescriptionCount()
					+ " TrialDescription ");
		}
		Iterator<TrialDescription> it = memory.getAllTrialDescriptions();
		TrialRunMetadataMutant trmm = new TrialRunMetadataMutant();
		
		Set<String> packages = new HashSet<String>();
		
		while (it.hasNext()) {
			TrialDescription td = it.next();
			String packageName = td.getPackageName();
			if (!IsEmpty.isEmpty(packageName)) {
				packages.add(packageName);
			}
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
			
			TrialTypeEnum type = td.getType();
			tmm.setType(type);

			switch (type) {
				case SourceFileTrial:
					Class<?> clazz = td.getSourceFileClass();
					if (clazz != null) {
						tmm.setTestedClass(clazz.getName());
					}
					break;
				case ApiTrial:
						tmm.setTestedPackage(td.getPackageName());
					break;
				case UseCaseTrial:
						tmm.setSystem(td.getSystemName());
						tmm.setUseCase(td.getUseCase());
					break;
			}
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
					
					Class<? extends I_AbstractTrial> trialClass = td.getTrialClass();
					switch (type) {
						case SourceFileTrial:
							try {
								Method m = trialClass.getDeclaredMethod("afterTrialTests", I_SourceFileTrial_TestsResults.class);
								TestMetadataMutant testMeta = new TestMetadataMutant();
								testMeta.setTestName(m.getName());
								testMetas.add(testMeta);
							} catch (NoSuchMethodException e) {
								//do noting
							}
								
							break;
						case ApiTrial:
							try {
								
								Method m = trialClass.getDeclaredMethod("afterTrialTests", I_ApiTrial_TestsResults.class);
								TestMetadataMutant testMeta = new TestMetadataMutant();
								testMeta.setTestName(m.getName());
								testMetas.add(testMeta);
								
							} catch (NoSuchMethodException e) {
								//do noting
							}
							break;
						
						case MetaTrial:
							TestMetadataMutant testMeta = new TestMetadataMutant();
							testMeta.setTestName("afterMetadataCalculated(I_TrialRunMetadata metadata)");
							testMetas.add(testMeta);
							
							testMeta = new TestMetadataMutant();
							testMeta.setTestName("afterNonMetaTrialsRun(I_TrialRunResult results)");
							testMetas.add(testMeta);
							
							testMeta = new TestMetadataMutant();
							testMeta.setTestName("testAftersCalled()");
							testMetas.add(testMeta);
							break;
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
		for (String packageName: packages) {
			try {
				ClassDiscovery classDiscovery = new ClassDiscovery(packageName);
				addClasses(trmm, classDiscovery);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		metadata = new TrialRunMetadata(trmm);
		
		// @diagram_sync on 5/26/2014 with Overview.seq
		memory.setMetaTrialData(metadata);
		reporter.onMetadataCalculated(metadata);
		if (listener != null) {
			listener.onMetadataCalculated(metadata);
		}
	}

	private void addClasses(TrialRunMetadataMutant trmm,
			ClassDiscovery classDiscovery) {
		List<String> classes = classDiscovery.getClassNames();
		for (String clazz: classes) {
			if (clazz.indexOf("$") == -1) {
				SourceInfoMutant sim = new SourceInfoMutant();
				sim.setName(clazz);
				try {
					Class<?> czx = Class.forName(clazz);
					//TODO this would be better done with a .java
					//source file parser, but this is good enough for now
					if (czx.isInterface()) {
						sim.setHasInterface(true);
					}
					if (czx.isEnum()) {
						sim.setHasEnum(true);
					}
					sim.setHasClass(true);
				} catch (ClassNotFoundException x) {
					reporter.onError(x);
				}
				trmm.setSourceInfo(clazz, sim);
			}
		}
		List<ClassDiscovery> subs =  classDiscovery.getSubPackages();
		for (ClassDiscovery sub: subs) {
			addClasses(trmm, sub);
		}
	}

	public void startingTrial(String name) {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
			reporter.log("startingTrial " + name);
		}
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
	 * @diagram sync on 5/8/201 with  Overview.seq
	 * 
	 * @param result
	 */
	public void onTrialCompleted(I_TrialResult result) {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
			reporter.log("trialFinished " + result.getName() + " " + result.isPassed());
		}
		onTrialCompetedInternal(result);
	}

	/**
	 * @diagram sync on 5/8/201 with  Overview.seq
	 * 
	 * @param result
	 */

	public void onTrialCompetedInternal(I_TrialResult result) {
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
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
			reporter.log("trialFinished " + result.getName() + " " + trials.get() + 
					" trials completed " + testCount.get() + " tests completed.");
		}
	}

	
	/**
	 * Check to see if all of the I_Trials are done running
	 * by comparing the trialsWhichCanRun 
	 * from the memory's RunnableTrialDescriptions 
	 * and the count of trials from the trialDone method calls.
	 * 
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 * @return a trial run result
	 * 		
	 */
	public TrialRunResultMutant checkDoneRunningNonMetaTrials() {
		if (doneDescribeingTrials.get()) {
			int trialsWhichCanRun = memory.getRunnableTrialDescriptions();
			if (memory.hasMetaTrial()) {
				trialsWhichCanRun --;
			}
			int trialsRan = trials.get();
			int trialClazzFails = trialClassDefFailures.get();
			int ignoredTrials = memory.getIgnoredTrialDescriptions();
			
			if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
				reporter.log("checkDoneRunningNonMetaTrials " + trialsRan + " =? " +
						trialsWhichCanRun + 
						"\n trialClazzFails=" + trialClazzFails + " ignoredTrials=" +
						ignoredTrials);
			}
			if (trialsRan == trialsWhichCanRun + trialClazzFails + ignoredTrials) {
				synchronized (doneRunningTrials) {
					if (!doneRunningTrials.get()) {
						doneRunningTrials.set(true);
						return onDoneRunningNonMetaTrials();
					}
				}
			} else {
				List<TrialInstancesProcessor> tips =  memory.getTrialInstancesProcessors();
				for (TrialInstancesProcessor tip: tips) {
					if (!tip.isFinished()) {
						TrialDescription td =  tip.getTrialDescription();
						if (td != null) {
							if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
								reporter.log("currently working on " + td);
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * All the trials are finished running,
	 * so notify the listener and cleanup.
	 * 
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 */
	private TrialRunResultMutant onDoneRunningNonMetaTrials() {
		if (reporter.isLogEnabled(Tests4J_NotificationManager.class)) {
			reporter.log("onDoneRunningNonMetaTrials()");
		}
		TrialRunResultMutant runResult = new TrialRunResultMutant();
		runResult.setStartTime(startTime.get());
		runResult.setAsserts(assertionCount.get());
		runResult.setUniqueAsserts(uniqueAssertionCount.get());
		runResult.setTestFailures(testFailureCount.get());
		runResult.setTests(testCount.get());
		runResult.setTrialFailures(trialFailures.get());
		runResult.setTrials(trials.get());
		
		stopRecordingTrialsRun(runResult);
		
		long end = System.currentTimeMillis();
		runResult.setRunTime(end - runResult.getStartTime());
		return runResult;
	}
	
	public void onAllTrialsDone(I_TrialRunResult p) {
		TrialRunResult endResult = new TrialRunResult(p);
		reporter.onRunCompleted(endResult);
		if (listener != null) {
			listener.onRunCompleted(endResult);
		}
		threadManager.shutdown();
	}

	/**
	 * @diagram_sync on 5/8/2014 to Overview.seq 
	 * @param runResult
	 */
	private void stopRecordingTrialsRun(TrialRunResultMutant runResult) {
		I_CoverageRecorder allCoverageRecorder = memory.getMainRecorder();
		if (allCoverageRecorder != null) {
			//
			List<I_PackageCoverage> packageCoverage = allCoverageRecorder.endRecording();
			List<I_PackageCoverage> toAdd = new ArrayList<I_PackageCoverage>();
			
			//filter out trial/test code from result
			for (I_PackageCoverage cover: packageCoverage) {
				boolean overlapped = false;
				String coveragePkgName = cover.getPackageName();
				for (String trialPackageName: trialPackageNames) {
					
					if (trialPackageName.contains(coveragePkgName) ||
							coveragePkgName.contains(trialPackageName)) {
						overlapped = true;
						break;
					}
				}
				if (!overlapped) {
					//add it to the results
					toAdd.add(new PackageCoverageDelegator(cover));
				}
			}
			runResult.setCoverage(toAdd);
		}
	}
	
	public synchronized void onDescibeTrialError() {
		threadManager.shutdown();
	}
	
	public boolean isRunning() {
		return running.get();
	}
	
	
}
