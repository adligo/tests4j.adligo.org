package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.metadata.I_SourceFileTrial_TestRunInfo;

@TrialType(type = TrialTypeEnum.SourceFileTrial)
public abstract class SourceFileTrial extends AbstractTrial {
	
	/**
	 * 
	 * 
	 * Override this method if you want to make
	 * assertions about code coverage;
	 * ie
	 * if (p.hasRecordedCoverage()) {
	 *		I_SourceFileCoverage coverage = p.getCoverage();
	 *		assertGreaterThanOrEquals(100.00, coverage.getPercentageCoveredDouble());
	 *	}
	 * @param p
	 */
	public void afterTrialTests(I_SourceFileTrial_TestRunInfo p) {}
}
