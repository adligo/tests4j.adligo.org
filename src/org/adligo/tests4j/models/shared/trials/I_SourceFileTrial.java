package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.results.feedback.I_SourceFileTrial_TestsResults;

/**
 * This class was mostly added to delegate through class loaders.
 * @author scott
 *
 */
public interface I_SourceFileTrial extends I_Trial {
	public void afterTrialTests(I_SourceFileTrial_TestsResults p);
}