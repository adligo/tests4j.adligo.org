package org.adligo.jtests.run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.asserts.I_AssertCommand;
import org.adligo.jtests.models.shared.results.I_TestFailure;
import org.adligo.jtests.models.shared.results.TestFailureMutant;
import org.adligo.jtests.models.shared.results.TestResult;
import org.adligo.jtests.models.shared.results.TestResultMutant;
import org.adligo.jtests.models.shared.system.I_AssertListener;
import org.adligo.jtests.models.shared.system.I_TestFinishedListener;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class JTestsInternalRunner implements Runnable, I_AssertListener {

	public static final String UNEXPECTED_EXCEPTION_WAS_THROWN = "Unexpected Exception was thrown.";
	
	private Method testMethod;
	private TestResultMutant testResultMutant = new TestResultMutant();
	private I_AbstractTrial trial;
	private I_TestFinishedListener listener;
	
	@Override
	public void run() {
		Throwable unexpected = null;
		try {
			testResultMutant.setName(testMethod.getName());
			
			testMethod.invoke(trial, new Object[] {});
			testResultMutant.setPassed(true);
			
		} catch (InvocationTargetException e) {
			unexpected = e.getCause();
		} catch (Exception x) {
			unexpected = x;
		}
		if (unexpected != null) {
			TestFailureMutant failure = new TestFailureMutant();
			failure.setException(unexpected);
			failure.setMessage(UNEXPECTED_EXCEPTION_WAS_THROWN);
			testResultMutant.setFailure(failure);
		
		}
		listener.testFinished(new TestResult(testResultMutant));
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
		testResultMutant.incrementAssertionCount();
		testResultMutant.addUniqueAsserts(cmd.hashCode());
	}

	public synchronized void assertFailed(I_TestFailure failure) {
		testResultMutant.setFailure(failure);
		listener.testFinished(new TestResult(testResultMutant));
		Thread.currentThread().interrupt();
	}

}
