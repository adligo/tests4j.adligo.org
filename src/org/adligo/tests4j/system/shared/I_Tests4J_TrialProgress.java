package org.adligo.tests4j.system.shared;

import org.adligo.tests4j.system.shared.trials.I_Progress;

public interface I_Tests4J_TrialProgress {

	public abstract String getTrialName();

	public abstract double getPctDone();

	/**
	 * may be null,
	 * if the pctDone is 0 it doesn't tell us much
	 * this allows us to append the running test if there is one
	 * @return
	 */
	public I_Progress getSubProgress();
}