package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Evaluation implements I_Evaluation {
	private boolean success;
	private String failureSubMessage;
	private Map<String,Object> data = new HashMap<String,Object>();
	
	public Evaluation() {
		
	}
	
	public Evaluation(I_Evaluation p) {
		success = p.isSuccess();
		failureSubMessage = p.getFailureSubMessage();
		Map<String,Object> pData = p.getData();
		if (pData != null) {
			data.putAll(pData);
		}
	}
	
	public boolean isSuccess() {
		return success;
	}
	public String getFailureSubMessage() {
		return failureSubMessage;
	}
	public Map<String, Object> getData() {
		return Collections.unmodifiableMap(data);
	}
}
