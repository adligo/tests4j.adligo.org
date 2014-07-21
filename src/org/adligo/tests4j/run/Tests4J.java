package org.adligo.tests4j.run;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.DefaultLogger;
import org.adligo.tests4j.models.shared.system.DefaultSystem;
import org.adligo.tests4j.models.shared.system.I_System;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_DelegateFactory;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.run.helpers.DefaultDelegateFactory;

/**
 * The main api to run tests for the Tests4J framework.
 * 
 * @author scott
 *
 */
public class Tests4J {
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
	public static I_Tests4J_Controls run(I_Tests4J_Params pParams, I_TrialRunListener pListener) {
		if (pListener == null) {
			I_Tests4J_Constants messages = Tests4J_Constants.CONSTANTS;
			throw new IllegalArgumentException(messages.getTests4J_NullListenerExceptionMessage());
		}
		I_System system = new DefaultSystem();
		//sets up logging for the run, from the params
		DefaultLogger logger = new DefaultLogger(new DefaultSystem(), pParams);
		Tests4J instance = new Tests4J();
		instance.setSystem(system);
		instance.setLogger(logger);
		return instance.instanceRun(pParams, pListener);
	}
	
	/**
	 * run the trials/tests
	 * @param pParams
	 * 
	 */
	public static I_Tests4J_Controls run(I_Tests4J_Params pParams) {
		I_System system = new DefaultSystem();
		//sets up logging for the run, from the params
		DefaultLogger logger = new DefaultLogger(new DefaultSystem(), pParams);
		Tests4J instance = new Tests4J();
		instance.setSystem(system);
		instance.setLogger(logger);
		return instance.instanceRun(pParams, null);
	}
	
	public static synchronized void setFactory(I_Tests4J_DelegateFactory pFactory) {
		FACTORY = pFactory;
	}
	
	public static I_Tests4J_DelegateFactory getFactory() {
		return FACTORY;
	}
	
	protected Tests4J() {
		//not part of the public api, 
		//but used when tests4j tests itself @see 
	}
	
	private I_Tests4J_Logger logger;
	private I_System system;
	/**
	 * not part of the public api,
	 * but used when test4j tests itself.
	 * 
	 * @param pParmas
	 * @param pListener
	 * @param system
	 * @return
	 */
	protected I_Tests4J_Controls instanceRun(I_Tests4J_Params pParams, I_TrialRunListener pListener) {
		if (pParams == null) {
			I_Tests4J_Constants messages = Tests4J_Constants.CONSTANTS;
			throw new IllegalArgumentException(messages.getTests4J_NullParamsExceptionMessage());
		}
		
		I_Tests4J_Delegate delegate =  FACTORY.create();
		delegate.setLogger(logger);
		delegate.setSystem(system);
		if (delegate.setup(pListener,pParams)) {
			delegate.run();
		}
		return delegate.getControls();
	}

	protected I_Tests4J_Logger getLogger() {
		return logger;
	}

	protected I_System getSystem() {
		return system;
	}

	protected void setLogger(I_Tests4J_Logger logger) {
		this.logger = logger;
	}

	protected void setSystem(I_System system) {
		this.system = system;
	}
	
	
}
