package org.adligo.tests4j.models.shared.system;

/**
 * this class represents progress of a trial,
 * and helped me obvoid deadlock.
 * 
 * @author scott
 *
 */
public class Tests4J_TrialProgress implements I_Tests4J_TrialProgress {
	private String trial;
	private double pctDone;
	
	public Tests4J_TrialProgress(String trialNameIn, double pctDoneIn) {
		trial = trialNameIn;
		pctDone = pctDoneIn;
	}
	
	
	public String getTrialName() {
		return trial;
	}

	public double getPctDone() {
		return pctDone;
	}
}
