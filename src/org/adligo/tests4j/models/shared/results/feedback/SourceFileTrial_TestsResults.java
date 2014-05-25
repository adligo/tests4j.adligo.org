package org.adligo.tests4j.models.shared.results.feedback;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverage;

public class SourceFileTrial_TestsResults extends TestsResults 
	implements I_SourceFileTrial_TestsResults {
	private I_SourceFileCoverage coverage;

	public SourceFileTrial_TestsResults() {}
	
	public SourceFileTrial_TestsResults(I_SourceFileTrial_TestsResults p) {
		super(p);
		coverage  = new SourceFileCoverage(getCoverage());
	}
	
	public I_SourceFileCoverage getCoverage() {
		return coverage;
	}
	
}
