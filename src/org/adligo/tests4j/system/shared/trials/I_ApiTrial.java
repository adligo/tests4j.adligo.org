package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;

/**
 * a interface mostly to allow casting
 * between class loaders.
 * 
 * @author scott
 *
 */
public interface I_ApiTrial extends I_Trial {
	public static final String AFTER_TRIAL_TESTS = "afterTrialTests(I_ApiTrialResult p)";
	
	public void afterTrialTests(I_ApiTrialResult p);
}
