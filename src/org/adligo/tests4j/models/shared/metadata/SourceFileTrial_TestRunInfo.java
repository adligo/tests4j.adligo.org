package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverage;

public class SourceFileTrial_TestRunInfo extends TestRunInfo 
	implements I_SourceFileTrial_TestRunInfo {
	private I_SourceFileCoverage coverage;

	public SourceFileTrial_TestRunInfo() {}
	
	public SourceFileTrial_TestRunInfo(I_SourceFileTrial_TestRunInfo p) {
		super(p);
		coverage  = new SourceFileCoverage(getCoverage());
	}
	
	public I_SourceFileCoverage getCoverage() {
		return coverage;
	}
	
}
