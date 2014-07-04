package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.Map;

/**
 * This interface provides a way to lookup 
 * implementations of I_UniformAssertionEvaluator 
 * for the plug-able assertion framework (isUniform, isNotUniform).
 * 
 * @author scott
 *
 */
public interface I_EvaluatorLookup {
	/**
	 * @param clazz
	 * @return a evaluator that has been mapped to a class
	 * or null if none is available.
	 */
	public I_UniformAssertionEvaluator<?> findEvaluator(Class<?> clazz);  
	/**
	 * @return the lookup data, note the key
	 * is the name of the class used in findEvaluator.
	 * The name of the class is used instead of the 
	 * class to prevent class loading conflicts.
	 */
	public Map<String, I_UniformAssertionEvaluator<?>> getLookupData();
}