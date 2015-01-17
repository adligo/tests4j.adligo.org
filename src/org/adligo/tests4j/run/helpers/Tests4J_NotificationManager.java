package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageBriefDelegator;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.SourceInfoMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TestMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadataMutant;
import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.run.common.AtomicBigInteger;
import org.adligo.tests4j.run.common.I_ThreadManager;
import org.adligo.tests4j.run.discovery.I_PackageDiscovery;
import org.adligo.tests4j.run.discovery.PackageDiscovery;
import org.adligo.tests4j.run.discovery.TestDescription;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.run.discovery.TrialQueueDecisionTree;
import org.adligo.tests4j.run.discovery.TrialState;
import org.adligo.tests4j.run.memory.Tests4J_Memory;
import org.adligo.tests4j.shared.asserts.reference.I_ReferenceGroup;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Listener;
import org.adligo.tests4j.system.shared.api.I_Tests4J_SourceInfoParams;
import org.adligo.tests4j.system.shared.api.Tests4J_ListenerDelegator;
import org.adligo.tests4j.system.shared.trials.ApiTrial;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_SourceFileTrial;
import org.adligo.tests4j.system.shared.trials.MetaTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileTrial;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class handles event notification
 * to the I_TrailRunListener.
 * 
 * @diagram_sync on 1/8/2015 with Overview.seq
 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
 * @author scott
 *
 */
public class Tests4J_NotificationManager implements I_Tests4J_NotificationManager {
	private AtomicBoolean doneDescribeingTrials = new AtomicBoolean(false);
	private AtomicBoolean doneRunningTrials = new AtomicBoolean(false);
	private Tests4J_Memory memory;
	private I_ThreadManager threadManager;
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
	private final AtomicBigInteger assertionCount = new AtomicBigInteger();
	private final AtomicBigInteger uniqueAssertionCount = new AtomicBigInteger();
	private final AtomicLong testCount = new AtomicLong();
	private final AtomicLong testFailureCount = new AtomicLong();
	private final AtomicLong testIgnoreCount = new AtomicLong();
	
	private final AtomicInteger trialFailures = new AtomicInteger();
	private final AtomicInteger trials = new AtomicInteger();
	private final AtomicInteger trialClassDefFailures = new AtomicInteger();
	private Set<String> trialPackageNames = new CopyOnWriteArraySet<String>();
	private Set<String> passingTrialNames = new CopyOnWriteArraySet<String>();
	private final AtomicBoolean running = new AtomicBoolean(true);
	private final AtomicBoolean ranMetaTrial = new AtomicBoolean(false);
	private final AtomicBoolean describeTrialError = new AtomicBoolean(false);
	private TrialQueueDecisionTree trialQueueDecisionTree;
	private Set<String> testedClasses_ = new TreeSet<String>();
	
	private volatile I_TrialRunMetadata metadata = null;
	private MetaTrialProcessor metaProcessor;
	
	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq 
	 * @param pMem
	 */
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
	 * @diagram_sync on 1/8/2014 with Overview.seq 
	 */
	public synchronized void onSetupDone() {
		doneDescribeingTrials.set(true);
		
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("onTrialDefinitionsDone()");
		}
		//@diagram_sync on 1/8/2014 with Overview.seq
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
		
		Set<String> packages_ = new HashSet<String>();
		I_Tests4J_SourceInfoParams siParams = memory.getSourceInfoParams();
		packages_.addAll(siParams.getPackagesTested());
		
		int totalTests = 0;
		while (it.hasNext()) {
			TrialState state =  it.next();
			TrialDescription td = state.getTrialDescription();
			String packageName = td.getPackageName();
			if (!StringMethods.isEmpty(packageName)) {
				packages_.add(packageName);
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
				case MetaTrial:
					TestMetadataMutant metaCalc = new TestMetadataMutant();
					metaCalc.setTestName(MetaTrial.AFTER_META_CALC);
					tmm.addTest(metaCalc);
					
					TestMetadataMutant metaResults = new TestMetadataMutant();
					metaResults.setTestName(MetaTrial.AFTER_NON_META_RESULTS);
					tmm.addTest(metaResults);
					break;
				case SourceFileTrial:
					Class<?> clazz = td.getSourceFileClass();
					if (clazz != null) {
						tmm.setTestedSourceFile(clazz.getName());
					}
					if (td.getAfterTrialTestsMethod() != null) {
						TestMetadataMutant attm = new TestMetadataMutant();
						attm.setTestName(SourceFileTrial.AFTER_TRIAL_TESTS);
						tmm.addTest(attm);
					}
					if (memory.hasCoveragePlugin()) {
						TestMetadataMutant tscMin = new TestMetadataMutant();
						tscMin.setTestName(I_SourceFileTrial.TEST_MIN_COVERAGE);
						tmm.addTest(tscMin);
						
						TestMetadataMutant tscDep = new TestMetadataMutant();
						tscDep.setTestName(I_SourceFileTrial.TEST_DEPENDENCIES);
						tmm.addTest(tscDep);
						
						I_ReferenceGroup rg = td.getReferenceGroupAggregate();
						if (rg != null) {
							TestMetadataMutant tmmr = new TestMetadataMutant();
							tmmr.setTestName(I_SourceFileTrial.TEST_REFERENCES);
							tmm.addTest(tmmr);
						}
					}
					break;
				case ApiTrial:
						tmm.setTestedPackage(td.getPackageName());
						if (td.getAfterTrialTestsMethod() != null) {
							TestMetadataMutant attm = new TestMetadataMutant();
							attm.setTestName(ApiTrial.AFTER_TRIAL_TESTS);
							tmm.addTest(attm);
						}
					break;
				default:
				  //UseCaseTrial
					break;
			}
			Iterator<TestDescription> atTests =  td.getTestMethods();
			while (atTests.hasNext()) {
				TestMetadataMutant tscDep = new TestMetadataMutant();
				TestDescription testDesc = atTests.next();
				
				Method tm = testDesc.getMethod();
				tscDep.setTestName(tm.getName());
				
				tscDep.setIgnored(testDesc.isIgnore());
				tscDep.setTimeout(testDesc.getTimeoutMillis());
				
				tmm.addTest(tscDep);
			}
			
			Method after = td.getAfterTrialMethod();
			if (after != null) {
				tmm.setBeforeTrialMethodName(after.getName());
			}
			trmm.addTrial(tmm);
		}
		for (String packageName: packages_) {
			try {
				I_PackageDiscovery classDiscovery = new PackageDiscovery(packageName);
				addClasses(trmm, classDiscovery, siParams);
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
		// @diagram_sync on 1/8/2015 with Coverage_Overview.seq
		memory.setTrialMetadata(metadata);
 	  // @diagram_sync on 1/8/2015 with Coverage_Overview.seq
		listener.onMetadataCalculated(metadata);
		reporter.onMetadataCalculated(metadata);
	}

	private void addClasses(TrialRunMetadataMutant trmm,
			I_PackageDiscovery classDiscovery, I_Tests4J_SourceInfoParams siParams) {
		List<String> classes = classDiscovery.getClassNames();
		
		for (String clazz: classes) {
			if (siParams.isTestable(clazz)) {
				if (clazz.indexOf("$") == -1) {
					testedClasses_.add(clazz);
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
		}
		List<I_PackageDiscovery> subs =  classDiscovery.getSubPackages();
		for (I_PackageDiscovery sub: subs) {
			addClasses(trmm, sub, siParams);
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
		int asserts = result.getAssertionCount();
		int uAsserts = result.getUniqueAssertionCount();
		int tests = result.getTestCount();
		
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log(this.toString() + " increment counts for;" +
					log.lineSeparator() + "" + result.getName() +
					log.lineSeparator() + "tests=" + tests + ", asserts=" + asserts +
					", uniqueAsserts=" + uAsserts);
		}
		assertionCount.addAndGet(asserts);
		uniqueAssertionCount.addAndGet(uAsserts);
		testCount.addAndGet(tests);
		testFailureCount.addAndGet(result.getTestFailureCount());
		testIgnoreCount.addAndGet(result.getTestIgnoredCount());
		
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
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 */
	public synchronized void onDoneRunningNonMetaTrials() {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("onDoneRunningNonMetaTrials()");
		}
		TrialRunResultMutant runResult = new TrialRunResultMutant();
		runResult.setStartTime(startTime.get());
		runResult.setAsserts(assertionCount.get());
		runResult.setUniqueAsserts(uniqueAssertionCount.get());
		
		runResult.setTestsFailed(testFailureCount.get());
		runResult.setTestsIgnored(testIgnoreCount.get());
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
									  //@diagram_sync on 1/8/2015 with Overview.seq
									  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
										called = runMetaTrial(runResult, trial);
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


  /**
   * Run the meta trial
   * @diagram_sync on 1/8/2015 with Overview.seq
   * @param runResult
   * @param trial
   * @return
   */
  private boolean runMetaTrial(TrialRunResultMutant runResult, I_AbstractTrial trial) {
    boolean called;
    if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
    	log.log("calling MetaTrailProcessor.runMetaTrialMethods");
    }
    I_TrialResult result = metaProcessor.runMetaTrialMethods(
    		(I_MetaTrial) trial, new TrialRunResult(runResult));
    if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
    	log.log("called MetaTrailProcessor.runMetaTrialMethods");
    }
    called = true;
    
    runResult.setTrials(trials.get());
    if (result.isPassed()) {
    	passingTrialNames.add(trial.getClass().getName());
    } else {
    	runResult.setTrialFailures(trialFailures.get());
    }
    runResult.setAsserts(assertionCount.get());
    runResult.setUniqueAsserts(uniqueAssertionCount.get());
    runResult.setTestsFailed(testFailureCount.get());
    runResult.setTests(testCount.get());
    return called;
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
	 * @param runResult
	 */
	private void stopRecordingTrialsRun(TrialRunResultMutant runResult) {
		if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
			log.log("stopRecordingTrialsRun(TrialRunResultMutant runResult)");
		}
		//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
		I_Tests4J_CoverageRecorder allCoverageRecorder = memory.getMainRecorder();
		if (allCoverageRecorder != null) {
			//
		  if (log.isLogEnabled(Tests4J_NotificationManager.class)) {
  		  if (log.isMainLog()) {
          log.log("There are " + testedClasses_.size() + " tested classes.");
        }
		  }
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			List<I_PackageCoverageBrief> packageCoverage = allCoverageRecorder.getAllCoverage(trialPackageNames);
			runResult.setCoverage(packageCoverage);
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

	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	@Override
	public void onProgress(I_PhaseState info) {
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
	public void onProcessStateChange(I_PhaseState info) {
		listener.onProccessStateChange(info);
		reporter.onProccessStateChange(info);
	}
}
