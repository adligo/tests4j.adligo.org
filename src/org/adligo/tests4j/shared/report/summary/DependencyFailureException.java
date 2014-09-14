package org.adligo.tests4j.shared.report.summary;

public class DependencyFailureException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DependencyFailureException(String message, StackTraceElement[] stack) {
		super(message);
		super.setStackTrace(stack);
	}
}
