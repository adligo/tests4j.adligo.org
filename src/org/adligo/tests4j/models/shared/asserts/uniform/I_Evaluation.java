package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;


/**
 * This interface contains information
 * about the evaluation of a assertUniform
 * or assertNotUniform method.
 * 
 * @author scott
 *
 */
public interface I_Evaluation<T> {
	public boolean isSuccess();
	/**
	 * A more detailed failure message than {@link I_AssertCommand#getFailureMessage()} 
	 * 
	 * @return
	 */
	public String getFailureReason();
	/**
	 * @return special data <T> pertaining to this
	 * evaluation result  I_LineTextCompareResult
	 * or String exc.  The data field is expected
	 * to be immutable.
	 * 
	 * May be NULL!
	 */
	public T getData();
}
