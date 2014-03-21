package org.adligo.jtests.models.shared.coverage;

public interface I_CoverageRecorder {
	/**
	 * this start recording the coverage
	 */
	public void startRecording();
	/**
	 * this stops recording coverage and returns the
	 * coverage for the specific package
	 * @param packageName
	 * @return
	 */
	public I_PackageCoverage getCoverage(String packageName);
}
