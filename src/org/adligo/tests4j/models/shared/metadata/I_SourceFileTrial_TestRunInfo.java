package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;

public interface I_SourceFileTrial_TestRunInfo extends I_AbstractTestRunInfo {

	public abstract I_SourceFileCoverage getCoverage();

}