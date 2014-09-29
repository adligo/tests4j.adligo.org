package org.adligo.tests4j.system.shared.trials;

public interface I_MetaTrialParamsAware<T extends I_MetaTrialInputData> {
	/**
	 * 
	 * @param p
	 */
	public void setTrialParams(I_MetaTrialParams<T> p);
}
