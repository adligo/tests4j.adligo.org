package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

/**
 * a meta trial is a trial that asserts things 
 * about the metadata of a test run.  This is useful
 * when you want to find source file classes
 * which do not have corresponding a source file trial.
 * This also allows to assert a number of trials 
 * which ran, and total code coverage.
 * 
 * @author scott
 *
 */
public interface I_MetaTrial extends I_AbstractTrial {
	
	/**
	 * asserts the percent passed in 
	 * is less than or equal to 
	 * the actual percentage of
	 * source file classes with trials
	 * to source file classes.
	 * 
	 * @param pct
	 */
	public void assertPercentOfSourceFileClassesWithSouceFileTrials(double pct);

	/**
	 * assert thing about the metadata ie
	 * the above assertPercentOfSourceFileClassesWithSouceFileTrials(double pct)
	 * override to change the default logic.
	 * @param metadata
	 */
	@Test
	public void testMetadata(I_TrialRunMetadata metadata);

	public void testResults(I_TrialRunResult results);
}
