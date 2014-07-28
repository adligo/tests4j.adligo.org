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
	 * This method should be executable by multiple threads, 
	 * so that each thread is instrumenting classes, to a shared classloader.
	 */
	public Class<? extends I_AbstractTrial> instrument(Class<? extends I_AbstractTrial> trial);
	
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