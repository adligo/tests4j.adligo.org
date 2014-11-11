package org.adligo.tests4j.system.shared.report.summary;

public class AllowedReferencesFailureException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AllowedReferencesFailureException(String message, StackTraceElement[] stack) {
		super(message);
		super.setStackTrace(stack);
	}
}
