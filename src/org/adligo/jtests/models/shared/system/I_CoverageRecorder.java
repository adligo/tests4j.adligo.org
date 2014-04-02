package org.adligo.jtests.models.shared.system;

import java.util.List;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

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
	
	/**
	 * this stops recording coverage and returns the
	 * coverage for the specific package
	 * @param packageName
	 * @return
	 */
	public List<I_PackageCoverage> getCoverage();
	
	public Class<? extends I_AbstractTrial> getDelegateClass(String name);
}
