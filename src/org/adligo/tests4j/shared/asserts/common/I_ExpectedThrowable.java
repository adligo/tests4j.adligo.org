package org.adligo.tests4j.shared.asserts.common;


/**
 * This interface represents a expected Throwable. 
 * @see {@link ExpectedThrowable}
 * @author scott
 *
 */
public interface I_ExpectedThrowable {

	/**
	 * 
	 * @return
	 * This method returns the expected message that the throwable
   * should match against {@link #getMatchType()}.
	 */
	public abstract String getMessage();

	/**
	 * 
	 * @return
	 * This method returns the expected class of the throwable 
   * which is expected to be thrown.
	 */
	public abstract Class<? extends Throwable> getThrowableClass();
	
	/**
	 * 
	 * @return
	 * This method returns the actual instance of the throwable or null.
	 */
	public Throwable getInstance();
	
	/**
	 * 
	 * @return
	 * This method returns the expected cause of the 
	 * Throwable to allow infinite recursive testing of Throwable
   * causation chain.
	 */
	public I_ExpectedThrowable getExpectedCause();

	/**
	 * @return
	 * This method returns the type of message matching to use.
	 */
	public I_MatchType getMatchType();
}