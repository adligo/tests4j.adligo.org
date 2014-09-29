package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.models.shared.results.I_UseCaseTrialResult;

/**
 * a interface mostly to allow casting
 * between class loaders.
 * 
 * @author scott
 *
 */
public interface I_UseCaseTrial {
	public void afterTrialTests(I_UseCaseTrialResult p);
}
