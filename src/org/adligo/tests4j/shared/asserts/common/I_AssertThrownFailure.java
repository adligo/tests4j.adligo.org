package org.adligo.tests4j.shared.asserts.common;


public interface I_AssertThrownFailure extends I_TestFailure {
	public I_ThrowableInfo getExpected();
	public I_ThrowableInfo getActual();
	/**
	 * a more detailed reason of why it failed
	 * than the failure message;
	 * ie
	 * failure message=Nothing was thrown, or something did NOT match.
	 * failure reason= Nothing was thrown.
	 * exc.
	 * 
	 * @return
	 */
	public String getFailureReason();
	/**
	 * determines which expected throwable
	 * did not match with the actual throwable
	 * one based.
	 * 
	 * ie the expected throwable is a IllegalArgumentException
	 * and the actual is null
	 * this would return 1
	 * 
	 * @return
	 */
	public int getThrowable();
}
