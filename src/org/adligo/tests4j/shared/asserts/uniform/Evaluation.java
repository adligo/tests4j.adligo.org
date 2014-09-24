package org.adligo.tests4j.shared.asserts.uniform;

/**
 * a immutable class to represent the result of a 
 * {@link I_UniformAssertionEvaluator#isUniform(org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData)}
 * or a 
 * {@link I_UniformAssertionEvaluator#isNotUniform(org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData)}
 * 
 * @author scott
 *
 * @param <T>
 */
public class Evaluation<T> implements I_Evaluation<T> {
	private boolean success = false;
	private String failureSubMessage;
	private T data;
	
	public Evaluation() {
		
	}
	
	public Evaluation(I_Evaluation<T> p) {
		success = p.isSuccess();
		failureSubMessage = p.getFailureReason();
		data = p.getData();
	}
	
	public boolean isSuccess() {
		return success;
	}
	public String getFailureReason() {
		return failureSubMessage;
	}
	public T getData() {
		return data;
	}
}
