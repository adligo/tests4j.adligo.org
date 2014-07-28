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
import org.adligo.tests4j.models.shared.results.I_TestResult;
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
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.I_Tests4J_TestFinishedListener;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.TrialBindings;
import org.adligo.tests4j.run.discovery.TestDescription;
import org.adligo.tests4j.run.discovery.TrialDescription;

/**
 * A Runnable that polls the queue of trials that 
 * need to be run, and runs them one at a time,
 * delegating @Tests to another thread @see TestRunnable.
 * 
 * @author scott
 *
 */
public class Tests4J_TrialsRunable implements Runnable,  
	I_Tests4J_TestFinishedListener {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";
	
	private Tests4J_Memory memory;
	private Tests4J_ThreadManager threadManager;
	private I_Tests4J_NotificationManager notifier;
	private TrialDescription trialDescription;
	private I_Tests4J_Log logger;
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
	private I_Tests4J_CoverageRecorder trialThreadLocalCoverageRecorder;
	private TrialDescriptionProcessor trialDescriptionProcessor;
	private AfterSourceFileTrialTestsProcessor afterSouceFileTrialTestsProcessor;
	private AfterApiTrialTestsProcessor afterApiTrialTestsProcessor;
	private AfterUseCaseTrialTestsProcessor afterUseCaseTrialTestsProcessor;
	/**
	 * 
	 * @param p
	 * @param pNotificationManager
	 * @param pReporter
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public Tests4J_TrialsRunable(Tests4J_Memory p, 
			I_Tests4J_NotificationManager pNotificationManager) {
		memory = p;
		notifier = pNotificationManager;
		
		
		logger = p.getLogger();
		threadManager = p.getThreadManager();
		
		testsRunner = new TestRunable(memory);
		testsRunner.setListener(this);
		testRunService = threadManager.createNewTestRunService();
		
		bindings = new TrialBindings(Platform.JSE, p.getEvaluationLookup(), logger);
		bindings.setAssertListener(testsRunner);
		
		trialDescriptionProcessor = new TrialDescriptionProcessor(memory);
		afterSouceFileTrialTestsProcessor = new AfterSourceFileTrialTestsProcessor(memory);
		afterApiTrialTestsProcessor = new AfterApiTrialTestsProcessor(memory);
		afterUseCaseTrialTestsProcessor = new AfterUseCaseTrialTestsProcessor(memory);
		
	}

	/**
	 * 
	 * 
	 */
	@Override
	public void run() {
		
		Class<? extends I_AbstractTrial> trialClazz = memory.pollTrialsToRun();
		while (trialClazz != null) {
			try {
				if ( !I_MetaTrial.class.isAssignableFrom(trialClazz)) {
					//start recording the trial coverage,
					//code cover the creation of the class, in the trialDescrption
					I_Tests4J_CoveragePlugin plugin = memory.getCoveragePlugin();
					if (plugin != null) {
						if (plugin.canThreadGroupLocalRecord()) {
							//@diagram sync on 7/5/2014
							// for Overview.seq 
							trialThreadLocalCoverageRecorder = plugin.createRecorder();
							trialThreadLocalCoverageRecorder.startRecording();
						}
					}
					
					trialDescription = trialDescriptionProcessor.newTrailDescriptionToRun(trialClazz, notifier);
				}
				
			} catch (Exception x) {
				logger.onException(x);
			} 
			
			if (trialDescription != null) {
				if (trialDescription.isRunnable()) {
					if (trialDescription.getType() != TrialType.MetaTrial) {
						try {
							//synchronized here so that each trial can only be running
							//once in all of the trial threads, having a trial running
							//in two threads at the same time would be confusing to the users
							synchronized (trialClazz) {
								if (logger.isLogEnabled(Tests4J_TrialsRunable.class)) {
									logger.log("Thread " + Thread.currentThread().getName() +
											" is running trial;\n" +trialDescription.getTrialName());
								}
								runTrial();
							}	
						} catch (RejectedExecutionException x) {
							memory.getLogger().onException(x);
						} catch (Exception x) {
							memory.getLogger().onException(x);
						} catch (Error x) {
							memory.getLogger().onException(x);
						} 
					}
				}
			}
			trialClazz = memory.pollTrialsToRun();
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
		
		if (logger.isLogEnabled(Tests4J_TrialsRunable.class)) {
			logger.log("running trial " + trialName);
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
			if (logger.isLogEnabled(Tests4J_TrialsRunable.class)) {
				logger.log("running trial tests " + trialName);
			}
			runTests();
			
			if (logger.isLogEnabled(Tests4J_TrialsRunable.class)) {
				logger.log("finished trial tests" + trialName);
			}
			if (trialResultMutant.isPassed()) {
				//skip this method unless everything passed in the trial
				trialResultMutant = runAfterTrialTests();
			}
			runAfterTrial();
		}
		
		if (logger.isLogEnabled(Tests4J_TrialsRunable.class)) {
			logger.log("calculating trial results " + trialName);
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
		if (logger.isLogEnabled(Tests4J_TrialsRunable.class)) {
			logger.log("notifying trial finished " + trialName);
		}
		notifier.onTrialCompleted(result);
		memory.addTrialsDone();
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
			memory.addTestsDone();
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
			
			if (logger.isLogEnabled(Tests4J_TrialsRunable.class)) {
				logger.log("starting test; " +trialName + "."+  method.getName());
			}
			
			//trial.beforeTests();
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
			//trial.afterTests();
		}
	}
	
	private BaseTrialResultMutant runAfterTrialTests() {
		
		TrialType type = trialDescription.getType();
		
		
		long tests = memory.getTestsDone();
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
				memory.addTestsDone();
				return result;
			case ApiTrial:
				afterApiTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				ApiTrialResultMutant apiResult = getApiTrialResult();
				TestResultMutant apiTrm = afterApiTrialTestsProcessor.afterApiTrialTests(apiResult);
				if (apiTrm != null) {
					apiResult.addResult(apiTrm);
				}
				memory.addTestsDone();
				return apiResult;
			case UseCaseTrial:
				afterUseCaseTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				UseCaseTrialResultMutant useCaseResult = getUseCaseResult();
				TestResultMutant useCaseTrm = afterUseCaseTrialTestsProcessor.afterUseCaseTrialTests(useCaseResult);
				if (useCaseTrm != null) {
					useCaseResult.addResult(useCaseTrm);
				}
				memory.addTestsDone();
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
	public void testFinished(I_TestResult p) {
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