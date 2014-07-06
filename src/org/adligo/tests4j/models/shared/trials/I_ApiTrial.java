package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;

public interface I_ApiTrial extends I_Trial {
	public void afterTrialTests(I_ApiTrialResult p);
}
