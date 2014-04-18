package org.adligo.tests4j.run;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialResult;
import org.adligo.tests4j.models.shared.results.TrialResultMutant;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;

public class TrialInstanceProcessor implements Runnable, I_TestFinishedListener {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";

	private Tests4J_Memory memory;
	private NotificationManager notifier;
	private TrialDescription trialDescription;
	private I_AbstractTrial trial;
	private TrialResultMutant trialResultMutant;
	
	private ExecutorService testRunService;
	private ArrayBlockingQueue<Boolean> blocking = new ArrayBlockingQueue<Boolean>(1);
	private TestRunable testsRunner;
	
	public TrialInstanceProcessor(Tests4J_Memory p, NotificationManager pNotificationManager) {
		memory = p;
		notifier = pNotificationManager;
		testsRunner = new TestRunable();
		testsRunner.setListener(this);
		testRunService = Executors.newSingleThreadExecutor();
		
	}

	@Override
	public void run() {
		try {
			Class<? extends I_AbstractTrial> trialClazz = memory.pollTrial();
			while (trialClazz != null) {
			
				addTrialDescription(trialClazz);
				trialClazz = memory.pollTrial();
			}
			notifier.checkDoneDescribingTrials();
			
			trialDescription = memory.pollDescriptions();
			while (trialDescription != null) {
				runTrial();
				trialDescription = memory.pollDescriptions();
			}
			testRunService.shutdownNow();
			notifier.checkDoneRunningTrials();
		} catch (Exception x) {
			x.printStackTrace(Tests4J_Memory.INITAL_OUT);
		}
	}

	private void addTrialDescription(Class<? extends I_AbstractTrial> trialClazz) {
		TrialDescription desc = new TrialDescription(trialClazz);
		memory.add(desc);
		if (!desc.isIgnored()) {
			if (!desc.isTrialCanRun()) {
				TrialResultMutant trm = new TrialResultMutant();
				trm.setTrialName(desc.getTrialName());
				String failureMessage = desc.getResultFailureMessage();
				if (failureMessage != null) {
					TrialFailure tf = new TrialFailure(failureMessage, desc.getResultException());
					trm.setFailure(tf);
				}
				trm.setType(desc.getType());
				trm.setPassed(false);
				memory.add(new TrialResult(trm));
			}
		}
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
		notifier.startingTrial(trialDescription.getTrialName());
		trial = trialDescription.getTrial();
		trial.setListener(testsRunner);
		
		trialResultMutant = new TrialResultMutant();
		trialResultMutant.setType(trialDescription.getType());
		trialResultMutant.setTrialName(trialDescription.getTrialName());
		runBeforeTrial();
		testsRunner.setTrial(trial);
		runTests();
		runAfterTrial();
		TrialResult result = new TrialResult(trialResultMutant); 
		notifier.trialDone(result);
		trialResultMutant = null;
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
		List<TestMethod> methods = trialDescription.getTestMethods();
		
		for(TestMethod tm: methods) {
			runTest(tm);
		}
		
	}

	private void runTest(TestMethod tm) {
		Method method = tm.getMethod();
		blocking.clear();
		if (tm.isIgnore()) {
			TestResultMutant trm = new TestResultMutant();
			trm.setName(method.getName());
			trm.setIgnored(true);
			trialResultMutant.addResult(trm);
		} else {
			notifier.startingTest(trialDescription.getTrialName() + "." + method.getName());
			
			trial.beforeTests();
			testsRunner.setTestMethod(method);
			testRunService.execute(testsRunner);
			
			try {
				Boolean result = blocking.poll(tm.getTimeoutMillis(), TimeUnit.MILLISECONDS);
				if (result == null) {
					TestResultMutant trm = new TestResultMutant(
							testsRunner.getTestResultMutant());
					TestFailureMutant tfm = new TestFailureMutant();
					String message = "Test Timedout at " + tm.getTimeoutMillis() + " milliseconds.";
					tfm.setMessage(message);
					tfm.setException(new IllegalStateException(message));
					trm.setFailure(new TestFailure(tfm));
					trialResultMutant.addResult(new TestResult(trm));
				}
			} catch (InterruptedException x) {
				//do nothing
			}
			trial.afterTests();
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
	public synchronized void testFinished(TestResult p) {
		TestResultMutant forOut = new TestResultMutant(p);
		forOut.setOutput(memory.getThreadLocalOutput());
		trialResultMutant.addResult(new TestResult(forOut));
		try {
			blocking.put(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
