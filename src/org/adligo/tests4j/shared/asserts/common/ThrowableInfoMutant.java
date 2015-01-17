package org.adligo.tests4j.shared.asserts.common;

import org.adligo.tests4j.shared.common.StackTraceBuilder;

public class ThrowableInfoMutant implements I_ThrowableInfo {
	private String className;
	private String message;
	private String stacktrace;
	private I_ThrowableInfo cause;
	
	public ThrowableInfoMutant() {}
	
	public ThrowableInfoMutant(I_ThrowableInfo info) {
		className = info.getClassName();
		message = info.getMessage();
		stacktrace = info.getMessage();
		cause = info.getCause();
	}
	
	public ThrowableInfoMutant(I_ExpectedThrowable p) {
		className = p.getThrowableClass().getName();
		Throwable instance = p.getInstance();
		if (instance != null) {
			message = instance.getMessage();
		}
		// for now ignore stacktrace, as it isn't really useful
		// we may need it at some point if we start allowing full 
		// stack trace comparisons
		I_ExpectedThrowable pCause = p.getExpectedCause();
		if (pCause != null) {
			cause = new ThrowableInfoMutant(pCause);
		}
	}
	
	
	public ThrowableInfoMutant(Throwable p) {
		className = p.getClass().getName();
		message = p.getMessage();
		stacktrace = new StackTraceBuilder().toString(p, false);
		
		Throwable pCause = p.getCause();
		if (pCause != null) {
			cause = new ThrowableInfoMutant(pCause);
		}
	}
	
	public String getClassName() {
		return className;
	}
	public String getMessage() {
		return message;
	}
	public String getStacktrace() {
		return stacktrace;
	}
	public I_ThrowableInfo getCause() {
		return cause;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}
	public void setCause(I_ThrowableInfo cause) {
		this.cause = cause;
	}
}
