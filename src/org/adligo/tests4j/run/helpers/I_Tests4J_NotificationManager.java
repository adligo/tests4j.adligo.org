package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_ProcessInfo;

public interface I_Tests4J_NotificationManager {

	/**
	 * @diagram_sync on 8/20/2014 with Overview.seq 
	 */
	public void onSetupDone();
	
	/**
	 * @diagram_sync on 8/20/2014 with Overview.seq 
	 */
	public void onDoneRunningNonMetaTrials();
	
	public void onProgress(I_Tests4J_ProcessInfo info);
	
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
	
	public void onProcessStateChange(I_Tests4J_ProcessInfo info);

}