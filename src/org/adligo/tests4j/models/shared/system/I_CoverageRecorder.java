package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public interface I_CoverageRecorder {
	/**
	 * this start recording the coverage
	 * for the larger group of trials
	 */
	public void startRecording();
	
	/**
	 * this start recording for a specific trial
	 */
	public void startTrialRecording();
	/**
	 * this stops recording coverage and returns the
	 * coverage for the specific package
	 * @param packageName
	 * @return
	 */
	public I_PackageCoverage getCoverage(String packageName);
	
	/**
	 * this stops recording coverage and returns the
	 * coverage for the specific package
	 * @param packageName
	 * @return
	 */
	public List<I_PackageCoverage> getCoverage();
}
