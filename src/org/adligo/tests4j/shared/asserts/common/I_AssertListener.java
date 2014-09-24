package org.adligo.tests4j.shared.asserts.common;


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
