package org.adligo.tests4j.system.shared;

import org.adligo.tests4j.models.shared.trials.I_Progress;

/**
 * this class represents progress of a trial,
 * and helped me obvoid deadlock.
 * 
 * @author scott
 *
 */
public class Tests4J_TrialProgress implements I_Tests4J_TrialProgress {
	private String trial_;
	private double pctDone_;
	/**
	 * this could be progress on 
	 * setting up the trial
	 * running the trial
	 * running the trial remotely
	 * 
	 */
	private I_Progress testProgress_;
	
	public Tests4J_TrialProgress(String trialName, double pctDone, I_Progress testProgress) {
		trial_ = trialName;
		pctDone_ = pctDone;
		testProgress_ = testProgress;
	}
	
	
	public String getTrialName() {
		return trial_;
	}

	public double getPctDone() {
		return pctDone_;
	}


	@Override
	public I_Progress getSubProgress() {
		return testProgress_;
	}
}
