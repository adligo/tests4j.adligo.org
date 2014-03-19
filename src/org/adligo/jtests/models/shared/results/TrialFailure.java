package org.adligo.jtests.models.shared.results;

import org.adligo.jtests.models.shared.common.I_Immutable;

public class TrialFailure implements I_TrialFailure, I_Immutable {
	private String message;
	private Throwable exception;
	
	public TrialFailure(String pMessage, Throwable pException) {
		message = pMessage;
		exception = pException;
	}

	public TrialFailure(I_TrialFailure p) {
		message = p.getMessage();
		exception = p.getException();
	}
	
	public String getMessage() {
		return message;
	}


	public Throwable getException() {
		return exception;
	}

	@Override
	public String toString() {
		return "TrialFailure [message=" + message + ", exception=" + exception
				+ "]";
	}
	
}
