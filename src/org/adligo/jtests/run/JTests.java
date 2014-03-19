package org.adligo.jtests.run;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.common.LineSeperator;
import org.adligo.jtests.models.shared.results.I_TrialResult;
import org.adligo.jtests.models.shared.results.I_TestRunResult;
import org.adligo.jtests.models.shared.system.I_TestResultsProcessor;
import org.adligo.jtests.models.shared.system.I_TestRunListener;
import org.adligo.jtests.models.shared.system.RunParameters;

public class JTests implements I_TestRunListener {private I_TestResultsProcessor testResultProcessor;
	
	RunParameters params;
	/*
	public static void main(String[] args) {
		
	}
	*/
	
	public static Thread run(RunParameters pParams, I_TestRunListener pProcessor) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		JTests jt = new JTests();
		return jt.runInternal(pParams, pProcessor);
	}
	
	public static Thread run(RunParameters pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		JTests jt = new JTests();
		return jt.runInternal(pParams, jt);
	}
	
	private JTests() {}
	
	private Thread runInternal(final RunParameters pParams, I_TestRunListener pListener) {
		JTestInternalRunner runner = new JTestInternalRunner(
				pParams.getTests(),pListener);
		
		runner.setSilent(pParams.isSilent());
		
		Thread t = new Thread(runner);
		
		t.start();
		return t;
	}

	@Override
	public void onTestCompleted(Class<? extends I_AbstractTrial> testClass,
			I_AbstractTrial test, I_TrialResult result) {
		testResultProcessor.process(result);
	}

	@Override
	public void onRunCompleted(I_TestRunResult result) {
		testResultProcessor.process(result);
		System.exit(0);
	}
	

}
