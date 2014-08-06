package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a mutable implementation of {@link I_EvaluatorLookup}
 * and is not threadsafe.  It is used to provide a pluggable
 * assertion frame work for assertUniform, assertNotUniform and
 * assertThrownUniform assertions, so that users of tests4j
 * may add their own assert evaluations at the Trial or Trials Run levels.
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
	private Map<String, I_UniformAssertionEvaluator<?,?>> lookup;
	private I_UniformThrownAssertionEvaluator thrownEvaulator = new UniformThrownAssertionEvaluator();
	
	public EvaluatorLookupMutant() {
		lookup =
				new HashMap<String,I_UniformAssertionEvaluator<?,?>>();
	}
	
	public EvaluatorLookupMutant(I_EvaluatorLookup other) {
		lookup = new HashMap<String,I_UniformAssertionEvaluator<?,?>>(other.getLookupData());
	}

	@Override
	public I_UniformAssertionEvaluator<?,?> findEvaluator(Class<?> clazz) {
		String className = clazz.getName();
		return lookup.get(className);
	}

	@Override
	public Map<String, I_UniformAssertionEvaluator<?,?>> getLookupData() {
		return new HashMap<String, I_UniformAssertionEvaluator<?,?>>(lookup);
	}
	
	public void setEvaluator(Class<?> clazz, I_UniformAssertionEvaluator<?,?> p) {
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

	public I_UniformThrownAssertionEvaluator getThrownEvaulator() {
		return thrownEvaulator;
	}

	public void setThrownEvaulator(I_UniformThrownAssertionEvaluator thrownEvaulator) {
		this.thrownEvaulator = thrownEvaulator;
	}
}
