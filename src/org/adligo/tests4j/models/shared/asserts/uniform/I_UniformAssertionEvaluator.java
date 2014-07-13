package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;


/**
 * This interface provides a plug-able 
 * way to evaluate for assertions.
 *   Implementations are expected to be threadsafe,
 *   as the default evaluators may be executed by multiple threads
 *   concurrently.
 *   
 * @author scott
 *
 * @param <T> the class that the uniform evaluator actually evaluates
 * @param <D> the type of data in the I_Evaluation
 */
public interface I_UniformAssertionEvaluator<T,D> {
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
	public I_Evaluation<D> isUniform(I_CompareAssertionData<T> p);
	/**
	 * This method should do the actual evaluation.
	 * @param p note this should be a instance of the class
	 * 	returned with getType()
	 * @return
	 */
	public I_Evaluation<D> isNotUniform(I_CompareAssertionData<T> p);
}
