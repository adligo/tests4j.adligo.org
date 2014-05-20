package org.adligo.tests4j.run;

import org.adligo.tests4j.models.shared.common.LineSeperator;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_DelegateFactory;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;
import org.adligo.tests4j.run.helpers.DefaultDelegateFactory;

/**
 * The main api to run tests for the Tests4J framework.
 * 
 * @author scott
 *
 */
public class Tests4J {
	public static final String NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED = "Null I_TestRunListener not allowed.";
	private static I_Tests4J_DelegateFactory FACTORY = new DefaultDelegateFactory();
	
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
	public static I_Tests4J_Controls run(Tests4J_Params pParams, I_TrialRunListener pListener) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		if (pListener == null) {
			throw new IllegalArgumentException(NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED);
		}
		I_Tests4J_Delegate delegate =  FACTORY.create();
		delegate.run(pListener,pParams);
		return delegate.getControls();
	}
	
	/**
	 * run the trials/tests
	 * @param pParams
	 * 
	 */
	public static I_Tests4J_Controls run(Tests4J_Params pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		I_Tests4J_Delegate delegate =  FACTORY.create();
		delegate.run(null,pParams);
		return delegate.getControls();
	}
	
	public static synchronized void setFactory(I_Tests4J_DelegateFactory pFactory) {
		FACTORY = pFactory;
	}
	
	public static I_Tests4J_DelegateFactory getFactory() {
		return FACTORY;
	}
}
