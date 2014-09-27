package org.adligo.tests4j.system.shared;

import java.io.IOException;
import java.util.List;

import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

/**
 * a pluggable interface for a this integrating testing api 
 * code coverage tools (like EclEmma/Jacoco)
 * Also the coverage plugin must implement a zero arg
 * constructor.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_CoveragePlugin {

	/**
	 * This method should be executable by multiple threads, 
	 * so that each thread is instrumenting classes, to a shared classloader.
	 */
	public I_Tests4J_CoverageTrialInstrumentation instrument(Class<? extends I_AbstractTrial> trial) throws IOException;
	
	/**
	 * @param trial
	 * @return percent done 0.0 - 100.0
	 */
	public double getInstrumentProgress(Class<? extends I_AbstractTrial> trial);
	
	/**
	 * give the plugin a chance to clean up the instrumentation
	 * work (caches exc)
	 */
	public void instrumentationComplete();
	/**
	 *
	 * @return
	 *    true if this coverage plugin 
	 *    has the ability to record code coverage local to the thread.
	 */
	public boolean isCanThreadGroupLocalRecord();
	
	/**
	 * Create a new recorder.
	 * @return
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public I_Tests4J_CoverageRecorder createRecorder();
	

}
