package org.adligo.tests4j.run;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

import org.adligo.tests4j.models.shared.AbstractTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.asserts.I_AssertCommand;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_TestFinishedListener;

public class TestRunable implements Runnable, I_AssertListener {

	public static final String UNEXPECTED_EXCEPTION_WAS_THROWN = "Unexpected Exception was thrown.";
	
	private Method testMethod;
	private I_AbstractTrial trial;
	private I_TestFinishedListener listener;
	private TestResult testResult;
	private CopyOnWriteArrayList<Integer> assertionHashes = new CopyOnWriteArrayList<Integer>(); 
	
	@Override
	public void run() {
		TestResultMutant testResultMutant = new TestResultMutant();
		assertionHashes.clear();
		
		Throwable unexpected = null;
		try {
			testResultMutant.setName(testMethod.getName());
			
			testResult = new TestResult(testResultMutant);
			testMethod.invoke(trial, new Object[] {});
			testResultMutant.setPassed(true);
			
		} catch (InvocationTargetException e) {
			unexpected = e.getCause();
		} catch (Exception x) {
			unexpected = x;
		}
		flushAssertionHashes(testResultMutant);
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
		assertionHashes.add(cmd.hashCode());
	}

	public synchronized void assertFailed(I_TestFailure failure) {
		TestResultMutant trm = new TestResultMutant(testResult);
		trm.setFailure(failure);
		flushAssertionHashes(trm);
		listener.testFinished(new TestResult(trm));
	}

	private void flushAssertionHashes(TestResultMutant trm) {
		Iterator<Integer> it = assertionHashes.iterator();
		while (it.hasNext()) {
			trm.incrementAssertionCount(it.next());
		}
	}

	public TestResult getTestResult() {
		return testResult;
	}
}
