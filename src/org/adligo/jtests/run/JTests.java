package org.adligo.jtests.run;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.common.LineSeperator;
import org.adligo.jtests.models.shared.results.I_TrialResult;
import org.adligo.jtests.models.shared.results.I_TrialRunResult;
import org.adligo.jtests.models.shared.system.I_TestResultsProcessor;
import org.adligo.jtests.models.shared.system.I_TestRunListener;
import org.adligo.jtests.models.shared.system.RunParameters;

public class JTests implements I_TestRunListener {private I_TestResultsProcessor testResultProcessor;
	
	RunParameters params;
	/*
	public static void main(String[] args) {
		
	}
	*/
	
	public static void run(RunParameters pParams, I_TestRunListener pProcessor) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		JTests jt = new JTests();
		jt.runInternal(pParams, pProcessor);
	}
	
	public static void run(RunParameters pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		JTests jt = new JTests();
		jt.runInternal(pParams, jt);
	}
	
	private JTests() {}
	
	private void runInternal(final RunParameters pParams, I_TestRunListener pListener) {
		TrialProcessor runner = new TrialProcessor(
				pParams.getTrials(),pListener);
		
		runner.setSilent(pParams.isSilent());
		runner.run();
	}

	@Override
	public void onTestCompleted(Class<? extends I_AbstractTrial> testClass,
			I_AbstractTrial test, I_TrialResult result) {
		testResultProcessor.process(result);
	}

	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		testResultProcessor.process(result);
		System.exit(0);
	}
	

}
