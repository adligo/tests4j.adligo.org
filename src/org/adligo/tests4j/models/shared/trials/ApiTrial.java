package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.results.feedback.I_ApiTrial_TestsResults;

@TrialType(type = TrialTypeEnum.ApiTrial)
public abstract class ApiTrial extends AbstractTrial {
	/**
	 * //TODO this should probably take 
	 * a AipTrial_TestRunInfo parameter which includes;
	 *     I_SourceFileCoverage 
	 *     Trial Metadata
	 *     # of assertions
	 *     # of unique assertions
	 *     hasRecorededCoverage()
	 *     
	 * Override this method if you want to make
	 * assertions about code coverage;
	 * ie
	 * assertNotNull(p);
	 * assertGreaterThanOrEquals(50.00, p.getPercentageCoveredDouble());
	 * @param p
	 */
	public void afterTrialTests(I_ApiTrial_TestsResults p) {}
}
