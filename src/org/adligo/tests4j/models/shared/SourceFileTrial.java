package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;

@TrialType(type = TrialTypeEnum.SourceFileTrial)
public abstract class SourceFileTrial extends AbstractTrial {
	
	/**
	 * Override this method if you want to make
	 * assertions about code coverage;
	 * ie
	 * assertNotNull(p);
	 * assertGreaterThanOrEquals(100.00,p.getPercentageCoveredDouble());
	 * @param p
	 */
	public void afterTrialTests(I_SourceFileCoverage p) {}
}
