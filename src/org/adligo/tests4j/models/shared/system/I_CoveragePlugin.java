package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.I_AbstractTrial;

/**
 * a pluggable interface for a this integrating testing api 
 * code coverage tools (like EclEmma/Jacoco)
 * Also the coverage plugin must implement a zero arg
 * constructor.
 * 
 * @author scott
 *
 */
public interface I_CoveragePlugin {
	/**
	 * this instruments the classes so that
	 * they notify the recorder
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public List<Class<? extends I_AbstractTrial>> instrumentClasses(I_Tests4J_Params params);
	
	/**
	 *
	 * @return
	 *    false if this coverage plugin 
	 *    can create sub recordings at the Trial or Test level
	 *    @see I_CoverageRecorder#getScope()
	 */
	public boolean canSubRecord();
	
	/**
	 * for recording all coverage for a run of trials
	 * the scope is;
	 * @see I_CoverageRecorder#getScope()
	 * for coverage recorders that can and want to record 
	 * at a more detailed level the scope is;
	 * TrialClassName for capturing the code covered by the trial
	 * TrialClassName#TestMethodName for capturing the code covered by the @Test
	 * 
	 * @param scope
	 * @return
	 * 
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public I_CoverageRecorder createRecorder(String scope);
	
}
