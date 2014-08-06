package org.adligo.tests4j.run.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_TestFailure;
import org.adligo.tests4j.models.shared.asserts.common.TestFailure;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureMutant;
import org.adligo.tests4j.models.shared.common.StackTraceBuilder;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_AssertListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.I_Tests4J_TestFinishedListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

/**
 * runs a single test in a thread,
 * which implements the @Test timeout annotation parameter.
 * 
 * @author scott
 *
 */
public class Tests4J_TestRunable implements Runnable, I_Tests4J_AssertListener {

	private Method testMethod;
	private I_AbstractTrial trial;
	private I_Tests4J_TestFinishedListener listener;
	private List<Integer> assertionHashes = new ArrayList<Integer>(); 
	private I_Tests4J_Log reporter;
	private TestResultMutant testResultMutant;
	private boolean assertFailed = false;
	
	public Tests4J_TestRunable(Tests4J_Memory pMemory) {
		reporter = pMemory.getLogger();
	}
	
	@Override
	public void run() {
		testResultMutant = new TestResultMutant();
		assertionHashes.clear();
		
		if (reporter.isLogEnabled(Tests4J_TestRunable.class)) {
			reporter.log("running test " + trial.getClass().getName() + "." + testMethod.getName());
		}
		Throwable unexpected = null;
		try {
			
			testResultMutant.setName(testMethod.getName());
			
			assertFailed = false;
			//todo isolated try catch Exception around this next method
			trial.beforeTests();
			testMethod.invoke(trial, new Object[] {});
			
			if (!assertFailed) {
				testResultMutant.setPassed(true);
			}
		} catch (Exception x) {
			Throwable cause = x.getCause();
			if (cause instanceof AssertionFailedException) {
				//a assertion failed, which is expected
			} else if (cause instanceof InvocationTargetException ) {
					unexpected = x.getCause();
			} else {
				unexpected = x;
			}
		}
		//todo try catch Exception around this next method
		trial.afterTests();
		
		flushAssertionHashes(testResultMutant);
		if (unexpected != null) {
			TestFailureMutant failure = new TestFailureMutant();
			String trace = StackTraceBuilder.toString(unexpected, true);
			
			failure.setFailureDetail(trace);
			I_Tests4J_ResultMessages messages = 
						Tests4J_Constants.CONSTANTS.getResultMessages();
			failure.setFailureMessage(messages.getAnUnexpectedExceptionWasThrown());
			TestFailure immutable = new TestFailure(failure);
			testResultMutant.setFailure(immutable);
		
		}
		TestResult toRet = new TestResult(testResultMutant);
		if (reporter.isLogEnabled(Tests4J_TestRunable.class)) {
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

	public I_Tests4J_TestFinishedListener getListener() {
		return listener;
	}

	public void setTestMethod(Method testMethod) {
		this.testMethod = testMethod;
	}

	public void setTrial(I_AbstractTrial trial) {
		this.trial = trial;
	}

	public void setListener(I_Tests4J_TestFinishedListener listener) {
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
		throw new AssertionFailedException();
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

}
