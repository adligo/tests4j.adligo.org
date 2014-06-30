package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a mutable implementation of {@link I_EvaluatorLookup}
 * and is not threadsafe.
 * 
 * @author scott
 *
 */
public class EvaluatorLookupMutant implements I_EvaluatorLookup {
	public static final String THE_CLAZZ_PARAMETER_IN_SET_LOOKUP_MUST_BE_ASSIGNABLE_TO_THE_I_UNIFORM_ASSERTION_EVALUATOR_S_TYPE = "The clazz parameter in setLookup must be assignable to the I_UniformAssertionEvaluator's type.";
	/**
	 * The map of class names
	 * to evaluators
	 */
	private Map<String, I_UniformAssertionEvaluator<?>> lookup =
			new HashMap<String,I_UniformAssertionEvaluator<?>>();
	
	public EvaluatorLookupMutant() {
	}
	
	public EvaluatorLookupMutant(I_EvaluatorLookup other) {
		lookup = Collections.unmodifiableMap(other.getLookupData());
	}

	@Override
	public I_UniformAssertionEvaluator<?> findEvaluator(Class<?> clazz) {
		String className = clazz.getName();
		return lookup.get(className);
	}

	@Override
	public Map<String, I_UniformAssertionEvaluator<?>> getLookupData() {
		return Collections.unmodifiableMap(lookup);
	}
	
	public void setEvaluator(Class<?> clazz, I_UniformAssertionEvaluator<?> p) {
		String className = clazz.getName();
		Class<?> type =  p.getType();
		if (!type.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException(
					THE_CLAZZ_PARAMETER_IN_SET_LOOKUP_MUST_BE_ASSIGNABLE_TO_THE_I_UNIFORM_ASSERTION_EVALUATOR_S_TYPE);
		}
		lookup.put(className, p);
	}
	
	public void removeEvaluator(String className) {
		lookup.remove(className);
	}
}
