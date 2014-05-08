package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

@TrialType(type = TrialTypeEnum.ApiTrial)
public abstract class ApiTrial extends AbstractTrial {
	/**
	 * Override this method if you want to make
	 * assertions about code coverage;
	 * ie
	 * assertNotNull(p);
	 * assertGreaterThanOrEquals(50.00, p.getPercentageCoveredDouble());
	 * @param p
	 */
	public void afterTrialTests(I_PackageCoverage p) {}
}
