package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.results.feedback.I_ApiTrial_TestsResults;

public interface I_ApiTrial extends I_Trial {
	public void afterTrialTests(I_ApiTrial_TestsResults p);
}
