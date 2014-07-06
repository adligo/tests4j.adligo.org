package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;

/**
 * a interface mostly to allow casting
 * between class loaders.
 * 
 * @author scott
 *
 */
public interface I_ApiTrial extends I_Trial {
	public void afterTrialTests(I_ApiTrialResult p);
}
