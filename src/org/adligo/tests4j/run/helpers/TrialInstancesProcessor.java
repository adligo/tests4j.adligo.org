package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.common.PlatformEnum;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.feedback.ApiTrial_TestsResultsMutant;
import org.adligo.tests4j.models.shared.results.feedback.I_ApiTrial_TestsResults;
import org.adligo.tests4j.models.shared.results.feedback.I_SourceFileTrial_TestsResults;
import org.adligo.tests4j.models.shared.results.feedback.SourceFileTrial_TestsResultsMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_ApiTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_SourceFileTrial;
import org.adligo.tests4j.models.shared.trials.I_TrialProcessorBindings;

public class TrialInstancesProcessor implements Runnable, 
I_TestFinishedListener, I_AssertListener, I_TrialProcessorBindings {
	public static final String AFTER_TRIAL_TESTS = "afterTrialTests";
	private static final String AFTER_API_TRIAL_TESTS_METHOD = 
			"afterTrialTests(I_ApiTrial_TestsResults p)";
	private static final String AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD =
			"afterTrialTests(I_SourceFileTrial_TestsResults p)";
	private static final String AFTER_METADATA_CALCULATED_METHOD = 
			"afterMetadataCalculated(I_TrialRunMetadata p)";
	private static final String AFTER_NON_META_TRIALS_RUN_METHOD = 
			"afterNonMetaTrialsRun(TrialRunResultMutant p)";

	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";
	
	private Tests4J_Memory memory;
	private Tests4J_Manager threadManager;
	private Tests4J_NotificationManager notifier;
	private TrialDescription trialDescription;
	private I_Tests4J_Reporter reporter;
	private I_AbstractTrial trial;
	private BaseTrialResultMutant trialResultMutant;
	
	private ExecutorService testRunService;
	private Future<?> testResultFuture;
	private ArrayBlockingQueue<TestResult> blocking = new ArrayBlockingQueue<TestResult>(1);
	private TestRunable testsRunner;
	private boolean ranAfterTrialTests;
	private boolean hadAfterTrialTests;
	private List<Integer> afterTrialTestsAssertionHashes = new CopyOnWriteArrayList<Integer>(); 
	private TestResultMutant afterTrialTestsResultMutant;
	private boolean finished = false;
	private TrialRunResultMutant runResultMutant;
	private boolean inAfterTrialTests = false;
	private boolean inRunMetaTrialMethods = false;
	private TestResultMutant metaTrialTestResultMutant;
	private Set<Integer> metaTrialAssertionHashes = new HashSet<Integer>();
	private String runMetaTrialMethod;
	private String trialName;
	private I_CoverageRecorder trialCoverageRecorder;
	
	/**
	 * 
	 * @param p
	 * @param pNotificationManager
	 * @param pReporter
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public TrialInstancesProcessor(Tests4J_Memory p, 
			Tests4J_NotificationManager pNotificationManager,
			I_Tests4J_Reporter pReporter) {
		memory = p;
		notifier = pNotificationManager;
		reporter = pReporter;
		threadManager = p.getThreadManager();
		
		testsRunner = new TestRunable(memory, pReporter);
		testsRunner.setListener(this);
		testRunService = threadManager.createNewTestRunService();
	}

	/**
	 * 
	 * 
	 */
	@Override
	public void run() {
		Class<? extends I_AbstractTrial> trialClazz = memory.pollTrialClasses();
		while (trialClazz != null) {
			trialDescription = null;
			try {
				String trialName = trialClazz.getName();
				if (!memory.hasStartedDescribingTrial(trialName)) {
					memory.startDescribingTrial(trialName);
					trialDescription = addTrialDescription(trialClazz);
				} 
				notifier.checkDoneDescribingTrials();
			} catch (Exception x) {
				memory.getReporter().onError(x);
				notifier.onDescibeTrialError();
				return;
			} catch (Error x) {
				memory.getReporter().onError(x);
				notifier.onDescibeTrialError();
				return;
			}
		
			if (trialDescription != null) {
				if (trialDescription.getType() != TrialType.MetaTrial) {
					if (trialDescription.isTrialCanRun()) {
						try {
							runTrial();
						} catch (RejectedExecutionException x) {
							memory.getReporter().onError(x);
						} catch (Exception x) {
							memory.getReporter().onError(x);
						} catch (Error x) {
							memory.getReporter().onError(x);
						} 
					}
				}
			}
			trialClazz = memory.pollTrialClasses();
		}
		runResultMutant = notifier.checkDoneRunningNonMetaTrials();
		if (runResultMutant != null) {
			afterNonMetaTrials();
		}
		
		testRunService.shutdownNow();
		finished = true;
	}



	/**
	 * @diagram sync on 5/8/2014
	 *    for Overview.seq
	 *    
	 * @param trialClazz
	 */
	private TrialDescription addTrialDescription(Class<? extends I_AbstractTrial> trialClazz) {
		
		TrialType type = TrialTypeFinder.getTypeInternal(trialClazz);
		trialCoverageRecorder = startRecordingTrial(trialClazz);
		
		TrialDescription desc = new TrialDescription(trialClazz, memory.getReporter());
		memory.setTrialDescription(trialClazz.getName(), desc);
		memory.add(desc);
		if (!desc.isIgnored()) {
			if (!desc.isTrialCanRun()) {
				BaseTrialResultMutant trm = new BaseTrialResultMutant();
				trm.setTrialName(desc.getTrialName());
				String failureMessage = desc.getResultFailureMessage();
				if (failureMessage != null) {
					TrialFailure tf = new TrialFailure(failureMessage, desc.getResultException());
					trm.setFailure(tf);
				}
				
				trm.setPassed(false);
				trm.setType(type);
				
				switch (type) {
					case UseCaseTrial:
							UseCaseTrialResultMutant mut = new UseCaseTrialResultMutant(trm);
							mut.setSystem(desc.getSystemName());
							mut.setUseCase(desc.getUseCase());
							memory.addResultBeforeMetadata(new UseCaseTrialResult(mut));
						break;
					case ApiTrial:
							ApiTrialResultMutant api = new ApiTrialResultMutant(trm);
							api.setPackageName(desc.getPackageName());
							memory.addResultBeforeMetadata(new ApiTrialResult(api));
						break;
					case SourceFileTrial:
							SourceFileTrialResultMutant src = new SourceFileTrialResultMutant(trm);
							Class<?> clazz = desc.getSourceFileClass();
							if (clazz != null) {
								src.setSourceFileName(clazz.getName());
								memory.addResultBeforeMetadata(new SourceFileTrialResult(src));
								break;
							}
					default:
						memory.addResultBeforeMetadata(new BaseTrialResult(trm));
				}
				
				
			}
		}
		return desc;
	}


	/**
	 * @diagram sync on 5/8/2014
	 *    for Overview.seq
	 *    
	 * @param trialClazz
	 */
	private I_CoverageRecorder startRecordingTrial(
			Class<? extends I_AbstractTrial> trialClazz) {
		I_CoveragePlugin plugin = memory.getPlugin();
		I_CoverageRecorder trialCoverageRecorder = null;
		if (plugin != null) {
			String name = trialClazz.getName();
			trialCoverageRecorder = memory.getRecorder(name);
			if (trialCoverageRecorder == null) {
				//@diagram sync on 7/3/2014
				// for Overview.seq 
				trialCoverageRecorder = plugin.createRecorder(name);
				memory.addRecorder(name, trialCoverageRecorder);
			}
		}
		if (trialCoverageRecorder != null) {
			//trial recording is done on the thread running this TrialInstancesProcessor
			trialCoverageRecorder.startRecording();
		}
		return trialCoverageRecorder;
	}
	
	private void failTrialOnException(String message, Throwable p, TrialType type) {
		trialResultMutant.setPassed(false);
		TrialFailure failure = new TrialFailure(message, p);
		trialResultMutant.setFailure(failure);
		if (type != null) {
			trialResultMutant.setType(type);
		}
	}
	
	private void runTrial() throws RejectedExecutionException  {
		
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("running trial " + trialName);
		}
		afterTrialTestsAssertionHashes.clear();
		afterTrialTestsResultMutant = null;
		
		trialName = trialDescription.getTrialName();
		int id = memory.incrementTrialRun(trialName);
		trialName = trialName + "[" + id + "]";
		notifier.startingTrial(trialName);
		
		
		trial = trialDescription.getTrial();
		trial.setBindings(testsRunner);
		
		trialResultMutant = new BaseTrialResultMutant();
			
		TrialType type = trialDescription.getType();
		trialResultMutant.setType(type);
		trialResultMutant.setTrialName(trialName);
		runBeforeTrial();
		if (this.trialResultMutant.getFailure() == null) {
		
			testsRunner.setTrial(trial);
			if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
				reporter.log("running trial tests " + trialName);
			}
			if (type == TrialType.MetaTrial) {
				runMetaTrialMethods();
			}
			runTests();
			
			if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
				reporter.log("finished trial tests" + trialName);
			}
			
			if (trialResultMutant.isPassed()) {
				//skip this method unless everything passed in the trial
				runAfterTrialTests(trialCoverageRecorder);
			}
			runAfterTrial();
		}
		
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("calculating trial results " + trialName);
		}
		I_TrialResult result;
		switch (type) {
			case UseCaseTrial:
					UseCaseTrialResultMutant mut = new UseCaseTrialResultMutant(trialResultMutant);
					mut.setSystem(trialDescription.getSystemName());
					mut.setUseCase(trialDescription.getUseCase());
					setAfterTrialTestsState(mut);
					result = new UseCaseTrialResult(mut);
				break;
			case ApiTrial:
					ApiTrialResultMutant api = new ApiTrialResultMutant(trialResultMutant);
					api.setPackageName(trialDescription.getPackageName());
					setAfterTrialTestsState(api);
					result = new ApiTrialResult(api);
				break;
			case SourceFileTrial:
					SourceFileTrialResultMutant src = new SourceFileTrialResultMutant(trialResultMutant);
					Class<?> clazz = trialDescription.getSourceFileClass();
					src.setSourceFileName(clazz.getName());
					setAfterTrialTestsState(src);
					result = new SourceFileTrialResult(src);
				break;
			default:
				result = new BaseTrialResult(trialResultMutant);
		}
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("notifying trial finished " + trialName);
		}
		notifier.onTrialCompleted(result);
	}
	
	private void setAfterTrialTestsState(BaseTrialResultMutant mutant) {
		mutant.setHadAfterTrialTests(hadAfterTrialTests);
		mutant.setRanAfterTrialTests(ranAfterTrialTests);
		if (afterTrialTestsResultMutant != null) {
			mutant.addResult(afterTrialTestsResultMutant);
		}
	}
	
	private void runBeforeTrial()  {
		Method beforeTrial = trialDescription.getBeforeTrialMethod();
		if (beforeTrial != null) {
			try {
				beforeTrial.invoke(trial, new Object[] {});
			} catch (Exception e) {
				failTrialOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + 
						trialDescription.getTrialName() + 
						"." + beforeTrial.getName(), e, null);
			}
			trialResultMutant.setBeforeTestOutput(memory.getThreadLocalOutput());
		}
	}

	/**
	 * 
	 * @return all suceeded
	 */
	private void runTests() throws RejectedExecutionException {
		Iterator<TestDescription> methods = trialDescription.getTestMethods();
		
		while (methods.hasNext()) {
			runTest(methods.next());
		}
	}

	private void runTest(TestDescription tm) throws RejectedExecutionException {
		Method method = tm.getMethod();
		blocking.clear();
		
		if (memory.hasTestsFilter()) {
			Class<?> trialClazz = trialDescription.getTrialClass();
			if (!memory.runTest(trialClazz, method.getName())) {
				return;
			}
		}

		if (tm.isIgnore()) {
			TestResultMutant trm = new TestResultMutant();
			trm.setName(method.getName());
			trm.setIgnored(true);
			trialResultMutant.addResult(trm);
		} else {
			notifier.startingTest(trialName, method.getName());
			
			
			
			trial.beforeTests();
			testsRunner.setTestMethod(method);
			testResultFuture = testRunService.submit(testsRunner);
			threadManager.setTestRunFuture(testRunService, testResultFuture);
			
			try {
				Long timeout = tm.getTimeoutMillis();
				if (timeout == 0L) {
					TestResult result = blocking.take();
					trialResultMutant.addResult(result);
					notifier.onTestCompleted(trialName, method.getName(), result.isPassed());
				} else {
					TestResult result = blocking.poll(tm.getTimeoutMillis(), TimeUnit.MILLISECONDS);
					if (result != null) {
						trialResultMutant.addResult(result);
					} else {
						TestResultMutant trm = new TestResultMutant(
								testsRunner.getTestResult());
						TestFailureMutant tfm = new TestFailureMutant();
						String message = "Test Timedout at " + tm.getTimeoutMillis() + " milliseconds.";
						tfm.setMessage(message);
						tfm.setException(new IllegalStateException(message));
						trm.setFailure(new TestFailure(tfm));
					
						trialResultMutant.addResult(new TestResult(trm));
						notifier.onTestCompleted(trialName, method.getName(), trm.isPassed());
					}
				}
			} catch (InterruptedException x) {
				//do nothing
			}
			trial.afterTests();
		}
	}
	
	private void runAfterTrialTests(I_CoverageRecorder trialCoverageRecorder) {
		ranAfterTrialTests = false;
		hadAfterTrialTests = false;
		inAfterTrialTests = true;
		
		TrialType type = trialDescription.getType();
		
		
		switch (type) {
			case SourceFileTrial:
				afterSourceFileTrialTests(trialCoverageRecorder, type);
				break;
			case ApiTrial:
				afterApiTrialTests(trialCoverageRecorder, type);
				break;
			default:
				//do nothing
		}
		
		inAfterTrialTests = false;
	}

	private void afterApiTrialTests(I_CoverageRecorder trialCoverageRecorder,
			TrialType type) {
		Method clazzMethod = null;
		List<I_PackageCoverage> coverage;
		Class<? extends I_AbstractTrial> trialClass = trialDescription.getTrialClass();
		try {
			clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_ApiTrial_TestsResults.class);
		} catch (NoSuchMethodException e) {
			//do nothing
		} catch (SecurityException e) {
			//do nothing
		}
		if (clazzMethod != null) {
			hadAfterTrialTests = true;
		} else {
			return;
		}
		ApiTrial_TestsResultsMutant apiInfoMut = new ApiTrial_TestsResultsMutant();
		
		if (trialCoverageRecorder != null) {
			coverage = trialCoverageRecorder.endRecording();
			I_PackageCoverage cover = trialDescription.findPackageCoverage(coverage);
			apiInfoMut.setCoverage(cover);
		}
		apiInfoMut.setAssertions(trialResultMutant.getAssertionCount());
		apiInfoMut.setUniqueAssertions(trialResultMutant.getUniqueAssertionCount());
		
		trial.setBindings(this);
		if (trialCoverageRecorder != null) {
			coverage = trialCoverageRecorder.endRecording();
			I_PackageCoverage pkgCover = trialDescription.findPackageCoverage(coverage);
			apiInfoMut.setCoverage(pkgCover);
		}
		ranAfterTrialTests = true;
		
		boolean passed = false;
		try {
			if (trial instanceof I_ApiTrial) {
				((I_ApiTrial) trial).afterTrialTests(apiInfoMut);
			}
			passed = true;
		} catch (AfterTrialTestsAssertionFailure x) {
			//the test failed, in one of it's asserts
		} catch (Exception x) {
			onAfterTrialTestsMethodException(x, AFTER_API_TRIAL_TESTS_METHOD);
		}
		if (afterTrialTestsResultMutant == null) {
			afterTrialTestsResultMutant = new TestResultMutant();
			afterTrialTestsResultMutant.setPassed(passed);
			flushAssertionHashes(afterTrialTestsResultMutant, afterTrialTestsAssertionHashes);
			afterTrialTestsResultMutant.setName(AFTER_API_TRIAL_TESTS_METHOD);
		}
		notifier.onTestCompleted(trialClass.getName(), 
				AFTER_API_TRIAL_TESTS_METHOD, 
				afterTrialTestsResultMutant.isPassed());
		trialResultMutant.addResult(afterTrialTestsResultMutant);
	}

	private void afterSourceFileTrialTests(
			I_CoverageRecorder trialCoverageRecorder, TrialType type) {
		Method clazzMethod = null;
		List<I_PackageCoverage> coverage;
		Class<? extends I_AbstractTrial> trialClass = trialDescription.getTrialClass();
		
		try {
			clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_SourceFileTrial_TestsResults.class);
		} catch (NoSuchMethodException e) {
			//do nothing
		} catch (SecurityException e) {
			//do nothing
		}
		if (clazzMethod != null) {
			hadAfterTrialTests = true;
		} else {
			return;
		}
		SourceFileTrial_TestsResultsMutant infoMut = new SourceFileTrial_TestsResultsMutant();
		
		if (trialCoverageRecorder != null) {
			coverage = trialCoverageRecorder.endRecording();
			I_SourceFileCoverage cover = trialDescription.findSourceFileCoverage(coverage);
			if (cover != null) {
				infoMut.setCoverage(cover);
			}
		}
		infoMut.setAssertions(trialResultMutant.getAssertionCount());
		infoMut.setUniqueAssertions(trialResultMutant.getUniqueAssertionCount());
		
		ranAfterTrialTests = true;
		
		trial.setBindings(this);
		boolean passed = false;
		try {
			if (trial instanceof I_SourceFileTrial) {
				((I_SourceFileTrial) trial).afterTrialTests(infoMut);
			}
			passed = true;
		} catch (AfterTrialTestsAssertionFailure x) {
			//the test failed, in one of it's asserts
		} catch (Exception x) {
			onAfterTrialTestsMethodException(x, AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD);
		}
		if (afterTrialTestsResultMutant == null) {
			afterTrialTestsResultMutant = new TestResultMutant();
			afterTrialTestsResultMutant.setPassed(passed);
			flushAssertionHashes(afterTrialTestsResultMutant, afterTrialTestsAssertionHashes);
			afterTrialTestsResultMutant.setName(AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD);
		}
		notifier.onTestCompleted(trialClass.getName(), 
				AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD, 
				afterTrialTestsResultMutant.isPassed());
		trialResultMutant.addResult(afterTrialTestsResultMutant);
	}
	
	private void runMetaTrialMethods() {
		inRunMetaTrialMethods = true;
		I_MetaTrial imt  = (I_MetaTrial) trial;
		
		
		trial.setBindings(this);
		runMetaTrialMethod = AFTER_METADATA_CALCULATED_METHOD;
		boolean passed = false;
		try {
			I_TrialRunMetadata metadata = memory.takeMetaTrialData();
			imt.afterMetadataCalculated(metadata);
			passed = true;
		} catch (MetaTrialAssertionFailure p) {
			//an assertion failed
		} catch (Exception x) {
			onMetaTrialAfterMethodException(x, AFTER_METADATA_CALCULATED_METHOD);
		}
		if (metaTrialTestResultMutant == null) {
			metaTrialTestResultMutant = new TestResultMutant();
			metaTrialTestResultMutant.setPassed(passed);
			flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
			metaTrialTestResultMutant.setName(AFTER_METADATA_CALCULATED_METHOD);
		}
		notifier.onTestCompleted(trial.getClass().getName(),
				metaTrialTestResultMutant.getName(), 
				metaTrialTestResultMutant.isPassed());
		trialResultMutant.addResult(metaTrialTestResultMutant);
		
		metaTrialAssertionHashes.clear();
		metaTrialTestResultMutant = null;
		
		
		runMetaTrialMethod = AFTER_NON_META_TRIALS_RUN_METHOD;
		passed = false;
		try {
			imt.afterNonMetaTrialsRun(runResultMutant);
			passed = true;
		} catch (MetaTrialAssertionFailure p) {
			//an assertion failed
		} catch (Exception x) {
			onMetaTrialAfterMethodException(x, AFTER_NON_META_TRIALS_RUN_METHOD);
		}
		if (metaTrialTestResultMutant == null) {
			metaTrialTestResultMutant = new TestResultMutant();
			metaTrialTestResultMutant.setPassed(passed);
			flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
			metaTrialTestResultMutant.setName(AFTER_NON_META_TRIALS_RUN_METHOD);
		}
		notifier.onTestCompleted(trial.getClass().getName(),
				metaTrialTestResultMutant.getName(), 
				metaTrialTestResultMutant.isPassed());
		trialResultMutant.addResult(metaTrialTestResultMutant);
		
		inRunMetaTrialMethods = false;
	}

	private void onMetaTrialAfterMethodException(Exception x, String method) {
		metaTrialTestResultMutant = new TestResultMutant();
		metaTrialTestResultMutant.setPassed(false);
		flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
		metaTrialTestResultMutant.setName(method);
		TestFailureMutant tfm = new TestFailureMutant();
		tfm.setException(x);
		String message = x.getMessage();
		if (StringMethods.isEmpty(message)) {
			tfm.setMessage("Unknown exception message in onMetaTrialAfterMethodException.");
		} else {
			tfm.setMessage(x.getMessage());
		}
		metaTrialTestResultMutant.setFailure(tfm);
	}
	
	

	private void onAfterTrialTestsMethodException(Exception x, String method) {
		afterTrialTestsResultMutant = new TestResultMutant();
		afterTrialTestsResultMutant.setPassed(false);
		flushAssertionHashes(afterTrialTestsResultMutant, afterTrialTestsAssertionHashes);
		afterTrialTestsResultMutant.setName(method);
		TestFailureMutant tfm = new TestFailureMutant();
		tfm.setException(x);
		String message = x.getMessage();
		if (StringMethods.isEmpty(message)) {
			message = "Unknown Error message.";
		}
		tfm.setMessage(message);
		afterTrialTestsResultMutant.setFailure(tfm);
	}
	
	private void runAfterTrial() {
		Method afterTrial = trialDescription.getAfterTrialMethod();
		if (afterTrial != null) {
			try {
				afterTrial.invoke(trial, new Object[] {});
			} catch (Exception e) {
				failTrialOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + 
						trialName + 
						"." + afterTrial.getName(), e, null);
			}
			trialResultMutant.setAfterTestOutput(memory.getThreadLocalOutput());
		}
	}
	
	@Override
	public void testFinished(TestResult p) {
		TestResultMutant forOut = new TestResultMutant(p);
		forOut.setOutput(memory.getThreadLocalOutput());
		TestResult result = new TestResult(forOut);
		
		try {
			blocking.put(result);
			if (p.isPassed()) {
				blocking.notify();
			} else {
				synchronized (blocking) {
					blocking.notify();
				}
			}
			testResultFuture.cancel(false);
		} catch (InterruptedException e) {
			//do nothing
		}
	}

	@Override
	public void assertCompleted(I_AssertCommand cmd) {
		if (inRunMetaTrialMethods) {
			metaTrialAssertionHashes.add(cmd.hashCode());
		} else if (inAfterTrialTests) {
			afterTrialTestsAssertionHashes.add(cmd.hashCode());
		}  
	}

	@Override
	public void assertFailed(I_TestFailure failure) {
		if (inRunMetaTrialMethods) {
			metaTrialTestResultMutant = new TestResultMutant();
			metaTrialTestResultMutant.setFailure(failure);
			metaTrialTestResultMutant.setName(runMetaTrialMethod);
			flushAssertionHashes(metaTrialTestResultMutant,metaTrialAssertionHashes);
			throw new MetaTrialAssertionFailure();
		} else if (inAfterTrialTests) {
			afterTrialTestsResultMutant = new TestResultMutant();
			afterTrialTestsResultMutant.setFailure(failure);
			afterTrialTestsResultMutant.setName(AFTER_TRIAL_TESTS);
			flushAssertionHashes(afterTrialTestsResultMutant,afterTrialTestsAssertionHashes);
			throw new AfterTrialTestsAssertionFailure();
		} 
	}

	private void flushAssertionHashes(TestResultMutant trm, Collection<Integer> hashes) {
		Iterator<Integer> it = hashes.iterator();
		while (it.hasNext()) {
			trm.incrementAssertionCount(it.next());
		}
	}

	public boolean isFinished() {
		return finished;
	}

	public synchronized TrialDescription getTrialDescription() {
		return trialDescription;
	}

	@Override
	public I_AssertListener getAssertionListener() {
		return this;
	}

	@Override
	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}

	private void afterNonMetaTrials() {
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("afterTrials()");
		}
		//@diagram_sync on 5/26/2014 with Overview.seq
		if (memory.hasMetaTrial()) {
			synchronized (memory) {
				if (!memory.hasRanMetaTrial()) {
					memory.setRanMetaTrial();
				
					//@diagram_sync on 5/26/2014 with Overview.seq
					trialDescription = memory.getMetaTrialDescription();
					runTrial();
					
					int assertionCount = trialResultMutant.getAssertionCount();
					runResultMutant.addAsserts(assertionCount);
					runResultMutant.addTestFailures(trialResultMutant.getTestFailureCount());
					//I_MetaTrial currently has two additional test methods
					runResultMutant.addTests(trialResultMutant.getTestCount());
					if (!trialResultMutant.isPassed()) {
						runResultMutant.addTrialFailures(1);
					}
					runResultMutant.addTrials(1);
					runResultMutant.addUniqueAsserts(trialResultMutant.getUniqueAssertionCount());
					
				}
			}
		} 
		notifier.onAllTrialsDone(new TrialRunResult(runResultMutant));
	}

	@Override
	public PlatformEnum getPlatform() {
		return PlatformEnum.JSE;
	}

	@Override
	public I_EvaluatorLookup getDefalutEvaluatorLookup() {
		return memory.getEvaluationLookup();
	}
}
