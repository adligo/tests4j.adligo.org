package org.adligo.tests4j.models.shared.trials;

public interface I_TrialParamsQueue {
	/**
	 * get the next parameters for your trial
	 * @param trialInstanceName
	 * @return
	 */
	public I_TrialParams<?> next(String trialInstanceName);
}
