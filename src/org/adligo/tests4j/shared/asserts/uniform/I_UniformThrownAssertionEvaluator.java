package org.adligo.tests4j.shared.asserts.uniform;

import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrowable;


/**
 * This interface provides a plug-able 
 * way to evaluate for assertions.
 *   Implementations are expected to be threadsafe,
 *   as the default evaluators may be executed by multiple threads
 *   concurrently.
 *   
 * @author scott
 *
 */
public interface I_UniformThrownAssertionEvaluator<I_AssertThrownFailure> {
	/**
	 * This method should do the actual evaluation.
	 * @param p note this should be a instance of the class
	 * 	returned with getType()
	 * @return
	 */
	public I_Evaluation<I_AssertThrownFailure> isUniform(I_ExpectedThrowable expected, Throwable actual);
}
