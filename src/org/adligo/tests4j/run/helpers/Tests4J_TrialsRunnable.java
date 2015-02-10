package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResultMutant;
import org.adligo.tests4j.run.common.I_JseSystem;
import org.adligo.tests4j.run.common.I_ThreadManager;
import org.adligo.tests4j.run.common.I_Threads;
import org.adligo.tests4j.run.common.ThreadsDelegate;
import org.adligo.tests4j.run.discovery.TestDescription;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.run.discovery.TrialQueueDecisionTree;
import org.adligo.tests4j.run.discovery.TrialState;
import org.adligo.tests4j.run.memory.Tests4J_Memory;
import org.adligo.tests4j.run.output.TrialOutput;
import org.adligo.tests4j.shared.asserts.common.TestFailure;
import org.adligo.tests4j.shared.asserts.common.TestFailureMutant;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.Platform;
import org.adligo.tests4j.shared.common.StackTraceBuilder;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.shared.output.I_ConcurrentOutputDelegator;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Runnable;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TestFinishedListener;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;
import org.adligo.tests4j.system.shared.api.Tests4J_TrialProgress;
import org.adligo.tests4j.system.shared.trials.BeforeTrial;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_Progress;
import org.adligo.tests4j.system.shared.trials.I_SourceFileTrial;
import org.adligo.tests4j.system.shared.trials.TrialBindings;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

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
	
	private Tests4J_Memory memory_;
	private I_Tests4J_Constants constants_;
	private I_ThreadManager threadManager_;
	private Map<String,Object> beforeTrialParams_;
	private I_Tests4J_NotificationManager notifier_;
	private TrialDescription trialDescription_;
	private I_Tests4J_Log log_;
	private I_AbstractTrial trial_;
	private BaseTrialResultMutant trialResultMutant_;
	
	private ExecutorService testRunService;
	private Future<?> testResultFuture;
	private ArrayBlockingQueue<TestResult> blocking = new ArrayBlockingQueue<TestResult>(100);
	private Tests4J_TestRunable testsRunner;
	private boolean finished = false;
	private ApiTrialResultMutant apiTrialResultMutant;
	private SourceFileTrialResultMutant sourceFileTrialResultMutant_;
	private UseCaseTrialResultMutant useCaseTrialResultMutant;
	private TrialBindings bindings;
	private TrialOutput out = new TrialOutput();
	private I_ConcurrentOutputDelegator outputDelegator;
	
	private String trialName;
	private String testName_;
	private I_Threads threads_ = new ThreadsDelegate();
	private I_Tests4J_CoverageRecorder trialThreadLocalCoverageRecorder;
	private AfterSourceFileTrialTestsProcessor afterSouceFileTrialTestsProcessor;
	private AfterApiTrialTestsProcessor afterApiTrialTestsProcessor;
	private AfterUseCaseTrialTestsProcessor afterUseCaseTrialTestsProcessor;
	private Tests4J_PhaseOverseer processInfo;
	private TrialQueueDecisionTree trialQueueDecisionTree;
	private volatile TrialState trialState;
	
	/**
	 * 
	 * @param p
	 * @param pNotificationManager
	 * @param pReporter
	 * 
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	public Tests4J_TrialsRunnable(Tests4J_Memory p, 
			I_Tests4J_NotificationManager pNotificationManager) {
		memory_ = p;
		constants_ = memory_.getConstants();
		trialQueueDecisionTree = p.getTrialQueueDecisionTree();
		processInfo = p.getTrialPhaseOverseer();
		notifier_ = pNotificationManager;
		
		log_ = p.getLog();
		threadManager_ = p.getThreadManager();
		
		testsRunner = new Tests4J_TestRunable(memory_);
		testsRunner.setListener(this);
		
		
		bindings = new TrialBindings(Platform.JSE, constants_, p.getEvaluationLookup(), log_);
		bindings.setAssertListener(testsRunner);
		
		afterSouceFileTrialTestsProcessor = new AfterSourceFileTrialTestsProcessor(memory_);
		afterApiTrialTestsProcessor = new AfterApiTrialTestsProcessor(memory_);
		afterUseCaseTrialTestsProcessor = new AfterUseCaseTrialTestsProcessor(memory_);
		
		beforeTrialParams_ = new HashMap<String,Object>();
		beforeTrialParams_.put(BeforeTrial.THREAD_FACTORY, threadManager_.getCustomThreadFactory());
		beforeTrialParams_ = Collections.unmodifiableMap(beforeTrialParams_);
	}

	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 * 
	 */
	@Override
	public void run() {
		try {
		  testRunService = threadManager_.createNewTestRunService();
			processInfo.addRunnableStarted();
			outputDelegator.setDelegate(out);
			
			trialState = trialQueueDecisionTree.poll();
			while (trialState != null) {
				trialDescription_ = trialState.getTrialDescription();
				if (trialDescription_ != null) {
					Class<? extends I_AbstractTrial> trialClazz = trialState.getTrialClass();
					
					try {
						if ( !I_MetaTrial.class.isAssignableFrom(trialClazz)) {
							//start recording the trial coverage,
							//code cover the creation of the class, in the trialDescrption
							I_Tests4J_CoveragePlugin plugin = memory_.getCoveragePlugin();
							if (plugin != null) {
								if (plugin.isCanThreadGroupLocalRecord()) {
								  Thread thread = threads_.currentThread();
								  String threadName = thread.getName();
								  String filter = null;
								  I_TrialType trialType = trialDescription_.getType();
								  TrialType type = TrialType.get(trialType);
								  switch (type) {
								    case SourceFileTrial:
								        Class<?> clazz = trialDescription_.getSourceFileClass();
								        filter = clazz.getName();
								      break;
								    case ApiTrial:
                        filter = trialDescription_.getPackageName();
                      break;
								    default: //donothing
								  }
								  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
									trialThreadLocalCoverageRecorder = plugin.createRecorder(threadName, filter);
									//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
									trialThreadLocalCoverageRecorder.startRecording();
								}
							}
						}
						
					} catch (Exception x) {
						log_.onThrowable(x);
					} 
					
					
					if (trialDescription_.isRunnable()) {
						if (trialDescription_.getType() != TrialType.MetaTrial) {
							boolean printing = trialDescription_.isPrintToStdOut(); 
							out.setPrinting(printing);
							
							try {
								//synchronized here so that each trial can only be running
								//once in all of the trial threads, having a trial running
								//in two threads at the same time would be confusing to the users
								synchronized (trialClazz) {
									if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
										log_.log("Thread " + Thread.currentThread().getName() +
												" is running trial;\n" +trialDescription_.getTrialName());
									}
									runTrial();
								}	
							} catch (Exception x) {
								log_.onThrowable(x);
							} 
						}
					} 
				}
				if (trialDescription_.getType() != TrialType.MetaTrial) {
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
		trialResultMutant_.setPassed(false);
		String stack = new StackTraceBuilder().toString(p);
		TrialFailure failure = new TrialFailure(message, stack);
		trialResultMutant_.addFailure(failure);
		if (type != null) {
			trialResultMutant_.setType(type);
		}
	}
	
	private void runTrial() throws RejectedExecutionException  {
		
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("running trial " + trialName);
		}
		apiTrialResultMutant = null;
		sourceFileTrialResultMutant_ = null;
		useCaseTrialResultMutant = null;
		
		trialName = trialDescription_.getTrialName();
		trialName = trialName + "[" + trialState.getId() + "]";
		notifier_.startingTrial(trialName);
		
		
		trial_ = trialDescription_.getTrial();
		trial_.setBindings(bindings);
		
		trialResultMutant_ = new BaseTrialResultMutant();
			
		I_TrialType type = trialDescription_.getType();
		trialResultMutant_.setType(type);
		trialResultMutant_.setTrialName(trialName);
		trialResultMutant_.setRunNumber(trialState.getId());
		runBeforeTrial();
		
		testsRunner.setTrial(trial_);
		testsRunner.setCod(outputDelegator);
		testsRunner.setOut(out);
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("running trial tests " + trialName);
		}
		runTests();
		
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("finished trial tests" + trialName);
		}
		if (trialResultMutant_.isPassed()) {
			//skip this method unless everything passed in the trial
			trialResultMutant_ = runAfterTrialTests();
		}
		runAfterTrial();
		
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("calculating trial results " + trialName);
		}
		
		onTrialCompleted();
	}

	public void onTrialCompleted() {
		I_TrialType type = trialDescription_.getType();
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
					sourceFileTrialResultMutant_ = getSourceFileTrialResult();
					result = new SourceFileTrialResult(sourceFileTrialResultMutant_);
				break;
			default:
				result = new BaseTrialResult(trialResultMutant_);
		}
		if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
			log_.log("notifying trial finished " + trialName);
		}
		notifier_.onTrialCompleted(result);
	}

	private UseCaseTrialResultMutant getUseCaseResult() {
		if (useCaseTrialResultMutant != null) {
			return useCaseTrialResultMutant;
		}
		useCaseTrialResultMutant = new UseCaseTrialResultMutant(trialResultMutant_);
		useCaseTrialResultMutant.setTrialName(trialDescription_.getTrialName());
		return useCaseTrialResultMutant;
	}

	private ApiTrialResultMutant getApiTrialResult() {
		if (apiTrialResultMutant != null) {
			return apiTrialResultMutant;
		}
		ApiTrialResultMutant apiTrialTestResultMutant = new ApiTrialResultMutant(trialResultMutant_);
		apiTrialTestResultMutant.setPackageName(trialDescription_.getPackageName());
		apiTrialTestResultMutant.setTrialName(trialDescription_.getTrialName());
		return apiTrialTestResultMutant;
	}

	/**
	 * clones (copy constructor) the current
	 * testResultMutant adds the source file class
	 * and returns.
	 * @return
	 */
	private SourceFileTrialResultMutant getSourceFileTrialResult() {
		if (sourceFileTrialResultMutant_ != null) {
			return sourceFileTrialResultMutant_;
		}
		SourceFileTrialResultMutant sourceFileTrialResultMutant = new SourceFileTrialResultMutant(trialResultMutant_);
		Class<?> clazz = trialDescription_.getSourceFileClass();
		sourceFileTrialResultMutant.setSourceFileName(clazz.getName());
		sourceFileTrialResultMutant.setTrialName(trialDescription_.getTrialName());
		sourceFileTrialResultMutant.setTrialClassName(
				trialDescription_.getTrialClass().getName());
		return sourceFileTrialResultMutant;
	}
	
	private void runBeforeTrial()  {
		Method beforeTrial = trialDescription_.getBeforeTrialMethod();
		if (beforeTrial != null) {
			try {
				beforeTrial.invoke(trial_, new Object[] {beforeTrialParams_});
			} catch (Exception e) {
				failTrialOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + 
						trialDescription_.getTrialName() + 
						"." + beforeTrial.getName(), e, null);
			}
			trialResultMutant_.setBeforeTestOutput(out.getResults());
		}
	}

	/**
	 * 
	 * @return all suceeded
	 */
	private void runTests() throws RejectedExecutionException {
		Iterator<TestDescription> methods = trialDescription_.getTestMethods();
		trialState.setTests(trialDescription_.getTestMethodsSize());
		
		while (methods.hasNext()) {
			runTest(methods.next());
			trialState.addTestCompleted();
		}
	}

	@SuppressWarnings("boxing")
  private void runTest(TestDescription tm) throws RejectedExecutionException {
		Method method = tm.getMethod();
		blocking.clear();
		
		if (memory_.hasTestsFilter()) {
			Class<?> trialClazz = trialDescription_.getTrialClass();
			if (!memory_.runTest(trialClazz, method.getName())) {
				return;
			}
		}

		if (tm.isIgnore()) {
			TestResultMutant trm = new TestResultMutant();
			trm.setName(method.getName());
			trm.setIgnored(true);
			trialResultMutant_.addResult(trm);
		} else {
			testName_ = method.getName();
			notifier_.startingTest(trialName, testName_);
			
			if (log_.isLogEnabled(Tests4J_TrialsRunnable.class)) {
				log_.log("starting test; " +trialName + "."+  method.getName());
			}
			
			//trial.beforeTests();
			testsRunner.setTestMethod(method);
			testResultFuture = testRunService.submit(testsRunner);
			
			threadManager_.setTestRunFuture(testRunService, testResultFuture);
			
			try {
				Long timeout = tm.getTimeoutMillis();
				if (timeout == 0L) {
					TestResult result = blocking.take();
					trialResultMutant_.addResult(result);
					notifier_.onTestCompleted(trialName, method.getName(), result.isPassed());
				} else {
					TestResult result = blocking.poll(tm.getTimeoutMillis(), TimeUnit.MILLISECONDS);
					if (result != null) {
						trialResultMutant_.addResult(result);
					} else {
						TestResultMutant trm = new TestResultMutant(
								testsRunner.getTestResult());
						I_Tests4J_ResultMessages messages = constants_.getResultMessages();
						
						TestFailureMutant tfm = new TestFailureMutant();
						tfm.setFailureMessage(messages.getTestTimedOut());
						trm.setFailure(new TestFailure(tfm));
					
						trialResultMutant_.addResult(new TestResult(trm));
					}
					//TODO write more tests for timeout, there was a bug 2/10/2015
					notifier_.onTestCompleted(trialName, method.getName(), false);
				}
			} catch (InterruptedException x) {
			  I_JseSystem sys = (I_JseSystem)  memory_.getSystem();
			  sys.interruptCurrentThread();
			}
			//trial.afterTests();
		}
	}
	
	private BaseTrialResultMutant runAfterTrialTests() {
		
		I_TrialType type = trialDescription_.getType();
		
		TrialType tt = TrialType.get(type);
		switch (tt) {
			case SourceFileTrial:
				afterSouceFileTrialTestsProcessor.reset(trialDescription_, 
						trialThreadLocalCoverageRecorder, trial_);
				SourceFileTrialResultMutant result = getSourceFileTrialResult();
				sourceFileTrialResultMutant_ = result; 
				//this must be run, in order to calculate code coverage, when there is a 
				//code coverage plugin
				TestResultMutant trm = afterSouceFileTrialTestsProcessor.afterSourceFileTrialTests(result);
				//add the default afterSourceFileTrialTests result
				if (trm != null) {
					result.addResult(trm);
				}
				
				if (memory_.hasCoveragePlugin()) {
					if (trialDescription_.getReferenceGroupAggregate() != null) {
						TestResult referencesResult = afterSouceFileTrialTestsProcessor.testReferences(result);
						result.addResult(referencesResult);
						notifier_.onTestCompleted(trialName, I_SourceFileTrial.TEST_REFERENCES, referencesResult.isPassed());
						trialState.addTestCompleted();
					}
					TestResult dependencyResult = afterSouceFileTrialTestsProcessor.testDependencies(result);
					result.addResult(dependencyResult);
					notifier_.onTestCompleted(trialName, I_SourceFileTrial.TEST_DEPENDENCIES, dependencyResult.isPassed());
					trialState.addTestCompleted();
					
					TestResult minCoverageResult = afterSouceFileTrialTestsProcessor.testMinCoverage(result);
					result.addResult(minCoverageResult);
					notifier_.onTestCompleted(trialName, I_SourceFileTrial.TEST_MIN_COVERAGE, minCoverageResult.isPassed());
					trialState.addTestCompleted();
					
				}
				trialState.addTestCompleted();
				return result;
			case ApiTrial:
				afterApiTrialTestsProcessor.reset(trialDescription_, 
						trialThreadLocalCoverageRecorder, trial_);
				ApiTrialResultMutant apiResult = getApiTrialResult();
				TestResultMutant apiTrm = afterApiTrialTestsProcessor.afterApiTrialTests(
						apiResult, log_);
				if (apiTrm != null) {
					apiResult.addResult(apiTrm);
				}
				trialState.addTestCompleted();
				return apiResult;
			case UseCaseTrial:
				afterUseCaseTrialTestsProcessor.reset(trialDescription_, 
						trialThreadLocalCoverageRecorder, trial_);
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
		Method afterTrial = trialDescription_.getAfterTrialMethod();
		if (afterTrial != null) {
			try {
				afterTrial.invoke(trial_, new Object[] {});
			} catch (Exception e) {
				failTrialOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + 
						trialName + 
						"." + afterTrial.getName(), e, null);
			}
			trialResultMutant_.setAfterTestOutput(out.getResults());
		}
	}
	
	@Override
	public void testFinished(I_TestResult p) {
		TestResultMutant forOut = new TestResultMutant(p);
		forOut.setOutput(out.getResults());
		TestResult result = new TestResult(forOut);
		
		blocking.add(result);
		
		if (p.isPassed()) {
		  //here is some interesting code, this call to
	    // blocking.notify() occurs on the test thread
	    //and throws a IllegalMonitorState exception,
	    //which seems to help the test thread
		  //communicate to the trial thread that it is done
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


	public I_ConcurrentOutputDelegator getOutputDelegator() {
		return outputDelegator;
	}

	public void setOutputDelegator(I_ConcurrentOutputDelegator outputDelegator) {
		this.outputDelegator = outputDelegator;
	}

	@Override
	public I_Tests4J_TrialProgress getTrial() {
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
		return trial_.getPctDone(testName_);
	}

}
