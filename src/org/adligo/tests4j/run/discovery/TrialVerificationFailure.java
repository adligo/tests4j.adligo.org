package org.adligo.tests4j.run.discovery;

public class TrialVerificationFailure {
	private String failureMessage;
	private IllegalArgumentException exception;
	
	public TrialVerificationFailure(String message, IllegalArgumentException e) {
		failureMessage = message;
		exception = e;
	}
	
	public String getFailureMessage() {
		return failureMessage;
	}
	public IllegalArgumentException getException() {
		return exception;
	}
	
}