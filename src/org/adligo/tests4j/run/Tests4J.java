package org.adligo.tests4j.run;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.common.DelegateSystem;
import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.common.MethodBlocker;
import org.adligo.tests4j.models.shared.common.Tests4J_System;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_DelegateFactory;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.run.helpers.DefaultDelegateFactory;
import org.adligo.tests4j.run.helpers.JseSystem;

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
	private static final MethodBlocker SET_FACTORY_METHOD_BLOCKER = getSetFactoryMethodBlocker();
	private static final DefaultDelegateFactory DEFAULT_FACTORY = new DefaultDelegateFactory();
	/**
	 * A ThreadLocal for the factory of RunTrialsProcessor,
	 * which may be swapped by the Tests4JTrial, in that one case we don't want to 
	 * derail the other trial threads, by accidently sending data that
	 * should go through the regular trial run to the Tests4JTrial trial.
	 */
	private static ThreadLocal<I_Tests4J_DelegateFactory> FACTORY = getFactoryInternal();
	
	static {
		((DelegateSystem) Tests4J_System.SYSTEM).setDelegate(new JseSystem());
	}
	private static MethodBlocker getSetFactoryMethodBlocker() {
		List<String> allowedCallers = new ArrayList<String>();
		allowedCallers.add("org.adligo.tests4j_tests.run.mocks.MockTests4J");
		
		return new MethodBlocker(Tests4J.class,"setFactory", allowedCallers);
	}
	
	private static ThreadLocal<I_Tests4J_DelegateFactory> getFactoryInternal() {
		
		ThreadLocal<I_Tests4J_DelegateFactory> toRet = new ThreadLocal<I_Tests4J_DelegateFactory>();
		toRet.set(DEFAULT_FACTORY);
		return toRet;
	}
	
	/**
	 * Run the trials/tests, using the public endorsed test4j api.
	 * 
	 * @diagram_sync with Overview.seq on 7/21/2014
	 *   see left most activation bar  (listener is optional)
	 * @param pParams
	 * @param pListener
	 */
	public static I_Tests4J_Controls run(I_Tests4J_Params pParams, I_Tests4J_Listener pListener) {
		if (pListener == null) {
			I_Tests4J_Constants messages = Tests4J_Constants.CONSTANTS;
			throw new IllegalArgumentException(messages.getNullListenerExceptionMessage());
		}
		//sets up logging for the run, from the params
		Tests4J instance = new Tests4J();
		instance.setSystem(Tests4J_System.SYSTEM);
		return instance.instanceRun(pParams, pListener);
	}
	
	/**
	 * 
	 * Run the trials/tests, using the public endorsed test4j api.
	 * 
	 * @diagram_sync with Overview.seq on 7/21/2014
	 *   see left most activation bar  (listener is optional)
	 * @param pParams
	 * 
	 */
	public static I_Tests4J_Controls run(I_Tests4J_Params pParams) {
		Tests4J instance = new Tests4J();
		instance.setSystem(Tests4J_System.SYSTEM);
		return instance.instanceRun(pParams, null);
	}
	
	/**
	 * Not part of the public api
	 * for tests only
	 * @param pFactory
	 */
	protected static void setFactory(I_Tests4J_DelegateFactory pFactory) {
		SET_FACTORY_METHOD_BLOCKER.checkAllowed();
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
	 * wrapper around some java.lang.System
	 * methods so that tests4j can make sure
	 * it makes some System calls.
	 */
	private I_System system;
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
			throw new IllegalArgumentException(messages.getNullParamsExceptionMessage());
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
		delegate.setSystem(system);
		//@diagram_sync with Overview.seq on 7/21/2014
		if (delegate.setup(pListener,pParams)) {
			//@diagram_sync with Overview.seq on 7/21/2014
			delegate.run();
		}
		//@diagram_sync with Overview.seq on 7/21/2014
		return delegate.getControls();
	}


	protected I_System getSystem() {
		return system;
	}

	protected void setSystem(I_System system) {
		this.system = system;
	}
	
	
}
