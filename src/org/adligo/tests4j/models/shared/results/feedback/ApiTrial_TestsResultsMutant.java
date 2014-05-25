package org.adligo.tests4j.models.shared.results.feedback;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;


public class ApiTrial_TestsResultsMutant extends TestsResultsMutant 
	implements I_ApiTrial_TestsResults {
	private I_PackageCoverage coverage;

	public ApiTrial_TestsResultsMutant() {}
	
	public ApiTrial_TestsResultsMutant(I_ApiTrial_TestsResults p) {
		super(p);
		coverage = p.getCoverage();
	}
	
	public I_PackageCoverage getCoverage() {
		return coverage;
	}

	public void setCoverage(I_PackageCoverage coverage) {
		this.coverage = coverage;
	}
	
	
}
