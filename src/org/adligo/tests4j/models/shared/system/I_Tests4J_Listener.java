package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

/**
 * This is the main interface which can be passed into the 
 * Tests4J framework's run method so that results can be aggregated
 * (for something other than the console and xml reporters)
 * or displayed (eclipse plugin).
 * Methods are expected to be Thread Safe.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Listener {
	/**
	 * @param metadata
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void onMetadataCalculated(I_TrialRunMetadata metadata);
	
	/**
	 * 
	 * @param trialName the full java class name for the trial.
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void onStartingTrial(String trialName);
	/**
	 * 
	 * @param trialName the full java class name for the trial.
	 * @param testName the name of the test method.
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void onStartingTest(String trialName, String testName);
	
	/**
	 * 
	 * @param trialName the full java class name for the trial.
	 * @param testName the name of the test method.
	 * @param passed if the test passed (for quick notification).
	 *    Note passed is repeated in the I_TrialResult.
	 *    
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void onTestCompleted(String trialName, String testName, boolean passed);
	
	/**
	 * @param
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void onTrialCompleted(I_TrialResult result);
	/**
	 * @param
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void onRunCompleted(I_TrialRunResult result);
	
	/**
	 * @param
	 * 
	 * @diagram Overview.seq sync on 7/25/2014
	 */
	public void onProgress(String process, double pctComplete);
}
