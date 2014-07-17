package org.adligo.tests4j.run.helpers;

/**
 * This is a way to notify that one of the assertions
 * in the afterTrialTests or other method has failed, as it
 * is on the trial thread.
 * @author scott
 *
 */
public class DelegateTestAssertionFailure extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
