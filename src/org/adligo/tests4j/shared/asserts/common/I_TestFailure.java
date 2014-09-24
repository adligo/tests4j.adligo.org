package org.adligo.tests4j.shared.asserts.common;



/**
 * this must be passable via xml
 * and is a generic interface, 
 * @see I_AssertCompairFailure
 * @see I_AssertThrownFailure
 * 
 * @author scott
 *
 */
public interface I_TestFailure {
	/**
	 * this was mostly added to make a switch
	 * in the AssertionProcessor, since GWT doesn't have
	 * a instanceof last time I checked (and it's slow anyway)
	 * @return
	 */
	public I_TestFailureType getType();
	/**
	 * the type of assertion,
	 * note this may be null if the failure is
	 * not because of an assertion.
	 * @return
	 */
	public I_AssertType getAssertType();
	/**
	 * this is the string passed into the assert method
	 * as a message, or created by tests4j if something
	 * is more catastrophically wrong (ie the test method threw an exception).
	 * @return
	 */
	public abstract String getFailureMessage();
	/**
	 * This should include any detail about 
	 * why the assert failed, and a stack trace 
	 * which points back to the assertion.
	 * 
	 * @return
	 */
	public abstract String getFailureDetail();
}