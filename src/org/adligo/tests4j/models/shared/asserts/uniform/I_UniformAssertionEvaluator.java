package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;


/**
 * This interface provides a plug-able 
 * way to evaluate for assertions.
 *   Implementations are expected to be threadsafe,
 *   as the default evaluators may be executed by multiple threads
 *   concurrently.
 *   
 * @author scott
 *
 * @param <T>
 */
public interface I_UniformAssertionEvaluator<T> {
	/**
	 * the class that this uniform evaluator 
	 * actually evaluates.
	 * @return
	 */
	public Class<T> getType();
	/**
	 * This method should do the actual evaluation.
	 * @param p note this should be a instance of the class
	 * 	returned with getType()
	 * @return
	 */
	public I_Evaluation isUniform(I_CompareAssertionData<?> p);
	/**
	 * This method should do the actual evaluation.
	 * @param p note this should be a instance of the class
	 * 	returned with getType()
	 * @return
	 */
	public I_Evaluation isNotUniform(I_CompareAssertionData<?> p);
}
