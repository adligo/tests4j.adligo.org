package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

/**
 * This is the main interface which can be passed into the 
 * Tests4J framework's run method so that results can be aggregated.
 * It is used internally by Tests4J for the following;
 *  1) passing messages to the console (org.adligo.tests4j.system.shared.report.summary)
 *  2) passing data for xml output files (org.adligo.tests4j.run.xml)
 *  3) passing data for the tests4j remote protocol
 *     (socket streams (org.adligo.tests4j.run.xml))
 * It can also be used implemented for custom tests4j runners (fabricate4_tests4j)
 * Methods are expected to be Thread Safe. 
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Listener {
  /**
   * this gives notification to the listener
   * that things are starting in tests4j
   * and was originally added because;
   *    I was seeing async
   * file deletes with the new Files 
   */
  public void onStartingSetup(I_Tests4J_Params params);
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
	 * @param process the name of the process, @see I_Tests4J_ProcessInfo
	 * 
	 * @diagram_sync BROKEN changed type on 8/19/2014 Overview.seq sync on 7/25/2014
	 */
	public void onProgress(I_PhaseState info);
	
	/**
	 * @param process the name of the process, @see I_Tests4J_ProcessInfo
	 * 
	 */
	public void onProccessStateChange(I_PhaseState info);
}
