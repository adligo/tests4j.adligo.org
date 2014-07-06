package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;

/**
 * a interface mostly to allow casting
 * between class loaders.
 * 
 * @author scott
 *
 */
public interface I_SourceFileTrial extends I_Trial {
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
	 * assertEquals(111, p.getUniqueAssertionCount());
	 * @param p 
	 *    the results of the trial with out this method.
	 */
	public void afterTrialTests(I_SourceFileTrialResult p);
}
