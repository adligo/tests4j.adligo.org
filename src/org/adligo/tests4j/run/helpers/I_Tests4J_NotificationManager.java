package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

public interface I_Tests4J_NotificationManager {

	/**
	 * @digram_sync on 5/26/2014 with Overview.seq
	 */
	public abstract void checkDoneDescribingTrials();

	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 */
	public abstract void sendMetadata();

	public abstract void onProgress(String processName, double pctDone);
	
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

	/**
	 * Check to see if all of the I_Trials are done running
	 * by comparing the trialsWhichCanRun 
	 * from the memory's RunnableTrialDescriptions 
	 * and the count of trials from the trialDone method calls.
	 * 
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 * @return a trial run result
	 * 		
	 */
	public abstract void checkDoneRunningNonMetaTrials();

	public abstract void onDescibeTrialError();

	public abstract boolean isRunning();
	
	public boolean isDoneDescribeingTrials();
	
	public boolean isDoneRunningTrials();

}