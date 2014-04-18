package org.adligo.tests4j.run;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialResult;
import org.adligo.tests4j.models.shared.results.TrialResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

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
		I_CoveragePlugin plugin = memory.getPlugin();
		I_CoverageRecorder trialCoverageRecorder = null;
		if (plugin != null) {
			String name = trialClazz.getName();
			/*
			trialCoverageRecorder = plugin.createRecorder(name);
			memory.addRecorder(name, trialCoverageRecorder);
			trialCoverageRecorder.startRecording();
			*/
		}
		
		TrialDescription desc = new TrialDescription(trialClazz, memory.getLog());
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
		if (trialCoverageRecorder != null) {
			trialCoverageRecorder.stopRecording();
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
		String trialName = trialDescription.getTrialName();
		notifier.startingTrial(trialName);
		I_CoverageRecorder trialCoverageRecorder = memory.getRecorder(trialName);
		if (trialCoverageRecorder != null) {
			trialCoverageRecorder.startRecording();
		}
		
		trial = trialDescription.getTrial();
		trial.setListener(testsRunner);
		
		trialResultMutant = new TrialResultMutant();
		trialResultMutant.setType(trialDescription.getType());
		trialResultMutant.setTrialName(trialDescription.getTrialName());
		runBeforeTrial();
		testsRunner.setTrial(trial);
		runTests();
		runAfterTrial();
		if (trialCoverageRecorder != null) {
			List<I_PackageCoverage> coverage = trialCoverageRecorder.getCoverage();
			//TODO
			//trialResultMutant.setCoverage(coverage);
		}
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
			
			I_CoveragePlugin plugin = memory.getPlugin();
			I_CoverageRecorder testCoverageRecorder = null;
			if (plugin != null) {
				String name = trialDescription.getTrialName() + 
						"." + tm.getMethod().getName();
				testCoverageRecorder = plugin.createRecorder(name);
				memory.addRecorder(name, testCoverageRecorder);
				testCoverageRecorder.startRecording();
			}
			
			
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
					if (testCoverageRecorder != null) {
						List<I_PackageCoverage> coverage = testCoverageRecorder.getCoverage();
						//TODO
						//trm.setCoverage(coverage);
					}
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
