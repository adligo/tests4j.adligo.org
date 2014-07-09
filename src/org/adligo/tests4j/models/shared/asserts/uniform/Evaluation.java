package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Evaluation<T> implements I_Evaluation<T> {
	private boolean success;
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
