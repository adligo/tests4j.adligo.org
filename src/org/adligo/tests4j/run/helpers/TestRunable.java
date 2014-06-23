package org.adligo.tests4j.run.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.I_Trial;
import org.adligo.tests4j.models.shared.I_TrialProcessorBindings;
import org.adligo.tests4j.models.shared.asserts.I_AssertCommand;
import org.adligo.tests4j.models.shared.common.PlatformEnum;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

public class TestRunable implements Runnable, I_AssertListener,
I_TrialProcessorBindings {

	public static final String UNEXPECTED_EXCEPTION_WAS_THROWN = "Unexpected Exception was thrown.";
	private Method testMethod;
	private I_AbstractTrial trial;
	private I_TestFinishedListener listener;
	private CopyOnWriteArrayList<Integer> assertionHashes = new CopyOnWriteArrayList<Integer>(); 
	private I_Tests4J_Reporter reporter;
	private TestResultMutant testResultMutant;
	private boolean assertFailed = false;
	
	public TestRunable(I_Tests4J_Reporter pReporter) {
		reporter = pReporter;
	}
	
	@Override
	public void run() {
		testResultMutant = new TestResultMutant();
		assertionHashes.clear();
		
		if (reporter.isLogEnabled(TestRunable.class)) {
			reporter.log("running test " + trial.getClass().getName() + "." + testMethod.getName());
		}
		Throwable unexpected = null;
		try {
			
			testResultMutant.setName(testMethod.getName());
			
			assertFailed = false;
			testMethod.invoke(trial, new Object[] {});
			if (!assertFailed) {
				testResultMutant.setPassed(true);
			}
		} catch (InvocationTargetException e) {
			unexpected = e.getCause();
		} catch (Exception x) {
			unexpected = x;
		}
		flushAssertionHashes(testResultMutant);
		if (unexpected != null) {
			TestFailureMutant failure = new TestFailureMutant();
			failure.setException(unexpected);
			failure.setLocationFailed(unexpected);
			failure.setMessage(UNEXPECTED_EXCEPTION_WAS_THROWN);
			testResultMutant.setFailure(failure);
		
		}
		TestResult toRet = new TestResult(testResultMutant);
		if (reporter.isLogEnabled(TestRunable.class)) {
			reporter.log("notifying test finished " + trial.getClass().getName() + 
					"." + testMethod.getName() + " " + toRet.isPassed());
		}
		listener.testFinished(toRet);
	}

	public Method getTestMethod() {
		return testMethod;
	}

	public I_AbstractTrial getTrial() {
		return trial;
	}

	public I_TestFinishedListener getListener() {
		return listener;
	}

	public void setTestMethod(Method testMethod) {
		this.testMethod = testMethod;
	}

	public void setTrial(I_AbstractTrial trial) {
		this.trial = trial;
	}

	public void setListener(I_TestFinishedListener listener) {
		this.listener = listener;
	}
	
	public synchronized void assertCompleted(I_AssertCommand cmd) {
		assertionHashes.add(cmd.hashCode());
	}

	public synchronized void assertFailed(I_TestFailure failure) {
		testResultMutant.setFailure(failure);
		flushAssertionHashes(testResultMutant);
		TestResult tr = new TestResult(testResultMutant);
		assertFailed = true;
		listener.testFinished(tr);
	}

	private void flushAssertionHashes(TestResultMutant trm) {
		Iterator<Integer> it = assertionHashes.iterator();
		while (it.hasNext()) {
			trm.incrementAssertionCount(it.next());
		}
	}

	public TestResult getTestResult() {
		return new TestResult(testResultMutant);
	}

	@Override
	public I_AssertListener getAssertionListener() {
		return this;
	}

	@Override
	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}

	@Override
	public PlatformEnum getPlatform() {
		return PlatformEnum.JSE;
	}
}
