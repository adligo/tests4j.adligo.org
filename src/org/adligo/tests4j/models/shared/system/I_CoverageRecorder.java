package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public interface I_CoverageRecorder {
	/**
	 * this is the prefix of a special scope for
	 * the call to Tests4J
	 * @see I_CoverageRecorder#getScope()
	 */
	public static final String TESTS4J_ = "Tests4J_";
	/**
	 * @see I_CoverageRecorder#getScope()
	 */
	public static final String RECORDER = ".recorder";
	/**
	 * returns the scope of what this causing this to record,
	 * The main run of trials has Tests4J_${System.currentTimeMillis()}.recorder
	 * Specific Trials would have their class name .recorder
	 * Specific Tests would have their trial class name .testMethodName.recorder
	 * 
	 * @return
	 */
	public String getScope();
	/**
	 * this start recording for the context of the current recorder
	 */
	public void startRecording();
	/**
	 * this pauses recording for the context of the current recorder
	 */
	public void pauseRecording();
	/**
	 * this ends recording for the context of the current recorder
	 * for good and cleans up the memory resources
	 */
	public List<I_PackageCoverage> endRecording();
	
	
}
