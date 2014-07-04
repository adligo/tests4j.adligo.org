package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.feedback.I_SourceFileTrial_TestsResults;

@TrialTypeAnnotation(type = TrialType.SourceFileTrial)
public abstract class SourceFileTrial extends AbstractTrial implements I_SourceFileTrial  {
	
	/**
	 * Override this method if you want to make
	 * assertions about code coverage or other about
	 * the run of the @Test methods in the current trial;
	 * ie
	 * if (p.hasRecordedCoverage()) {
	 *		I_SourceFileCoverage coverage = p.getCoverage();
	 *		assertGreaterThanOrEquals(100.00, coverage.getPercentageCoveredDouble());
	 *	}
	 *
	 * assertEquals(111, p.getUniqueAssertions());
	 * @param p
	 */
	public void afterTrialTests(I_SourceFileTrial_TestsResults p) {}
}