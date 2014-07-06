package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;

@TrialTypeAnnotation(type = TrialType.ApiTrial)
public abstract class ApiTrial extends AbstractTrial implements I_ApiTrial  {
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
	public void afterTrialTests(I_ApiTrialResult p) {}
}
