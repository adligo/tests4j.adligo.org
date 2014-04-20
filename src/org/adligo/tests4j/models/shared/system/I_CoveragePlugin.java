package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.AbstractTrial;

/**
 * a pluggable interface for a this integrating testing api 
 * code coverage tools (like EclEmma/Jacoco)
 * 
 * @author scott
 *
 */
public interface I_CoveragePlugin {
	/**
	 * this instruments the classes so that
	 * they notify the recorder
	 */
	public List<Class<? extends AbstractTrial>> instrumentClasses(I_Tests4J_Params params);
	
	/**
	 * if this plugin has support for recorder scope other than
	 * I_CoverageRecorder#TRIAL_RUN
	 * @see createRecorder(String)
	 * @return
	 */
	public boolean hasSupportForRecorderScope();
	/**
	 * for recording all coverage for a run of trials
	 * the scope is;
	 * @see I_CoverageRecorder#TRIAL_RUN
	 * for coverage recorders that can and want to record 
	 * at a more detailed level the scope is;
	 * TrialClassName for capturing the code covered by the trial
	 * TrialClassName#TestMethodName for capturing the code covered by the @Test
	 * 
	 * @param scope
	 * @return
	 */
	public I_CoverageRecorder createRecorder(String scope);
	
	/**
	 * The coverage plugin should re-map the line numbers
	 * for us so that they can show up correctly
	 * in the stack traces provided by the
	 * tests4j api. 
	 *   This is because the instrumented byte code 
	 *   generates the stack, so it adds lines between
	 *   all of the source code lines.
	 * @param p
	 * @return
	 */
	public Integer getSourceFileLine(StackTraceElement p);
}
