package org.adligo.tests4j.shared.asserts.common;


public interface I_ThrowableInfo {
	/**
	 * the name of the throwable class 
	 * which this instance is providing information
	 * about ie "java.lang.IllegalStateException"
	 * @return
	 */
	public String getClassName();
	/**
	 * The message from this throwable,
	 * may be null
	 * @return
	 */
	public String getMessage();
	
	/**
	 * The the stack trace represented as a multiple line string, note the stack 
	 * trace should already have some indentation and should have paid attention 
	 * to the I_Tests4J_Constants.isLeftToRight() setting so it may have indentation 
	 * at the left or the right accordingly.
	 * @return
	 */
	public String getStacktrace();
	
	/**
	 * the cause, may be null
	 * @return
	 */
	public I_ThrowableInfo getCause();
}
