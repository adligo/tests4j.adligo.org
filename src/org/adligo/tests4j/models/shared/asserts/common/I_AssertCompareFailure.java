package org.adligo.tests4j.models.shared.asserts.common;

/**
 * a interface for passing information about an
 * assertion comparison failure.
 * 
 * @author scott
 *
 */
public interface I_AssertCompareFailure extends I_TestFailure {
	/**
	 * the name of the expected class
	 * @return
	 */
	public String getExpectedClass();
	/**
	 * the expected value (usually a .toString() 
	 * from the instance or "null")
	 * @return
	 */
	public String getExpectedValue();
	/**
	 * the name of the actual class
	 * @return
	 */
	public String getActualClass();
	/**
	 * the expected value (usually a .toString() 
	 * from the instance or "null")
	 * @return
	 */
	public String getActualValue();
}
