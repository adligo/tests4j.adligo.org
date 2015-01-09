package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.I_TrialResult;

/**
 * @diagram_sync on 1/8/2015 with Overview.seq
 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
 * @author scott
 *
 */
public interface I_Tests4J_NotificationManager {

	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq 
	 */
	public void onSetupDone();
	
	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	public void onDoneRunningNonMetaTrials();
	
	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 * @param info
	 */
	public void onProgress(I_PhaseState info);
	
	public abstract void startingTrial(String name);

	public abstract void startingTest(String trialName, String testName);

	public abstract void onTestCompleted(String trialName, String testName,
			boolean passed);

	/**
	 * @diagram sync on 5/8/201 with  Overview.seq
	 * 
	 * @param result
	 */
	public abstract void onTrialCompleted(I_TrialResult result);

	public abstract void onDescibeTrialError();

	public abstract boolean isRunning();
	
	public boolean isDoneDescribeingTrials();
	
	public boolean isDoneRunningTrials();
	
	public boolean hasDescribeTrialError();
	
	public void onProcessStateChange(I_PhaseState info);

}