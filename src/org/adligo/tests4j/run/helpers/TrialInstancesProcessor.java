package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.adligo.tests4j.models.shared.ApiTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.SourceFileTrial;
import org.adligo.tests4j.models.shared.asserts.AssertionHelperInfo;
import org.adligo.tests4j.models.shared.asserts.I_AssertCommand;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
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
import org.adligo.tests4j.models.shared.results.UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResultMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

public class TrialInstancesProcessor implements Runnable, I_TestFinishedListener, I_AssertListener {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";

	private Tests4J_Memory memory;
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
	private List<Integer> afterTrialTestsAssertionHashes = new ArrayList<Integer>(); 
	private TestResultMutant afterTrialTestsResultMutant;
	private boolean finished = false;
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
		
		testsRunner = new TestRunable(pReporter);
		testsRunner.setListener(this);
		testRunService = Executors.newSingleThreadExecutor();
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
			memory.getLog().onError(x);
			notifier.onDescibeTrialError();
			return;
		} catch (Error x) {
			memory.getLog().onError(x);
			notifier.onDescibeTrialError();
			return;
		}
		
		try {
			
			trialDescription = memory.pollDescriptions();
			while (trialDescription != null) {
				runTrial();
				trialDescription = memory.pollDescriptions();
			}
			testRunService.shutdownNow();
			notifier.checkDoneRunningTrials();
		} catch (Exception x) {
			memory.getLog().onError(x);
		} catch (Error x) {
			memory.getLog().onError(x);
		}
		finished = true;
	}

	/**
	 * @diagram sync on 5/8/2014
	 *    for Overview.seq
	 *    
	 * @param trialClazz
	 */
	private void addTrialDescription(Class<? extends I_AbstractTrial> trialClazz) {
		
		I_CoverageRecorder trialCoverageRecorder = startRecordingTrial(trialClazz);
		
		TrialDescription desc = new TrialDescription(trialClazz, memory.getLog());
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
				TrialTypeEnum type = desc.getType();
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
				if (Boolean.TRUE == memory.getRecordSeperateTrialCoverage()) {
					
					trialCoverageRecorder = plugin.createRecorder(name);
					memory.addRecorder(name, trialCoverageRecorder);
				}
			}
		}
		if (trialCoverageRecorder != null) {
			trialCoverageRecorder.startRecording();
		}
		return trialCoverageRecorder;
	}
	
	private void failTestOnException(String message, Throwable p, TrialTypeEnum type) {
		trialResultMutant.setPassed(false);
		TrialFailure failure = new TrialFailure(message, p);
		trialResultMutant.setFailure(failure);
		if (type != null) {
			trialResultMutant.setType(type);
		}
	}
	private void runTrial() {
		
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
		AssertionHelperInfo atm = new AssertionHelperInfo();
		atm.setCoveragePlugin(memory.getPlugin());
		atm.setListener(testsRunner);
		
		trial.setMemory(atm);
		
		trialResultMutant = new BaseTrialResultMutant();
			
		trialResultMutant.setType(trialDescription.getType());
		trialResultMutant.setTrialName(trialDescription.getTrialName());
		runBeforeTrial();
		testsRunner.setTrial(trial);
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("running trial tests " + trialDescription.getTrialName());
		}
		runTests();
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("finished trial tests" + trialDescription.getTrialName());
		}
		
		runAfterTrialTests(trialCoverageRecorder);
		runAfterTrial();
		
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("calculating trial results " + trialDescription.getTrialName());
		}
		TrialTypeEnum type = trialDescription.getType();
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
			default:
					SourceFileTrialResultMutant src = new SourceFileTrialResultMutant(trialResultMutant);
					Class<?> clazz = trialDescription.getSourceFileClass();
					src.setSourceFileName(clazz.getSimpleName());
					setAfterTrialTestsState(src);
					result = new SourceFileTrialResult(src);
				break;
		}
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("notifying trial finished " + trialDescription.getTrialName());
		}
		notifier.onTrialCompleted(result);
		trialResultMutant = null;
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
				failTestOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + 
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
	private void runTests() {
		Iterator<TestDescription> methods = trialDescription.getTestMethods();
		
		while (methods.hasNext()) {
			runTest(methods.next());
		}
		
	}

	private void runTest(TestDescription tm) {
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
		
		TrialTypeEnum type = trialDescription.getType();
		
		List<I_PackageCoverage> coverage = null;
		Method clazzMethod = null;
		switch (type) {
			case SourceFileTrial:
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
				if (trialCoverageRecorder == null) {
					return;
				}
				coverage = trialCoverageRecorder.endRecording();
				ranAfterTrialTests = true;
				I_SourceFileCoverage cover = trialDescription.findSourceFileCoverage(coverage);
				AssertionHelperInfo atm = new AssertionHelperInfo();
				atm.setCoveragePlugin(memory.getPlugin());
				atm.setListener(this);
				trial.setMemory(atm);
				try {
					((SourceFileTrial) trial).afterTrialTests(cover);
				} catch (Exception x) {
					failTestOnException(x.getMessage(), x, type);
				}
				break;
			case ApiTrial:
				trialClass = trialDescription.getTrialClass();
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
				if (trialCoverageRecorder == null) {
					return;
				}
				atm = new AssertionHelperInfo();
				atm.setCoveragePlugin(memory.getPlugin());
				atm.setListener(this);
				trial.setMemory(atm);
				coverage = trialCoverageRecorder.endRecording();
				ranAfterTrialTests = true;
				I_PackageCoverage pkgCover = trialDescription.findPackageCoverage(coverage);
				try {
					((ApiTrial) trial).afterTrialTests(pkgCover);
				} catch (Exception x) {
					failTestOnException(x.getMessage(), x, type);
				}
				break;
			default:
				//do nothing
		}
	}
	
	private void runAfterTrial() {
		Method afterTrial = trialDescription.getAfterTrialMethod();
		if (afterTrial != null) {
			try {
				afterTrial.invoke(trial, new Object[] {});
			} catch (Exception e) {
				failTestOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + 
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
		afterTrialTestsAssertionHashes.add(cmd.hashCode());
	}

	@Override
	public void assertFailed(I_TestFailure failure) {
		afterTrialTestsResultMutant = new TestResultMutant();
		afterTrialTestsResultMutant.setFailure(failure);
		afterTrialTestsResultMutant.setName("afterTrialTests");
		flushAssertionHashes(afterTrialTestsResultMutant);
	}

	private void flushAssertionHashes(TestResultMutant trm) {
		Iterator<Integer> it = afterTrialTestsAssertionHashes.iterator();
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
	
	public void poke() {
		
	}
}
