package org.adligo.tests4j.run;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.asserts.AssertionHelperInfo;
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

public class TrialInstancesProcessor implements Runnable, I_TestFinishedListener {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
			"Unexpected exception thrown from ";

	private Tests4J_Memory memory;
	private Tests4J_NotificationManager notifier;
	private TrialDescription trialDescription;
	private I_AbstractTrial trial;
	private TrialResultMutant trialResultMutant;
	
	private ExecutorService testRunService;
	private ArrayBlockingQueue<TestResult> blocking = new ArrayBlockingQueue<TestResult>(1);
	private TestRunable testsRunner;
	
	public TrialInstancesProcessor(Tests4J_Memory p, Tests4J_NotificationManager pNotificationManager) {
		memory = p;
		notifier = pNotificationManager;
		testsRunner = new TestRunable();
		testsRunner.setListener(this);
		testRunService = Executors.newSingleThreadExecutor();
	}

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
			memory.getLog().log(x);
			notifier.onDescibeTrialError();
			return;
		} catch (Error x) {
			memory.getLog().log(x);
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
			memory.getLog().log(x);
		} catch (Error x) {
			memory.getLog().log(x);
		}
	}

	private void addTrialDescription(Class<? extends I_AbstractTrial> trialClazz) {
		
		I_CoveragePlugin plugin = memory.getPlugin();
		I_CoverageRecorder trialCoverageRecorder = null;
		if (plugin != null) {
			if (Boolean.TRUE == memory.getRecordSeperateTrialCoverage()) {
				String name = trialClazz.getName();
				trialCoverageRecorder = plugin.createRecorder(name);
				memory.addRecorder(name, trialCoverageRecorder);
				trialCoverageRecorder.startRecording();
			}
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
		AssertionHelperInfo atm = new AssertionHelperInfo();
		atm.setCoveragePlugin(memory.getPlugin());
		atm.setListener(testsRunner);
		
		trial.setMemory(atm);
		
		trialResultMutant = new TrialResultMutant();
			
		trialResultMutant.setType(trialDescription.getType());
		trialResultMutant.setTrialName(trialDescription.getTrialName());
		runBeforeTrial();
		testsRunner.setTrial(trial);
		runTests();
		if (trialCoverageRecorder != null) {
			List<I_PackageCoverage> coverage = trialCoverageRecorder.getCoverage();
			//TODO
			//trialResultMutant.setCoverage(coverage);
		}
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
		Iterator<TestDescription> methods = trialDescription.getTestMethods();
		
		while (methods.hasNext()) {
			runTest(methods.next());
		}
		
	}

	private void runTest(TestDescription tm) {
		I_CoveragePlugin plugin = memory.getPlugin();
		I_CoverageRecorder testCoverageRecorder = null;
		if (plugin != null) {
			if (Boolean.TRUE == memory.getRecordSeperateTestCoverage()) {
				String name = trial.getClass().getName() + tm.getMethod().getName();
				testCoverageRecorder = plugin.createRecorder(name);
				memory.addRecorder(name, testCoverageRecorder);
				testCoverageRecorder.startRecording();
			}
		}
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
				Long timeout = tm.getTimeoutMillis();
				if (timeout == 0L) {
					TestResult result = blocking.take();
					trialResultMutant.addResult(result);
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
						if (testCoverageRecorder != null) {
							List<I_PackageCoverage> coverage = testCoverageRecorder.getCoverage();
							//TODO
							//trm.setCoverage(coverage);
						}
						trialResultMutant.addResult(new TestResult(trm));
					}
				}
			} catch (InterruptedException x) {
				//do nothing
			}
			trial.afterTests();
			if (testCoverageRecorder != null) {
				List<I_PackageCoverage> coverage = testCoverageRecorder.getCoverage();
				//TODO
				//trialResultMutant.setCoverage(coverage);
			}
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
		TestResult result = new TestResult(forOut);
		
		try {
			blocking.put(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
