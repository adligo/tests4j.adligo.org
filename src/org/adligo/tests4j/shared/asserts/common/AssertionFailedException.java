package org.adligo.tests4j.shared.asserts.common;

/**
 * may be thrown when a assertion fails
 * to obvoid running other assertions.
 * 
 * @author scott
 *
 */
public class AssertionFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssertionFailedException() {}
	
	public AssertionFailedException(String p) {
		super(p);
	}
}
