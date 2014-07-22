package org.adligo.tests4j.run;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.common.MethodBlocker;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.DefaultLogger;
import org.adligo.tests4j.models.shared.system.DefaultSystem;
import org.adligo.tests4j.models.shared.system.I_Tests4J_System;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_DelegateFactory;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.run.helpers.DefaultDelegateFactory;

/**
 * The main api to run tests for the Tests4J framework.
 * Call the static methods in this class to execute 
 * a list of trials locally or remotely, on one or more threads
 * in the Java Standard Edition.
 *   
 *   Hopefully other cool frameworks like GWT and ADF 
 * will get their own api for running on the phone or in the browser,
 * which could use the shared packages in this project as the basis.
 *   Then intellegence4j another project could be a central aggregation
 * point for all of the tests from the various platforms, and other information
 * about the development effort like 'Who wrote this?'.  
 *    How do the author java doc's compare to the people who actually checked it in?
 * 
 * 
 * 
 * @author scott
 *
 */
public class Tests4J {
	private static final DefaultDelegateFactory DEFAULT_FACTORY = new DefaultDelegateFactory();
	/**
	 * A ThreadLocal for the factory of RunTrialsProcessor,
	 * which may be swapped by the Tests4JTrial, in that one case we don't want to 
	 * derail the other trial threads, by accidently sending data that
	 * should go through the regular trial run to the Tests4JTrial trial.
	 */
	private static ThreadLocal<I_Tests4J_DelegateFactory> FACTORY = getFactoryInternal();
	
	private static ThreadLocal<I_Tests4J_DelegateFactory> getFactoryInternal() {
		ThreadLocal<I_Tests4J_DelegateFactory> toRet = new ThreadLocal<I_Tests4J_DelegateFactory>();
		toRet.set(DEFAULT_FACTORY);
		return toRet;
	}
	
	
	/**
	 * run the trials/tests
	 * @diagram_sync with Overview.seq on 7/21/2014
	 *   see left most activation bar  (listener is optional)
	 * @param pParams
	 * @param pListener
	 */
	public static I_Tests4J_Controls run(I_Tests4J_Params pParams, I_Tests4J_Listener pListener) {
		if (pListener == null) {
			I_Tests4J_Constants messages = Tests4J_Constants.CONSTANTS;
			throw new IllegalArgumentException(messages.getTests4J_NullListenerExceptionMessage());
		}
		I_Tests4J_System system = new DefaultSystem();
		//sets up logging for the run, from the params
		DefaultLogger logger = new DefaultLogger(new DefaultSystem(), pParams);
		Tests4J instance = new Tests4J();
		instance.setSystem(system);
		instance.setLogger(logger);
		return instance.instanceRun(pParams, pListener);
	}
	
	/**
	 * 
	 * run the trials/tests
	 * @diagram_sync with Overview.seq on 7/21/2014
	 *   see left most activation bar  (listener is optional)
	 * @param pParams
	 * 
	 */
	public static I_Tests4J_Controls run(I_Tests4J_Params pParams) {
		I_Tests4J_System system = new DefaultSystem();
		//sets up logging for the run, from the params
		DefaultLogger logger = new DefaultLogger(new DefaultSystem(), pParams);
		Tests4J instance = new Tests4J();
		instance.setSystem(system);
		instance.setLogger(logger);
		return instance.instanceRun(pParams, null);
	}
	
	/**
	 * Not part of the public api
	 * for tests only
	 * @param pFactory
	 */
	protected static void setFactory(I_Tests4J_DelegateFactory pFactory) {
		List<String> allowedCallers = new ArrayList<String>();
		allowedCallers.add("org.adligo.tests4j_tests.run.mocks.MockTests4J");
		
		new MethodBlocker(Tests4J.class,"setFactory", allowedCallers);
		FACTORY.set(pFactory);
	}
	
	/**
	 * Not part of the public api
	 * for tests only
	 */
	protected static I_Tests4J_DelegateFactory getFactory() {
		return FACTORY.get();
	}
	
	/**
	 * not part of the public api,
	 * but used when tests4j tests itself
	 */
	protected Tests4J() {}
	
	/**
	 * redirects output (can silence tests4j when it tests itself,
	 * so that the log doesn't contain a failure from a 
	 * test of a expected test failure.)
	 */
	private I_Tests4J_Logger logger;
	/**
	 * wrapper around some java.lang.System
	 * methods so that tests4j can make sure
	 * it makes some System calls.
	 */
	private I_Tests4J_System system;
	/**
	 * not part of the public api,
	 * but used when test4j tests itself.
	 * 
	 * @diagram_sync with Overview.seq on 7/21/2014
	 *   see upper left note
	 * @param pParmas
	 * @param pListener
	 * @param system
	 * @return
	 */
	protected I_Tests4J_Controls instanceRun(I_Tests4J_Params pParams, I_Tests4J_Listener pListener) {
		Thread.setDefaultUncaughtExceptionHandler(new Tests4J_UncaughtExceptionHandler());
		
		if (pParams == null) {
			I_Tests4J_Constants messages = Tests4J_Constants.CONSTANTS;
			throw new IllegalArgumentException(messages.getTests4J_NullParamsExceptionMessage());
		}
		I_Tests4J_DelegateFactory factory = FACTORY.get();
		//I am not sure how this null is happening, 
		//I do change the FACTORY, but I never set it to null?
		if (factory == null) {
			FACTORY.set(DEFAULT_FACTORY);
			factory = DEFAULT_FACTORY;
		}
		//@diagram_sync with Overview.seq on 7/21/2014
		I_Tests4J_Delegate delegate =  factory.create();
		//@diagram_sync with Overview.seq on 7/21/2014
		delegate.setLogger(logger);
		//@diagram_sync with Overview.seq on 7/21/2014
		delegate.setSystem(system);
		//@diagram_sync with Overview.seq on 7/21/2014
		if (delegate.setup(pListener,pParams)) {
			//@diagram_sync with Overview.seq on 7/21/2014
			delegate.run();
		}
		//@diagram_sync with Overview.seq on 7/21/2014
		return delegate.getControls();
	}

	protected I_Tests4J_Logger getLogger() {
		return logger;
	}

	protected I_Tests4J_System getSystem() {
		return system;
	}

	protected void setLogger(I_Tests4J_Logger logger) {
		this.logger = logger;
	}

	protected void setSystem(I_Tests4J_System system) {
		this.system = system;
	}
	
	
}
