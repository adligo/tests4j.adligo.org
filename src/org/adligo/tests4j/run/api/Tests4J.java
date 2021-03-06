package org.adligo.tests4j.run.api;

import org.adligo.tests4j.run.common.I_JseSystem;
import org.adligo.tests4j.run.common.JseSystem;
import org.adligo.tests4j.run.discovery.ConstantsDiscovery;
import org.adligo.tests4j.run.helpers.DefaultDelegateFactory;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.MethodBlocker;
import org.adligo.tests4j.shared.en.Tests4J_EnglishConstants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Controls;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Delegate;
import org.adligo.tests4j.system.shared.api.I_Tests4J_DelegateFactory;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Listener;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Params;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 * @diagram_sync on 1/8/2015 with Overview.seq
 * 
 * @author scott
 *
 */
public class Tests4J {
	public static final String TESTS4J_REQUIRES_A_FACTORY = "Tests4J requires a factory.";
	private static final MethodBlocker SET_FACTORY_METHOD_BLOCKER = getSetFactoryMethodBlocker();
	private static final DefaultDelegateFactory DEFAULT_FACTORY = new DefaultDelegateFactory();
	/**
	 * A ThreadLocal for the factory of RunTrialsProcessor,
	 * which may be swapped by the Tests4JTrial, in that one case we don't want to 
	 * derail the other trial threads, by accidently sending data that
	 * should go through the regular trial run to the Tests4JTrial trial.
	 */
	private static ThreadLocal<I_Tests4J_DelegateFactory> FACTORY = getFactoryInternal();

	/**
	 * Run the trials/tests, using the public endorsed test4j api.
	 * 
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 *   see left most activation bar  (listener is optional)
	 * @param pParams
	 * @param pListener
	 */
	public static I_Tests4J_Controls run(I_Tests4J_Params params, I_Tests4J_Listener listener) {
		//sets up logging for the run, from the params
		Tests4J instance = new Tests4J();
		instance.setParams(params);
		instance.setListener(listener);
		return instance.instanceRun();
	}
	
	/**
	 * 
	 * Run the trials/tests, using the public endorsed test4j api.
	 * 
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 *   see left most activation bar  (listener is optional)
	 * @param pParams
	 * 
	 */
	public static I_Tests4J_Controls run(I_Tests4J_Params params) {
		Tests4J instance = new Tests4J();
		instance.setParams(params);
    return instance.instanceRun();
	}
	
	/**
	 * Not part of the public api
	 * for tests only
	 * @param pFactory
	 */
	protected static void setFactory(I_Tests4J_DelegateFactory factory) {
		if (factory == null) {
			throw new NullPointerException(TESTS4J_REQUIRES_A_FACTORY);
		}
		SET_FACTORY_METHOD_BLOCKER.checkAllowed();
		FACTORY.set(factory);
	}

	private static MethodBlocker getSetFactoryMethodBlocker() {
    List<String> allowedCallers = new ArrayList<String>();
    allowedCallers.add("org.adligo.tests4j_tests.run.api.mocks.MockTests4J");
    allowedCallers.add("org.adligo.tests4j_tests.trials_api.common.Tests4JRunnerMock");
    return new MethodBlocker(Tests4J_EnglishConstants.ENGLISH,
        Tests4J.class,"setFactory", allowedCallers);
  }
  
  private static ThreadLocal<I_Tests4J_DelegateFactory> getFactoryInternal() {
    
    ThreadLocal<I_Tests4J_DelegateFactory> toRet = new ThreadLocal<I_Tests4J_DelegateFactory>();
    toRet.set(DEFAULT_FACTORY);
    return toRet;
  }
  
  private static I_Tests4J_Constants getConstants(I_Tests4J_Params params, I_JseSystem system) {
    String language = system.getLanguage();
    try {
      return new ConstantsDiscovery(language);
    } catch (IOException x) {
      return Tests4J_EnglishConstants.ENGLISH;
    }
  }

  /**
  * wrapper around some java.lang.System
  * methods so that tests4j can make sure
  * it makes some System calls.
  */
  private I_JseSystem system_ = new JseSystem();
  private MethodBlocker setSystemBlock_ = new MethodBlocker(Tests4J_EnglishConstants.ENGLISH,
      Tests4J.class, "setSystem", Collections.singleton("org.adligo.tests4j_tests.trials_api.common.Tests4JRunnerMock"));
  private I_Tests4J_Constants constants_;
  private I_Tests4J_Params params_;
  private I_Tests4J_Listener listener_;
  
	/**
	 * not part of the public api,
	 * but used when tests4j tests itself
	 */
	protected Tests4J() {}
	

	protected void blockDuplicateRun() {
	  File dirFile = system_.newFile(".");
	  String dir = dirFile.getAbsolutePath();
	  if (dir.charAt(dir.length() -1) == '.') {
	    dir = dir.substring(0, dir.length() - 1);
	  }
	  String file = dir + ".tests4j_run";
	  File tmpFile = system_.newFile(file);
	  if (tmpFile.exists()) {
	    throwAlreadyRunningException(dir, null);
	  }
	  try {
  	  if (!tmpFile.createNewFile()) {
  	    throwAlreadyRunningException(dir, null);
  	  }
	  } catch (IOException x) {
	    throwAlreadyRunningException(dir, x);
	  }
	  tmpFile.deleteOnExit();
	}

  public void throwAlreadyRunningException(String dir, IOException x) {
    String val = constants_.getAnotherTests4J_InstanceIsRunningHere() +
        system_.lineSeperator() + dir;
    if (x != null) {
      throw new RuntimeException(val, x);
    } else {
      throw new RuntimeException(val);
    }
  }
	/**
	 * not part of the public api,
	 * but used when test4j tests itself.
	 * 
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 *   see upper left note
	 * @param pParmas
	 * @param pListener
	 * @param system
	 * @return
	 */
	protected I_Tests4J_Controls instanceRun() {

		I_Tests4J_DelegateFactory factory = getFactory();
		//@diagram_sync on 1/8/2015 with Overview.seq
		I_Tests4J_Delegate delegate =  factory.create(system_);

		//@diagram_sync on 1/8/2015 with Overview.seq
		if (delegate.setup(listener_,params_)) {
			//@diagram_sync on 1/8/2015 with Overview.seq
			delegate.runOnAnotherThreadIfAble();
		}
		//@diagram_sync on 1/8/2015 with Overview.seq
		return delegate.getControls();
	}

	protected static I_Tests4J_DelegateFactory getFactory() {
		I_Tests4J_DelegateFactory factory = FACTORY.get();
		if (factory == null) {
			FACTORY.set(DEFAULT_FACTORY);
			factory = DEFAULT_FACTORY;
		}
		return factory;
	}

	protected  I_System getSystem() {
		return system_;
	}

  protected void setSystem(I_JseSystem system) {
	  setSystemBlock_.checkAllowed();
	  system_ = system;
	}

  protected void setParams(I_Tests4J_Params params) {
    constants_ = getConstants(params, system_);
    if (params == null) {
      throw new IllegalArgumentException(constants_.getNullParamsExceptionMessage());
    }
    Tests4J_Params copy = new Tests4J_Params(params);
    if (params.getConstants() == null) {
      copy.setConstants(constants_);
    }
    params_ = copy;
  }

  protected void setListener(I_Tests4J_Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException(constants_.getNullListenerExceptionMessage());
    }
    
    listener_ = listener;
  }
	
	
}
