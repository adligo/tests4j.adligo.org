package org.adligo.tests4j.models.shared.results.feedback;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;

public interface I_SourceFileTrial_TestsResults extends I_TestsResults {

	public abstract I_SourceFileCoverage getCoverage();

}