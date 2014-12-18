package org.adligo.tests4j.shared.asserts.common;

import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;


/**
 * implementations of this interface are capable
 * of listing to assertion results.
 * 
 * known implementations;
 *  org.adligo.tests4j.models.shared.asserts.AssertionProcessor
 * @author scott
 *
 */
public interface I_AssertListener {
  
  /**
   * This allows the stack trace to get manipulated
   * based on the instance of the trial,
   * so that the correct name can be linked to.
   * @return
   */
  public I_Asserts getTrialInstance();
  
	/**
	 * this method is called after a successful assertion.
	 * @param cmd
	 */
	public void assertCompleted(I_AssertCommand cmd);
	/**
	 * this method is called after a assertion failure
	 * @param failure
	 */
	public void assertFailed(I_TestFailure failure);
}
