package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.adligo.tests4j.models.shared.common.Platform;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
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
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.TrialBindings;

public class TrialInstancesProcessor implements Runnable,  
	I_TestFinishedListener {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";
	
	private Tests4J_Memory memory;
	private Tests4J_Manager threadManager;
	private I_Tests4J_NotificationManager notifier;
	private TrialDescription trialDescription;
	private I_Tests4J_Reporter reporter;
	private I_AbstractTrial trial;
	private BaseTrialResultMutant trialResultMutant;
	
	private ExecutorService testRunService;
	private Future<?> testResultFuture;
	private ArrayBlockingQueue<TestResult> blocking = new ArrayBlockingQueue<TestResult>(100);
	private TestRunable testsRunner;
	private boolean finished = false;
	private ApiTrialResultMutant apiTrialResultMutant;
	private SourceFileTrialResultMutant sourceFileTrialResultMutant;
	private UseCaseTrialResultMutant useCaseTrialResultMutant;
	private TrialBindings bindings;
	

	private String trialName;
	private I_CoverageRecorder trialThreadLocalCoverageRecorder;
	private AfterSourceFileTrialTestsProcessor afterSouceFileTrialTestsProcessor;
	private AfterApiTrialTestsProcessor afterApiTrialTestsProcessor;
	private AfterUseCaseTrialTestsProcessor afterUseCaseTrialTestsProcessor;
	private TrialDescriptionProcessor trialDescriptionProcessor;
	/**
	 * 
	 * @param p
	 * @param pNotificationManager
	 * @param pReporter
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public TrialInstancesProcessor(Tests4J_Memory p, 
			I_Tests4J_NotificationManager pNotificationManager,
			I_Tests4J_Reporter pReporter) {
		memory = p;
		notifier = pNotificationManager;
		reporter = pReporter;
		threadManager = p.getThreadManager();
		
		testsRunner = new TestRunable(memory, pReporter);
		testsRunner.setListener(this);
		testRunService = threadManager.createNewTestRunService();
		
		bindings = new TrialBindings(Platform.JSE, p.getEvaluationLookup(), pReporter);
		bindings.setAssertListener(testsRunner);
		
		afterSouceFileTrialTestsProcessor = new AfterSourceFileTrialTestsProcessor(memory);
		afterApiTrialTestsProcessor = new AfterApiTrialTestsProcessor(memory);
		afterUseCaseTrialTestsProcessor = new AfterUseCaseTrialTestsProcessor(memory);
		trialDescriptionProcessor = new TrialDescriptionProcessor(memory);
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
				if ( !I_MetaTrial.class.isAssignableFrom(trialClazz)) {
					//start recording the trial coverage,
					//code cover the creation of the class, in the trialDescrption
					I_CoveragePlugin plugin = memory.getPlugin();
					if (plugin != null) {
						if (plugin.canThreadGroupLocalRecord()) {
							//@diagram sync on 7/5/2014
							// for Overview.seq 
							trialThreadLocalCoverageRecorder = plugin.createRecorder();
							trialThreadLocalCoverageRecorder.startRecording();
						}
					}
					trialDescription = trialDescriptionProcessor.addTrialDescription(trialClazz);
					notifier.checkDoneDescribingTrials();
				}
				
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
				if (trialDescription.isTrialCanRun()) {
					if (trialDescription.getType() != TrialType.MetaTrial) {
						try {
							//synchronized here so that each trial can only be running
							//once in all of the trial threads, having a trial running
							//in two threads at the same time would be confusing to the users
							synchronized (trialClazz) {
								if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
									reporter.log("Thread " + Thread.currentThread().getName() +
											" is running trial;\n" +trialDescription.getTrialName());
								}
								runTrial();
							}	
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
		
		notifier.checkDoneRunningNonMetaTrials();
		
		testRunService.shutdownNow();
		finished = true;
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
		apiTrialResultMutant = null;
		sourceFileTrialResultMutant = null;
		useCaseTrialResultMutant = null;
		
		trialName = trialDescription.getTrialName();
		int id = memory.incrementTrialRun(trialName);
		trialName = trialName + "[" + id + "]";
		notifier.startingTrial(trialName);
		
		
		trial = trialDescription.getTrial();
		trial.setBindings(bindings);
		
		trialResultMutant = new BaseTrialResultMutant();
			
		TrialType type = trialDescription.getType();
		trialResultMutant.setType(type);
		trialResultMutant.setTrialName(trialName);
		runBeforeTrial();
		I_TrialResult result;
		if (this.trialResultMutant.getFailure() == null) {
		
			testsRunner.setTrial(trial);
			if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
				reporter.log("running trial tests " + trialName);
			}
			runTests();
			
			if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
				reporter.log("finished trial tests" + trialName);
			}
			if (trialResultMutant.isPassed()) {
				//skip this method unless everything passed in the trial
				trialResultMutant = runAfterTrialTests();
			}
			runAfterTrial();
		}
		
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("calculating trial results " + trialName);
		}
		
		switch (type) {
			case UseCaseTrial:
					useCaseTrialResultMutant = getUseCaseResult();
					result = new UseCaseTrialResult(useCaseTrialResultMutant);
				break;
			case ApiTrial:
					apiTrialResultMutant = getApiTrialResult();
					result = new ApiTrialResult(apiTrialResultMutant);
				break;
			case SourceFileTrial:
					sourceFileTrialResultMutant = getSourceFileTrialResult();
					result = new SourceFileTrialResult(sourceFileTrialResultMutant);
				break;
			default:
				result = new BaseTrialResult(trialResultMutant);
		}
		if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
			reporter.log("notifying trial finished " + trialName);
		}
		notifier.onTrialCompleted(result);
	}

	private UseCaseTrialResultMutant getUseCaseResult() {
		if (useCaseTrialResultMutant != null) {
			return useCaseTrialResultMutant;
		}
		useCaseTrialResultMutant = new UseCaseTrialResultMutant(trialResultMutant);
		useCaseTrialResultMutant.setSystem(trialDescription.getSystemName());
		useCaseTrialResultMutant.setUseCase(trialDescription.getUseCase());
		return useCaseTrialResultMutant;
	}

	private ApiTrialResultMutant getApiTrialResult() {
		if (apiTrialResultMutant != null) {
			return apiTrialResultMutant;
		}
		ApiTrialResultMutant apiTrialTestResultMutant = new ApiTrialResultMutant(trialResultMutant);
		apiTrialTestResultMutant.setPackageName(trialDescription.getPackageName());
		return apiTrialTestResultMutant;
	}

	/**
	 * clones (copy constructor) the current
	 * testResultMutant adds the source file class
	 * and returns.
	 * @return
	 */
	private SourceFileTrialResultMutant getSourceFileTrialResult() {
		if (sourceFileTrialResultMutant != null) {
			return sourceFileTrialResultMutant;
		}
		SourceFileTrialResultMutant sourceFileTrialResultMutant = new SourceFileTrialResultMutant(trialResultMutant);
		Class<?> clazz = trialDescription.getSourceFileClass();
		sourceFileTrialResultMutant.setSourceFileName(clazz.getName());
		return sourceFileTrialResultMutant;
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
			
			if (reporter.isLogEnabled(TrialInstancesProcessor.class)) {
				reporter.log("starting test; " +trialName + "."+  method.getName());
			}
			
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
	
	private BaseTrialResultMutant runAfterTrialTests() {
		
		TrialType type = trialDescription.getType();
		
		
		switch (type) {
			case SourceFileTrial:
				afterSouceFileTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				SourceFileTrialResultMutant result = getSourceFileTrialResult();
				TestResultMutant trm = afterSouceFileTrialTestsProcessor.afterSourceFileTrialTests(result);
				if (trm != null) {
					result.addResult(trm);
				}
				TestResultMutant minCoverageResult = afterSouceFileTrialTestsProcessor.testMinCoverage(result);
				result.addResult(minCoverageResult);
				return result;
			case ApiTrial:
				afterApiTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				ApiTrialResultMutant apiResult = getApiTrialResult();
				TestResultMutant apiTrm = afterApiTrialTestsProcessor.afterApiTrialTests(apiResult);
				if (apiTrm != null) {
					apiResult.addResult(apiTrm);
				}
				return apiResult;
			case UseCaseTrial:
				afterUseCaseTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				UseCaseTrialResultMutant useCaseResult = getUseCaseResult();
				TestResultMutant useCaseTrm = afterUseCaseTrialTestsProcessor.afterUseCaseTrialTests(useCaseResult);
				if (useCaseTrm != null) {
					useCaseResult.addResult(useCaseTrm);
				}
				return useCaseResult;
			default:
				//do nothing
		}
		
		return null;
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
		
		blocking.add(result);
		if (p.isPassed()) {
			blocking.notify();
		} else {
			synchronized (blocking) {
				blocking.notify();
			}
		}
		testResultFuture.cancel(false);
	}

	

	public boolean isFinished() {
		return finished;
	}

	public synchronized TrialDescription getTrialDescription() {
		return trialDescription;
	}



}
