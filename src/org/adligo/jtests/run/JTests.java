package org.adligo.jtests.run;

import org.adligo.jtests.base.shared.AbstractTest;
import org.adligo.jtests.base.shared.asserts.I_AbstractTest;
import org.adligo.jtests.models.shared.results.I_TestResult;
import org.adligo.jtests.models.shared.run.I_TestCompleteListener;
import org.adligo.jtests.models.shared.run.I_TestResultsProcessor;
import org.adligo.jtests.models.shared.run.RunParameters;

public class JTests implements I_TestCompleteListener {
	RunParameters params;
	/*
	public static void main(String[] args) {
		
	}
	*/
	public static void run(RunParameters pParams, I_TestResultsProcessor pProcessor) {
		JTests jt = new JTests(pParams, pProcessor);
		
	}
	
	private JTests(final RunParameters pParams,  final I_TestResultsProcessor pProcessor) {
		final SimpleRunner runner = new SimpleRunner(pParams.getTests(),
				pParams.isFailFast());
		AbstractTest.setListener(runner);
		
		new Thread(runner).start();
	}

	@Override
	public void onTestCompleted(Class<? extends I_AbstractTest> testClass,
			I_AbstractTest test, I_TestResult result) {
		// TODO Auto-generated method stub
		
	}
	

}
