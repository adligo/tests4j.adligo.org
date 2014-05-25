package org.adligo.tests4j.models.shared.results.feedback;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverage;

public class ApiTrial_TestsResults extends TestsResults 
	implements I_ApiTrial_TestsResults {
	private PackageCoverage coverage;
	
	public ApiTrial_TestsResults() {}
	
	public ApiTrial_TestsResults(I_ApiTrial_TestsResults p) {
		super(p);
		I_PackageCoverage pCoverage = p.getCoverage();
		if (pCoverage != null) {
			coverage = new PackageCoverage(pCoverage);
		}
	}

	@Override
	public I_PackageCoverage getCoverage() {
		return coverage;
	}
	
	
}
