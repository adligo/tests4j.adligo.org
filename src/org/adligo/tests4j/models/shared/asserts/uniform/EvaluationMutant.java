package org.adligo.tests4j.models.shared.asserts.uniform;


public class EvaluationMutant<T> implements I_Evaluation<T> {
	private boolean success = false;
	private String failureReason;
	private T data;
	
	public EvaluationMutant() {
		
	}
	
	public EvaluationMutant(I_Evaluation<T> p) {
		success = p.isSuccess();
		failureReason = p.getFailureReason();
		data = p.getData();
	}
	
	public boolean isSuccess() {
		return success;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public T getData() {
		return data;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setFailureReason(String p) {
		this.failureReason = p;
	}
	public void setData(T p) {
		data = p;
	}
}
