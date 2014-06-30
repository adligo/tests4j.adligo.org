package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.HashMap;
import java.util.Map;

public class EvaluationMutant implements I_Evaluation {
	private boolean success;
	private String failureSubMessage;
	private Map<String,Object> data = new HashMap<String,Object>();
	
	public EvaluationMutant() {
		
	}
	
	public EvaluationMutant(I_Evaluation p) {
		success = p.isSuccess();
		failureSubMessage = p.getFailureSubMessage();
		setData(p.getData());
	}
	
	public boolean isSuccess() {
		return success;
	}
	public String getFailureSubMessage() {
		return failureSubMessage;
	}
	public Map<String, Object> getData() {
		return new HashMap<String,Object>(data);
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setFailureSubMessage(String failureSubMessage) {
		this.failureSubMessage = failureSubMessage;
	}
	public void setData(Map<String, Object> p) {
		data.clear();
		if (p != null) {
			data.putAll(p);
		}
	}
	public void addData(String key, Object p) {
		data.put(key, p);
	}
	
}
