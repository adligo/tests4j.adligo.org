package org.adligo.jtests.run;

import org.adligo.jtests.base.shared.AbstractTest;
import org.adligo.jtests.base.shared.I_AbstractTest;
import org.adligo.jtests.models.shared.results.I_TestResult;
import org.adligo.jtests.models.shared.run.I_AllTestsDoneListener;
import org.adligo.jtests.models.shared.run.I_TestCompleteListener;
import org.adligo.jtests.models.shared.run.I_TestResultsProcessor;
import org.adligo.jtests.models.shared.run.RunParameters;

public class JTests implements I_TestCompleteListener, I_AllTestsDoneListener {
	public static final String J_TESTS_REQUIRES_A_I_TEST_RESULTS_PROCESSOR = "JTests requires a I_TestResultsProcessor.";

	private I_TestResultsProcessor testResultProcessor;
	
	RunParameters params;
	/*
	public static void main(String[] args) {
		
	}
	*/
	public static Thread run(RunParameters pParams, I_TestResultsProcessor pProcessor) {
		JTests jt = new JTests();
		return jt.runInternal(pParams, pProcessor);
	}
	
	private JTests() {}
	
	public Thread runInternal(final RunParameters pParams, I_TestResultsProcessor pProcessor) {
		testResultProcessor = pProcessor;
		if (testResultProcessor == null) {
			throw new IllegalArgumentException(J_TESTS_REQUIRES_A_I_TEST_RESULTS_PROCESSOR);
		}
		I_AllTestsDoneListener whenDone =  pParams.getAllTestsDoneListener();
		if (whenDone == null) {
			whenDone = this;
		}
		JTestInternalRunner runner = new JTestInternalRunner(
				pParams.getTests(),whenDone, this);
		
		runner.setSilent(pParams.isSilent());
		
		Thread t = new Thread(runner);
		
		t.start();
		return t;
	}

	@Override
	public void onTestCompleted(Class<? extends I_AbstractTest> testClass,
			I_AbstractTest test, I_TestResult result) {
		testResultProcessor.process(result);
	}

	@Override
	public void whenFinished() {
		System.exit(0);
	}
	

}
