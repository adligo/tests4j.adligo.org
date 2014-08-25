package org.adligo.tests4j.run.helpers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.adligo.tests4j.models.shared.common.I_TrialType;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageDelegator;
import org.adligo.tests4j.models.shared.metadata.I_TestMetadata;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.SourceInfoMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TestMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadataMutant;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_ProcessInfo;
import org.adligo.tests4j.models.shared.system.Tests4J_ListenerDelegator;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.run.discovery.PackageDiscovery;
import org.adligo.tests4j.run.discovery.TestDescription;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.run.discovery.TrialQueueDecisionTree;
import org.adligo.tests4j.run.discovery.TrialState;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * This class handles event notification
 * to the I_TrailRunListener.
 * 
 * @author scott
 *
 */
public class Tests4J_NotificationManager implements I_Tests4J_NotificationManager {
	private AtomicBoolean doneDescribeingTrials = new AtomicBoolean(false);
	private AtomicBoolean doneRunningTrials = new AtomicBoolean(false);
	private Tests4J_Memory memory;
	private I_Tests4J_ThreadManager threadManager;
	/**
	 * always call reporter first then call listener
	 */
	private I_Tests4J_Log log;
	/**
	 * always call reporter first then call listener
	 */
	private Tests4J_ListenerDelegator listener;
	private I_Tests4J_Listener reporter;
	
	private final AtomicLong startTime = new AtomicLong();
	private final AtomicLong assertionCount = new AtomicLong();
	private final AtomicLong uniqueAssertionCount = new AtomicLong();
	private final AtomicInteger testCount = new AtomicInteger();
	private final AtomicInteger testFailureCount = new AtomicInteger();
	private final AtomicInteger trialFailures = new AtomicInteger();
	private final AtomicInteger trials = new AtomicInteger();
	private final AtomicInteger trialClassDefFailures = new AtomicInteger();
	private Set<String> trialPackageNames = new CopyOnWriteArraySet<String>();
	private Set<String> passingTrialNames = new CopyOnWriteArraySet<String>();
	private final AtomicBoolean running = new AtomicBoolean(true);
	private final AtomicBoolean ranMetaTrial = new AtomicBoolean(false);
	private final AtomicBoolean describeTrialError = new AtomicBoolean(false);
	private TrialQueueDecisionTree trialQueueDecisionTree;
	
	private volatile I_TrialRunMetadata metadata = null;
	private MetaTrialProcessor metaProcessor;
	
	public Tests4J_NotificationManager(Tests4J_Memory pMem) {
		memory = pMem;
		trialQueueDecisionTree = pMem.getTrialQueueDecisionTree();
		log = pMem.getLog();
		threadManager = pMem.getThreadManager();
		listener = pMem.getListener();
		reporter = pMem.getReporter();
		
		long now = System.currentTimeMillis();
		startTime.set(now);
		metaProcessor = new MetaTrialProcessor(memory, this);
	}
	


	/**
	 * @diagram_sync on 8/20/2014 with Overview.seq 
	 */
	public void onSetupDone() {
		doneDescribeingTrials.set(true);
		
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("onTrialDefinitionsDone()");
		}
		sendMetadata();
		
		I_Tests4J_CoveragePlugin plugin =  memory.getCoveragePlugin();
		if (plugin != null) {
			plugin.instrumentationComplete();
		}
		int defFailures = memory.getFailureResultsSize();
		trialClassDefFailures.set(defFailures);
		
		Collection<TrialState> states = trialQueueDecisionTree.getAllStates();
		Iterator<TrialState> it = states.iterator();
		while (it.hasNext()) {
			TrialState state =  it.next();
			TrialDescription desc = state.getTrialDescription();
			
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


	private void sendMetadata() {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("sendingMetadata. " + trialQueueDecisionTree.getTrialCount() + 
					" TrialDescription ");
		}
		Collection<TrialState> states = trialQueueDecisionTree.getAllStates();
		Iterator<TrialState> it = states.iterator();
		
		TrialRunMetadataMutant trmm = new TrialRunMetadataMutant();
		
		Set<String> packages = new HashSet<String>();
		
		int totalTests = 0;
		while (it.hasNext()) {
			TrialState state =  it.next();
			TrialDescription td = state.getTrialDescription();
			String packageName = td.getPackageName();
			if (!StringMethods.isEmpty(packageName)) {
				packages.add(packageName);
			}
			TrialMetadataMutant tmm = new TrialMetadataMutant();
			tmm.setTrialName(td.getTrialName());
			Method before = td.getBeforeTrialMethod();
			if (before != null) {
				tmm.setBeforeTrialMethodName(before.getName());
			}
			boolean ignored = td.isIgnored();
			tmm.setIgnored(ignored);
			long timeout = td.getTimeout();
			tmm.setTimeout(timeout);
			
			I_TrialType type = td.getType();
			tmm.setType(type);

			TrialType tt = TrialType.get(type);
			switch (tt) {
				case SourceFileTrial:
					Class<?> clazz = td.getSourceFileClass();
					if (clazz != null) {
						tmm.setTestedSourceFile(clazz.getName());
					}
					break;
				case ApiTrial:
						tmm.setTestedPackage(td.getPackageName());
					break;
				case UseCaseTrial:
						tmm.setSystem(td.getSystemName());
						tmm.setUseCase(td.getUseCase());
					break;
				default:
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
					
					
					switch (tt) {
						case SourceFileTrial:
							try {
								Method m = trialClass.getDeclaredMethod("afterTrialTests", I_SourceFileTrialResult.class);
								TestMetadataMutant testMeta = new TestMetadataMutant();
								testMeta.setTestName(m.getName());
								testMetas.add(testMeta);
							} catch (NoSuchMethodException e) {
								//do noting
							}
								
							break;
						case ApiTrial:
							try {
								
								Method m = trialClass.getDeclaredMethod("afterTrialTests", I_ApiTrialResult.class);
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
							
							break;
						default:
							break;
					}
					tmm.setTests(testMetas);
					if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
						log.log("sendingMetadata. " + trialClass.getName()
								+ "  has " + testMetas.size() + " tests.");
						totalTests = totalTests + testMetas.size();
					}
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
				PackageDiscovery classDiscovery = new PackageDiscovery(packageName);
				addClasses(trmm, classDiscovery);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		metadata = new TrialRunMetadata(trmm);
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("sendingMetadata. " + trialQueueDecisionTree.getTrialCount() 
					+ " TrialDescription " + metadata.getAllTestsCount() + " tests."
					+ "\n total tests " + totalTests);
		}
		// @diagram_sync on 5/26/2014 with Overview.seq
		memory.setMetaTrialData(metadata);
		listener.onMetadataCalculated(metadata);
		reporter.onMetadataCalculated(metadata);
	}

	private void addClasses(TrialRunMetadataMutant trmm,
			PackageDiscovery classDiscovery) {
		List<String> classes = classDiscovery.getClassNames();
		for (String clazz: classes) {
			if (clazz.indexOf("$") == -1) {
				SourceInfoMetadataMutant sim = new SourceInfoMetadataMutant();
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
					log.onThrowable(x);
				}
				trmm.setSourceInfo(clazz, sim);
			}
		}
		List<PackageDiscovery> subs =  classDiscovery.getSubPackages();
		for (PackageDiscovery sub: subs) {
			addClasses(trmm, sub);
		}
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager#startingTrial(java.lang.String)
	 */
	@Override
	public void startingTrial(String name) {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("startingTrial " + name);
		}
		listener.onStartingTrial(name);
		reporter.onStartingTrial(name);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager#startingTest(java.lang.String, java.lang.String)
	 */
	@Override
	public void startingTest(String trialName, String testName) {
		listener.onStartingTest(trialName, testName);
		reporter.onStartingTest(trialName, testName);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager#onTestCompleted(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void onTestCompleted(String trialName, String testName, boolean passed) {
		listener.onTestCompleted(trialName, testName, passed);
		reporter.onTestCompleted(trialName, testName, passed);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager#onTrialCompleted(org.adligo.tests4j.models.shared.results.I_TrialResult)
	 */
	@Override
	public void onTrialCompleted(I_TrialResult result) {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("trialFinished " + result.getName() + " " + result.isPassed());
		}
		onTrialCompetedInternal(result);
	}

	/**
	 * @diagram sync on 5/8/201 with  Overview.seq
	 * 
	 * @param result
	 */

	private void onTrialCompetedInternal(I_TrialResult result) {
		listener.onTrialCompleted(result);
		reporter.onTrialCompleted(result);
		
		incrementTrialResultCounts(result);
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("trialFinished " + result.getName() + " " + trials.get() + 
					" trials completed " + testCount.get() + " tests completed.");
		}
		
	}

	private synchronized void incrementTrialResultCounts(I_TrialResult result) {
		assertionCount.addAndGet(result.getAssertionCount());
		uniqueAssertionCount.addAndGet(result.getUniqueAssertionCount());
		testCount.addAndGet(result.getTestCount());
		testFailureCount.addAndGet(result.getTestFailureCount());
		if (!result.isPassed()) {
			trialFailures.getAndIncrement();
		} else {
			String name = result.getName();
			int bracketIndex = name.indexOf("[");
			if (bracketIndex != -1) {
				name = name .substring(0, bracketIndex);
			}
			passingTrialNames.add(name);
		}
		trials.getAndIncrement();
	}


	/**
	 * All the trials are finished running,
	 * so notify the listener and cleanup.
	 * 
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 */
	public synchronized void onDoneRunningNonMetaTrials() {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("onDoneRunningNonMetaTrials()");
		}
		TrialRunResultMutant runResult = new TrialRunResultMutant();
		runResult.setStartTime(startTime.get());
		runResult.setAsserts(assertionCount.get());
		runResult.setUniqueAsserts(uniqueAssertionCount.get());
		runResult.setTestFailures(testFailureCount.get());
		runResult.setTests(testCount.get());
		runResult.setTrialFailures(trialFailures.get());
		runResult.setTrials(trials.get());
		runResult.setPassingTrials(passingTrialNames);
		
		stopRecordingTrialsRun(runResult);
		
		

	
		//@diagram_sync TODO
		if (memory.hasMetaTrial()) {
			synchronized (metaProcessor) {
				if (!memory.hasRanMetaTrial()) {
					memory.setRanMetaTrial();
					boolean called = false;
					
					TrialState ts = trialQueueDecisionTree.getMetaTrialState();
					TrialDescription desc = ts.getTrialDescription();
					if (desc != null) {
						Class<? extends I_AbstractTrial> trialClass = desc.getTrialClass();
						synchronized (trialClass) {
							synchronized (ranMetaTrial) {
								
								if (!ranMetaTrial.get()) {
									ranMetaTrial.set(true);
									I_AbstractTrial trial = (I_AbstractTrial) desc.getTrial();
									if (trial instanceof I_MetaTrial) {
										if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
											log.log("calling MetaTrailProcessor.runMetaTrialMethods");
										}
										I_TrialResult result = metaProcessor.runMetaTrialMethods(
												(I_MetaTrial) trial, new TrialRunResult(runResult));
										if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
											log.log("called MetaTrailProcessor.runMetaTrialMethods");
										}
										called = true;
										
										runResult.setTrials(trials.get() + 1);
										if (result.isPassed()) {
											passingTrialNames.add(trial.getClass().getName());
										} else {
											runResult.setTrialFailures(trialFailures.get() + 1);
										}
										runResult.setAsserts(assertionCount.get() + result.getAssertionCount());
										runResult.setUniqueAsserts(uniqueAssertionCount.get() + result.getUniqueAssertionCount());
										runResult.setTestFailures(testFailureCount.get() + result.getTestFailureCount());
										runResult.setTests(testCount.get() + result.getTestCount());
									}
								}
							}
						}
					} 
					if (!called) {
						log.onThrowable(new Throwable("Some issue with the meta trial which wasn't called."));
					}
				}
			}
		} 
		
		long end = System.currentTimeMillis();
		runResult.setRunTime(end - runResult.getStartTime());
		
		onAllTrialsDone(new TrialRunResult(runResult));
		
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager#onAllTrialsDone(org.adligo.tests4j.models.shared.results.I_TrialRunResult)
	 */
	private void onAllTrialsDone(I_TrialRunResult p) {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("onAllTrialsDone(I_TrialRunResult p)");
		}
		if (log.isMainLog()) {
			if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
				log.log("Exiting JVM after this last notification.");
			}
		}
		TrialRunResult endResult = new TrialRunResult(p);
		
		long now = memory.getTime();
		memory.setSetupEndTime(now);
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			double millis = now - memory.getStartTime();
			double secs = millis/1000.0;
			log.log("Finised trials after " + secs + " seconds.");
		}
		
		listener.onRunCompleted(endResult);
		reporter.onRunCompleted(endResult);
		
	}

	/**
	 * @diagram_sync on 5/8/2014 to Overview.seq 
	 * @param runResult
	 */
	private void stopRecordingTrialsRun(TrialRunResultMutant runResult) {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("stopRecordingTrialsRun(TrialRunResultMutant runResult)");
		}
		I_Tests4J_CoverageRecorder allCoverageRecorder = memory.getMainRecorder();
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
		runResult.setPassingTrials(passingTrialNames);
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("stopRecordingTrialsRun(TrialRunResultMutant runResult) finished");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager#onDescibeTrialError()
	 */
	@Override
	public synchronized void onDescibeTrialError() {
		describeTrialError.set(true);
		doneDescribeingTrials.set(true);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return running.get();
	}
	
	public boolean isDoneDescribeingTrials() {
		return doneDescribeingTrials.get();
	}

	@Override
	public void onProgress(I_Tests4J_ProcessInfo info) {
		if (info.getPercentDone() <= 100.0) {
			if (!doneRunningTrials.get()) {
				listener.onProgress(info);
				reporter.onProgress(info);
			}
			
		}
	}

	public boolean isDoneRunningTrials() {
		return doneRunningTrials.get();
	}

	public boolean hasDescribeTrialError() {
		return describeTrialError.get();
	}

	@Override
	public void onProcessStateChange(I_Tests4J_ProcessInfo info) {
		listener.onProccessStateChange(info);
		reporter.onProccessStateChange(info);
	}
}
