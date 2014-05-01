package org.adligo.tests4j.run;

import org.adligo.tests4j.models.shared.common.LineSeperator;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;
import org.adligo.tests4j.run.helpers.TrialsProcessor;

/**
 * The main api to run tests for the Tests4J framework.
 * 
 * @author scott
 *
 */
public class Tests4J {
	public static final String NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED = "Null I_TestRunListener not allowed.";
	
	static {
		Thread.setDefaultUncaughtExceptionHandler(new Tests4J_UncaughtExceptionHandler());
	}
	
	/**
	 * run the trials/tests
	 * 
	 * @param pParams
	 * @param pListener
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public static void run(Tests4J_Params pParams, I_TrialRunListener pListener) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		if (pListener == null) {
			throw new IllegalArgumentException(NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED);
		}
		new TrialsProcessor(pParams,pListener);
	}
	
	/**
	 * run the trials/tests
	 * @param pParams
	 * 
	 */
	public static void run(Tests4J_Params pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		new TrialsProcessor(pParams,null);
	}
}
