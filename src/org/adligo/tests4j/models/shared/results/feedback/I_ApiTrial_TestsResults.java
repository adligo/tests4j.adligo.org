package org.adligo.tests4j.models.shared.results.feedback;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public interface I_ApiTrial_TestsResults extends I_TestsResults {
	public abstract I_PackageCoverage getCoverage();
}
