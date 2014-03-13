package org.adligo.jtests.run;

import org.adligo.jtests.models.shared.run.I_TestResultsProcessor;
import org.adligo.jtests.models.shared.run.RunParameters;

public class JTests implements Runnable, I_TestCompleteListener {
	private RunParameters params;
	
	/*
	public static void main(String[] args) {
		
	}
	*/
	public static void run(RunParameters pParams, I_TestResultsProcessor pProcessor) {
		JTests jt = new JTests(pParams, pProcessor);
		
	}
	
	private JTests(RunParameters pParams, I_TestResultsProcessor pProcessor) {
		params = pParams;
		SimpleRunner sr = new SimpleRunner();
		resultProcessor = pProcessor;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
