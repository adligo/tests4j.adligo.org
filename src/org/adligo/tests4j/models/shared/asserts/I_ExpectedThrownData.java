package org.adligo.tests4j.models.shared.asserts;

/**
 * a interface which represents expected thrown 
 * scenario @see {@link ExpectedThrownData}
 * @author scott
 *
 */
public interface I_ExpectedThrownData {

	/**
	 * the expected message that the throwable
	 * should contain
	 * @return
	 */
	public abstract String getMessage();

	/**
	 * the expected class of the throwable 
	 * which is expected to be thrown.
	 * @return
	 */
	public abstract Class<? extends Throwable> getThrowableClass();

}