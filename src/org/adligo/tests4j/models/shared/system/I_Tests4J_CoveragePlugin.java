package org.adligo.tests4j.models.shared.system;

import java.util.List;

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
	 * this instruments the classes so that
	 * they notify the recorder
	 * 
	 * Note this is a I_AbstractTrial because it allows instrumentation
	 * of the MetaTrial which isn't a I_Trial.
	 * 
	 * @diagram Overview.seq sync on 5/26/2014
	 */
	public List<Class<? extends I_AbstractTrial>> instrumentClasses(List<Class<? extends I_AbstractTrial>> trials);
	
	/**
	 *
	 * @return
	 *    true if this coverage plugin 
	 *    has the ability to record code coverage local to the thread.
	 */
	public boolean canThreadGroupLocalRecord();
	
	/**
	 * Create a new recorder that records either 
	 * all code coverage from all threads, or
	 * code coverage for this thread only.
	 * 
	 * @param scope
	 * @return
	 * 
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public I_Tests4J_CoverageRecorder createRecorder();
	
}
