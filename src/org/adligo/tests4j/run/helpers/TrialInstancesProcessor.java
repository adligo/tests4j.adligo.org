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

import org.adligo.tests4j.models.shared.ApiTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.I_MetaTrial;
import org.adligo.tests4j.models.shared.I_TrialProcessorBindings;
import org.adligo.tests4j.models.shared.SourceFileTrial;
import org.adligo.tests4j.models.shared.asserts.I_AssertCommand;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
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
import org.adligo.tests4j.models.shared.results.feedback.SourceFileTrial_TestsResultsMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

public class TrialInstancesProcessor implements Runnable, 
I_TestFinishedListener, I_AssertListener, I_TrialProcessorBindings {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";
	
	private Tests4J_Memory memory;
	private Tests4J_ThreadManager threadManager;
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
		
		testsRunner = new TestRunable(pReporter);
		testsRunner.setListener(this);
		testRunService = threadManager.createNewTestRunService();
	}

	/**
	 * 
	 * 
	 */
	@Override
	public void run() {
		try {
			Class<? extends I_AbstractTrial> trialClazz = memory.pollTrialClasses();
			while (trialClazz != null) {
				
				addTrialDescription(trialClazz);
				trialClazz = memory.pollTrialClasses();
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
		
		try {
			//@diagram_sync on 5/26/2014 with Overview.seq
			TrialDescription td = memory.pollDescriptions();
			//@diagram_sync on 5/26/2014 with Overview.seq
			runTrialDescription(td);
			//@diagram_sync on 5/26/2014 with Overview.seq
			runResultMutant = notifier.checkDoneRunningNonMetaTrials();
			if (runResultMutant != null) {
				afterNonMetaTrials();
			}
		} catch (RejectedExecutionException x) {
			memory.getReporter().onError(x);
		} catch (Exception x) {
			memory.getReporter().onError(x);
		} catch (Error x) {
			memory.getReporter().onError(x);
		} 
		testRunService.shutdownNow();
		finished = true;
	}

	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 * @param p
	 */
	private void runTrialDescription(TrialDescription p) {
		trialDescription = p;
		while (trialDescription != null) {
			runTrial();
			trialDescription = memory.pollDescriptions();
		}
	}

	/**
	 * @diagram sync on 5/8/2014
	 *    for Overview.seq
	 *    
	 * @param trialClazz
	 */
	private void addTrialDescription(Class<? extends I_AbstractTrial> trialClazz) {
		
		TrialTypeEnum type = TrialTypeFinder.getTypeInternal(trialClazz);
		I_CoverageRecorder trialCoverageRecorder = startRecordingTrial(trialClazz);
		
		TrialDescription desc = new TrialDescription(trialClazz, memory.getReporter());
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
							memory.add(new UseCaseTrialResult(mut));
						break;
					case ApiTrial:
							ApiTrialResultMutant api = new ApiTrialResultMutant(trm);
							api.setPackageName(desc.getPackageName());
							memory.add(new ApiTrialResult(api));
						break;
					case SourceFileTrial:
							SourceFileTrialResultMutant src = new SourceFileTrialResultMutant(trm);
							Class<?> clazz = desc.getSourceFileClass();
							if (clazz != null) {
								src.setSourceFileName(clazz.getSimpleName());
								memory.add(new SourceFileTrialResult(src));
								break;
							}
					default:
						memory.add(new BaseTrialResult(trm));
				}
				
				
			}
		}
		pauseRecordingTrial(trialCoverageRecorder);
	}

	/**
	 * @diagram sync on 5/8/2014
	 *    for Overview.seq
	 *    
	 * @param trialCoverageRecorder
	 */
	private void pauseRecordingTrial(I_CoverageRecorder trialCoverageRecorder) {
		if (trialCoverageRecorder != null) {
			trialCoverageRecorder.pauseRecording();
		}
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
				trialCoverageRecorder = plugin.createRecorder(name);
				memory.addRecorder(name, trialCoverageRecorder);
			}
		}
		if (trialCoverageRecorder != null) {
			trialCoverageRecorder.startRecording();
		}
		return trialCoverageRecorder;
	}
	
	private void failTrialOnException(String message, Throwable p, TrialTypeEnum type) {
		trialResultMutant.setPassed(false);
		TrialFailure failure = new TrialFailure(message, p);
		trialResultMutant.setFailure(failure);
		if (type != null) {
			trialResultMutant.setType(type);
		}
	}
	private void runTrial() throws RejectedExecutionException  {
		
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("running trial " + trialDescription.getTrialName());
		}
		afterTrialTestsAssertionHashes.clear();
		afterTrialTestsResultMutant = null;
		
		String trialName = trialDescription.getTrialName();
		Class<? extends I_AbstractTrial> trialClazz = trialDescription.getTrialClass();
		notifier.startingTrial(trialName);
		I_CoverageRecorder trialCoverageRecorder =  startRecordingTrial(trialClazz);
		
		
		trial = trialDescription.getTrial();
		trial.setBindings(testsRunner);
		
		trialResultMutant = new BaseTrialResultMutant();
			
		TrialTypeEnum type = trialDescription.getType();
		trialResultMutant.setType(type);
		trialResultMutant.setTrialName(trialDescription.getTrialName());
		runBeforeTrial();
		if (this.trialResultMutant.getFailure() == null) {
		
			testsRunner.setTrial(trial);
			if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
				reporter.log("running trial tests " + trialDescription.getTrialName());
			}
			if (type == TrialTypeEnum.MetaTrial) {
				runMetaTrialMethods();
			}
			runTests();
			
			if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
				reporter.log("finished trial tests" + trialDescription.getTrialName());
			}
			
			runAfterTrialTests(trialCoverageRecorder);
			runAfterTrial();
		}
		
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("calculating trial results " + trialDescription.getTrialName());
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
					src.setSourceFileName(clazz.getSimpleName());
					setAfterTrialTestsState(src);
					result = new SourceFileTrialResult(src);
				break;
			default:
				result = new BaseTrialResult(trialResultMutant);
		}
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("notifying trial finished " + trialDescription.getTrialName());
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

		String trialName = trialDescription.getTrialName();
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
		
		TrialTypeEnum type = trialDescription.getType();
		
		List<I_PackageCoverage> coverage = null;
		Method clazzMethod = null;
		switch (type) {
			case SourceFileTrial:
				afterSourceFileTrialTests(trialCoverageRecorder, type, clazzMethod);
				break;
			case ApiTrial:
				afterApiTrialTests(trialCoverageRecorder, type, clazzMethod);
				break;
			default:
				//do nothing
		}
		
		inAfterTrialTests = false;
	}

	private void afterApiTrialTests(I_CoverageRecorder trialCoverageRecorder,
			TrialTypeEnum type, Method clazzMethod) {
		List<I_PackageCoverage> coverage;
		Class<? extends I_AbstractTrial> trialClass = trialDescription.getTrialClass();
		try {
			clazzMethod = trialClass.getDeclaredMethod("afterTrialTests", I_PackageCoverage.class);
		} catch (NoSuchMethodException e) {
			//do nothing
		} catch (SecurityException e) {
			//do nothing
		}
		if (clazzMethod != null) {
			hadAfterTrialTests = true;
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
		
		try {
			if (trial instanceof ApiTrial) {
				((ApiTrial) trial).afterTrialTests(apiInfoMut);
			}
		} catch (Exception x) {
			failTrialOnException(x.getMessage(), x, type);
		}
		afterTrialTestsResultMutant = new TestResultMutant();
		afterTrialTestsResultMutant.setPassed(true);
		flushAssertionHashes(afterTrialTestsResultMutant, afterTrialTestsAssertionHashes);
		afterTrialTestsResultMutant.setName("afterTrialTests(I_PackageCoverage p");
		
		trialResultMutant.addResult(afterTrialTestsResultMutant);
	}

	private void afterSourceFileTrialTests(
			I_CoverageRecorder trialCoverageRecorder, TrialTypeEnum type,
			Method clazzMethod) {
		List<I_PackageCoverage> coverage;
		Class<? extends I_AbstractTrial> trialClass = trialDescription.getTrialClass();
		
		try {
			clazzMethod = trialClass.getDeclaredMethod("afterTrialTests", I_SourceFileCoverage.class);
		} catch (NoSuchMethodException e) {
			//do nothing
		} catch (SecurityException e) {
			//do nothing
		}
		if (clazzMethod != null) {
			hadAfterTrialTests = true;
		}
		SourceFileTrial_TestsResultsMutant infoMut = new SourceFileTrial_TestsResultsMutant();
		
		if (trialCoverageRecorder != null) {
			coverage = trialCoverageRecorder.endRecording();
			I_SourceFileCoverage cover = trialDescription.findSourceFileCoverage(coverage);
			infoMut.setCoverage(cover);
		}
		infoMut.setAssertions(trialResultMutant.getAssertionCount());
		infoMut.setUniqueAssertions(trialResultMutant.getUniqueAssertionCount());
		
		ranAfterTrialTests = true;
		
		trial.setBindings(this);
		try {
			if (trial instanceof SourceFileTrial) {
				((SourceFileTrial) trial).afterTrialTests(infoMut);
			}
		} catch (Exception x) {
			failTrialOnException(x.getMessage(), x, type);
		}
		afterTrialTestsResultMutant = new TestResultMutant();
		afterTrialTestsResultMutant.setPassed(true);
		flushAssertionHashes(afterTrialTestsResultMutant, afterTrialTestsAssertionHashes);
		afterTrialTestsResultMutant.setName("afterTrialTests(I_SourceFileCoverage p");
		
		trialResultMutant.addResult(afterTrialTestsResultMutant);
	}
	
	private void runMetaTrialMethods() {
		inRunMetaTrialMethods = true;
		
		TrialTypeEnum type = trialDescription.getType();
		
		trial.setBindings(this);
		try {
			if (trial instanceof I_MetaTrial) {
				I_TrialRunMetadata metadata = memory.takeMetaTrialData();
				((I_MetaTrial) trial).afterMetadataCalculated(metadata);
			}
			if (metaTrialTestResultMutant == null) {
				metaTrialTestResultMutant = new TestResultMutant();
				metaTrialTestResultMutant.setPassed(true);
				flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
				metaTrialTestResultMutant.setName("afterTrialTests(I_SourceFileCoverage p");
			}
		} catch (Exception x) {
			onMetaTrialAfterMethodException(x);
		}
		trialResultMutant.addResult(metaTrialTestResultMutant);
		metaTrialAssertionHashes.clear();
		metaTrialTestResultMutant = null;
		
		try {
			if (trial instanceof I_MetaTrial) {
				((I_MetaTrial) trial).afterNonMetaTrialsRun(runResultMutant);
			}
			if (metaTrialTestResultMutant == null) {
				metaTrialTestResultMutant = new TestResultMutant();
				metaTrialTestResultMutant.setPassed(true);
				flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
				metaTrialTestResultMutant.setName("afterTrialTests(I_SourceFileCoverage p");
			}
			trialResultMutant.addResult(metaTrialTestResultMutant);
		} catch (Exception x) {
			onMetaTrialAfterMethodException(x);
		}
		trialResultMutant.addResult(metaTrialTestResultMutant);
		
		inRunMetaTrialMethods = true;
		
	}

	private void onMetaTrialAfterMethodException(Exception x) {
		metaTrialTestResultMutant = new TestResultMutant();
		metaTrialTestResultMutant.setPassed(false);
		flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
		metaTrialTestResultMutant.setName("afterTrialTests(I_SourceFileCoverage p");
		TestFailureMutant tfm = new TestFailureMutant();
		tfm.setException(x);
		tfm.setMessage(x.getMessage());
		metaTrialTestResultMutant.setFailure(tfm);
	}
	
	private void runAfterTrial() {
		Method afterTrial = trialDescription.getAfterTrialMethod();
		if (afterTrial != null) {
			try {
				afterTrial.invoke(trial, new Object[] {});
			} catch (Exception e) {
				failTrialOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + 
						trialDescription.getTrialName() + 
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
			blocking.notify();
			testResultFuture.cancel(false);
		} catch (InterruptedException e) {
			//do nothing
		}
	}

	@Override
	public void assertCompleted(I_AssertCommand cmd) {
		if (inAfterTrialTests) {
			afterTrialTestsAssertionHashes.add(cmd.hashCode());
		} else if (inRunMetaTrialMethods) {
			metaTrialAssertionHashes.add(cmd.hashCode());
		}
	}

	@Override
	public void assertFailed(I_TestFailure failure) {
		if (inAfterTrialTests) {
			afterTrialTestsResultMutant = new TestResultMutant();
			afterTrialTestsResultMutant.setFailure(failure);
			afterTrialTestsResultMutant.setName("afterTrialTests");
			flushAssertionHashes(afterTrialTestsResultMutant,afterTrialTestsAssertionHashes);
		} else if (inRunMetaTrialMethods) {
			metaTrialTestResultMutant = new TestResultMutant();
			metaTrialTestResultMutant.setFailure(failure);
			metaTrialTestResultMutant.setName("afterTrialTests");
			flushAssertionHashes(metaTrialTestResultMutant,metaTrialAssertionHashes);
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
			//@diagram_sync on 5/26/2014 with Overview.seq
			trialDescription = memory.getMetaTrialDescription();
			runTrialDescription(trialDescription);
			int assertionCount = trialResultMutant.getAssertionCount();
			runResultMutant.addAsserts(assertionCount);
			runResultMutant.addTestFailures(trialResultMutant.getTestFailureCount());
			//I_MetaTrial currently has two additional test methods
			runResultMutant.addTests(trialResultMutant.getTestCount() + 2);
			if (!trialResultMutant.isPassed()) {
				runResultMutant.addTrialFailures(1);
			}
			runResultMutant.addTrials(1);
			runResultMutant.addUniqueAsserts(trialResultMutant.getUniqueAssertionCount());
		} 
		notifier.onAllTrialsDone(new TrialRunResult(runResultMutant));
	}
}
