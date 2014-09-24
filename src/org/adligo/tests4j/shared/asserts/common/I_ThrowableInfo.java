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
	 * The the stack trace represented as a 
	 * multiple line string
	 * @return
	 */
	public String getStacktrace();
	
	/**
	 * the cause, may be null
	 * @return
	 */
	public I_ThrowableInfo getCause();
}
