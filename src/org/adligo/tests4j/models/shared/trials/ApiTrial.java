package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;

@TrialTypeAnnotation(type = TrialType.API_TRIAL_TYPE)
public class ApiTrial extends AbstractTrial implements I_ApiTrial  {
	/**
	 * 
	 * Override this method if you want to make
	 * assertions about code coverage;
	 * ie
	 * assertNotNull(p);
	 * assertGreaterThanOrEquals(11.0, p.getAssertionCount());
	 * if (p.
	 * assertGreaterThanOrEquals(50.00, p.getPercentageCoveredDouble());
	 * @param p
	 */
	public void afterTrialTests(I_ApiTrialResult p) {
		if (p.hasRecordedCoverage()) {
			I_PackageCoverage cover = p.getPackageCoverage();
			assertGreaterThanOrEquals(90.0, cover.getPercentageCoveredDouble());
		}
		
	}
}
