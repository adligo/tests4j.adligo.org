package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.adligo.tests4j.models.shared.asserts.common.TestFailure;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureMutant;
import org.adligo.tests4j.models.shared.common.I_TrialType;
import org.adligo.tests4j.models.shared.common.Platform;
import org.adligo.tests4j.models.shared.common.StackTraceBuilder;
import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Runnable;
import org.adligo.tests4j.models.shared.system.I_Tests4J_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_TrialProgress;
import org.adligo.tests4j.models.shared.system.Tests4J_TrialProgress;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_Progress;
import org.adligo.tests4j.models.shared.trials.TrialBindings;
import org.adligo.tests4j.run.common.I_Tests4J_ThreadManager;
import org.adligo.tests4j.run.discovery.TestDescription;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.run.discovery.TrialDescriptionProcessor;
import org.adligo.tests4j.run.discovery.TrialQueueDecisionTree;
import org.adligo.tests4j.run.discovery.TrialState;
import org.adligo.tests4j.run.memory.Tests4J_Memory;
import org.adligo.tests4j.run.output.TrialOutput;
import org.adligo.tests4j.shared.output.I_ConcurrentOutputDelegator;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * A Runnable that polls the queue of trials that 
 * need to be run, and runs them one at a time,
 * delegating @Tests to another thread @see TestRunnable.
 * 
 * @author scott
 *
 */
public class Tests4J_TrialsRunnable implements Runnable,
	I_Tests4J_TestFinishedListener, I_Tests4J_Runnable, I_Progress {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";
	
	private Tests4J_Memory memory;
	private I_Tests4J_ThreadManager threadManager;
	private I_Tests4J_NotificationManager notifier;
	private TrialDescription trialDescription;
	private I_Tests4J_Log log_;
	private I_AbstractTrial trial;
	private BaseTrialResultMutant trialResultMutant;
	
	private ExecutorService testRunService;
	private Future<?> testResultFuture;
	private ArrayBlockingQueue<TestResult> blocking = new ArrayBlockingQueue<TestResult>(100);
	private Tests4J_TestRunable testsRunner;
	private boolean finished = false;
	private ApiTrialResultMutant apiTrialResultMutant;
	private SourceFileTrialResultMutant sourceFileTrialResultMutant;
	private UseCaseTrialResultMutant useCaseTrialResultMutant;
	private TrialBindings bindings;
	private TrialOutput out = new TrialOutput();
	private I_ConcurrentOutputDelegator outputDelegator;
	
	private String trialName;
	private String testName_;
	private I_Tests4J_CoverageRecorder trialThreadLocalCoverageRecorder;
	private AfterSourceFileTrialTestsProcessor afterSouceFileTrialTestsProcessor;
	private AfterApiTrialTestsProcessor afterApiTrialTestsProcessor;
	private AfterUseCaseTrialTestsProcessor afterUseCaseTrialTestsProcessor;
	private Tests4J_ProcessInfo processInfo;
	private TrialQueueDecisionTree trialQueueDecisionTree;
	private TrialState trialState;
	
	/**
	 * 
	 * @param p
	 * @param pNotificationManager
	 * @param pReporter
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public Tests4J_TrialsRunnable(Tests4J_Memory p, 
			I_Tests4J_NotificationManager pNotificationManager) {
		memory = p;
		trialQueueDecisionTree = p.getTrialQueueDecisionTree();
		processInfo = p.getTrialProcessInfo();
		notifier = pNotificationManager;
		
		log_ = p.getLog();
		threadManager = p.getThreadManager();
		
		testsRunner = new Tests4J_TestRunable(memory);
		testsRunner.setListener(this);
		testRunService = threadManager.createNewTestRunService();
		
		bindings = new TrialBindings(Platform.JSE, p.getEvaluationLookup(), log_);
		bindings.setAssertListener(testsRunner);
		
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
		try {
			processInfo.addRunnableStarted();
			outputDelegator.setDelegate(out);
			
			trialState = trialQueueDecisionTree.poll();
			while (trialState != null) {
				trialDescription = trialState.getTrialDescription();
				if (trialDescription != null) {
					Class<? extends I_AbstractTrial> trialClazz = trialState.getTrialClass();
					
					try {
						if ( !I_MetaTrial.class.isAssignableFrom(trialClazz)) {
							//start recording the trial coverage,
							//code cover the creation of the class, in the trialDescrption
							I_Tests4J_CoveragePlugin plugin = memory.getCoveragePlugin();
							if (plugin != null) {
								if (plugin.isCanThreadGroupLocalRecord()) {
									//@diagram sync on 7/5/2014
									// for Overview.seq 
									trialThreadLocalCoverageRecorder = plugin.createRecorder();
									trialThreadLocalCoverageRecorder.startRecording();
								}
							}
						}
						
					} catch (Exception x) {
						log_.onThrowable(x);
					} 
					
					
					if (trialDescription.isRunnable()) {
						if (trialDescription.getType() != TrialType.MetaTrial) {
							boolean printing = trialDescription.isPrintToStdOut(); 
							out.setPrinting(printing);
							
							try {
								//synchronized here so that each trial can only be running
								//once in all of the trial threads, having a trial running
								//in two threads at the same time would be confusing to the users
								synchronized (trialClazz) {
									if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
										log_.log("Thread " + Thread.currentThread().getName() +
												" is running trial;\n" +trialDescription.getTrialName());
									}
									runTrial();
								}	
							} catch (Exception x) {
								log_.onThrowable(x);
							} 
						}
					} 
				}
				if (trialDescription.getType() != TrialType.MetaTrial) {
					processInfo.addDone();
				}
				trialState.setFinishedRun();
				synchronized (trialState) {
					trialState.notifyAll();
				}
				trialState = trialQueueDecisionTree.poll();
			}
			trialName = null;
			processInfo.addRunnableFinished();
			testRunService.shutdownNow();
			finished = true;
		} catch (Exception x) {
			log_.onThrowable(x);
		}
		//its not on the main thread so join
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			log_.onThrowable(e);
		}
	}

	private void failTrialOnException(String message, Throwable p, TrialType type) {
		trialResultMutant.setPassed(false);
		String stack = StackTraceBuilder.toString(p, true);
		TrialFailure failure = new TrialFailure(message, stack);
		trialResultMutant.addFailure(failure);
		if (type != null) {
			trialResultMutant.setType(type);
		}
	}
	
	private void runTrial() throws RejectedExecutionException  {
		
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("running trial " + trialName);
		}
		apiTrialResultMutant = null;
		sourceFileTrialResultMutant = null;
		useCaseTrialResultMutant = null;
		
		trialName = trialDescription.getTrialName();
		trialName = trialName + "[" + trialState.getId() + "]";
		notifier.startingTrial(trialName);
		
		
		trial = trialDescription.getTrial();
		trial.setBindings(bindings);
		
		trialResultMutant = new BaseTrialResultMutant();
			
		I_TrialType type = trialDescription.getType();
		trialResultMutant.setType(type);
		trialResultMutant.setTrialName(trialName);
		runBeforeTrial();
		
		testsRunner.setTrial(trial);
		testsRunner.setCod(outputDelegator);
		testsRunner.setOut(out);
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("running trial tests " + trialName);
		}
		runTests();
		
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("finished trial tests" + trialName);
		}
		if (trialResultMutant.isPassed()) {
			//skip this method unless everything passed in the trial
			trialResultMutant = runAfterTrialTests();
		}
		runAfterTrial();
		
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("calculating trial results " + trialName);
		}
		
		onTrialCompleted();
	}

	public void onTrialCompleted() {
		I_TrialType type = trialDescription.getType();
		I_TrialResult result;
		TrialType tt = TrialType.get(type);
		switch (tt) {
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
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("notifying trial finished " + trialName);
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
		useCaseTrialResultMutant.setTrialName(trialDescription.getTrialName());
		return useCaseTrialResultMutant;
	}

	private ApiTrialResultMutant getApiTrialResult() {
		if (apiTrialResultMutant != null) {
			return apiTrialResultMutant;
		}
		ApiTrialResultMutant apiTrialTestResultMutant = new ApiTrialResultMutant(trialResultMutant);
		apiTrialTestResultMutant.setPackageName(trialDescription.getPackageName());
		apiTrialTestResultMutant.setTrialName(trialDescription.getTrialName());
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
		sourceFileTrialResultMutant.setTrialName(trialDescription.getTrialName());
		sourceFileTrialResultMutant.setTrialClassName(
				trialDescription.getTrialClass().getName());
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
			trialResultMutant.setBeforeTestOutput(out.getResults());
		}
	}

	/**
	 * 
	 * @return all suceeded
	 */
	private void runTests() throws RejectedExecutionException {
		Iterator<TestDescription> methods = trialDescription.getTestMethods();
		trialState.setTests(trialDescription.getTestMethodsSize());
		
		while (methods.hasNext()) {
			runTest(methods.next());
			trialState.addTestCompleted();
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
			testName_ = method.getName();
			notifier.startingTest(trialName, testName_);
			
			if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
				log_.log("starting test; " +trialName + "."+  method.getName());
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
						I_Tests4J_ResultMessages messages = Tests4J_Constants.CONSTANTS.getResultMessages();
						
						TestFailureMutant tfm = new TestFailureMutant();
						tfm.setFailureMessage(messages.getTestTimedOut());
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
		
		I_TrialType type = trialDescription.getType();
		
		TrialType tt = TrialType.get(type);
		switch (tt) {
			case SourceFileTrial:
				afterSouceFileTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				SourceFileTrialResultMutant result = getSourceFileTrialResult();
				
				//this must be run, in order to calculate code coverage, when there is a 
				//code coverage plugin
				TestResultMutant trm = afterSouceFileTrialTestsProcessor.afterSourceFileTrialTests(result);
				//add the default afterSourceFileTrialTests result
				if (trm != null) {
					result.addResult(trm);
				}
				
				if (memory.hasCoveragePlugin()) {
					TestResult minCoverageResult = afterSouceFileTrialTestsProcessor.testMinCoverage(result);
					result.addResult(minCoverageResult);
					trialState.addTestCompleted();
				
					TestResult dependencyResult = afterSouceFileTrialTestsProcessor.testDependencies(result);
					result.addResult(dependencyResult);
					
				}
				trialState.addTestCompleted();
				return result;
			case ApiTrial:
				afterApiTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				ApiTrialResultMutant apiResult = getApiTrialResult();
				TestResultMutant apiTrm = afterApiTrialTestsProcessor.afterApiTrialTests(
						apiResult, log_);
				if (apiTrm != null) {
					apiResult.addResult(apiTrm);
				}
				trialState.addTestCompleted();
				return apiResult;
			case UseCaseTrial:
				afterUseCaseTrialTestsProcessor.reset(trialDescription, 
						trialThreadLocalCoverageRecorder, trial);
				UseCaseTrialResultMutant useCaseResult = getUseCaseResult();
				TestResultMutant useCaseTrm = afterUseCaseTrialTestsProcessor.afterUseCaseTrialTests(useCaseResult);
				if (useCaseTrm != null) {
					useCaseResult.addResult(useCaseTrm);
				}
				trialState.addTestCompleted();
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
			trialResultMutant.setAfterTestOutput(out.getResults());
		}
	}
	
	@Override
	public void testFinished(I_TestResult p) {
		TestResultMutant forOut = new TestResultMutant(p);
		forOut.setOutput(out.getResults());
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

	public I_ConcurrentOutputDelegator getOutputDelegator() {
		return outputDelegator;
	}

	public void setOutputDelegator(I_ConcurrentOutputDelegator outputDelegator) {
		this.outputDelegator = outputDelegator;
	}

	@Override
	public synchronized I_Tests4J_TrialProgress getTrial() {
		if (trialState == null) {
			return null; 
		}
		
		return new Tests4J_TrialProgress(trialState.getTrialName(), trialState.getPctDone(), 
				this);
	}

	@Override
	public String getName() {
		return testName_;
	}

	@Override
	public double getPctDone() {
		return trial.getPctDone(testName_);
	}

}
