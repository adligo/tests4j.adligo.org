package org.adligo.tests4j.system.shared.trials;

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
 * 
 * @author scott
 *
 */
public interface I_MetaTrial extends I_AbstractTrial {
	public static final String AFTER_META_CALC = "afterMetadataCalculated(I_TrialRunMetadata metadata)";
	public static final String AFTER_NON_META_RESULTS = "afterNonMetaTrialsRun(I_TrialRunResult results)";
	
	/**
	 * this method can assert thing about the metadata ie
	 * the above assertPercentOfSourceFileClassesWithSouceFileTrials(double pct)
	 * override to change the default logic.
	 * @param metadata
	 */
	public void afterMetadataCalculated(I_TrialRunMetadata metadata) throws Exception;

	/**
	 * this method can assert things about
	 * the trial run results, ie total percentage covered.
	 * that nothing was ignored exc.
	 * 
	 * @param results
	 */
	public void afterNonMetaTrialsRun(I_TrialRunResult results) throws Exception;
}
