package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public interface I_CoverageRecorder {
	public static final String TRIAL_RUN = "TrialRun";
	/**
	 * returns the scope of what this causing this to record,
	 * the TRIAL_RUN scope is for a trials.
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
	 * this stop recording for the context of the current recorder
	 */
	public void stopRecording();


	/**
	 * this stops recording coverage and returns the
	 * coverage.
	 * @param packageName
	 * @return
	 */
	public List<I_PackageCoverage> getCoverage();
}
