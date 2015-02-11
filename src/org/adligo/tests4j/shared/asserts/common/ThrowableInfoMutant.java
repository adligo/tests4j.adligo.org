package org.adligo.tests4j.shared.asserts.common;

import org.adligo.tests4j.shared.common.StackTraceBuilder;

public class ThrowableInfoMutant implements I_ThrowableInfo {
	private String className_;
	private String message_;
	private I_MatchType matchType_;
	private String stacktrace_;
	private I_ThrowableInfo cause_;
	
	public ThrowableInfoMutant() {}
	
	public ThrowableInfoMutant(I_ThrowableInfo info) {
		className_ = info.getClassName();
		message_ = info.getMessage();
		stacktrace_ = info.getMessage();
		cause_ = info.getCause();
		matchType_ = info.getMatchType();
	}
	
	public ThrowableInfoMutant(I_ExpectedThrowable p) {
		className_ = p.getThrowableClass().getName();
		Throwable instance = p.getInstance();
		if (instance != null) {
			message_ = instance.getMessage();
		}
		// for now ignore stacktrace, as it isn't really useful
		// we may need it at some point if we start allowing full 
		// stack trace comparisons
		I_ExpectedThrowable pCause = p.getExpectedCause();
		if (pCause != null) {
			cause_ = new ThrowableInfoMutant(pCause);
		}
		matchType_ = p.getMatchType();
	}
	
	
	public ThrowableInfoMutant(Throwable p) {
		className_ = p.getClass().getName();
		message_ = p.getMessage();
		stacktrace_ = new StackTraceBuilder().toString(p, false);
		
		Throwable pCause = p.getCause();
		if (pCause != null) {
			cause_ = new ThrowableInfoMutant(pCause);
		}
	}
	
	public String getClassName() {
		return className_;
	}
	public String getMessage() {
		return message_;
	}
	public String getStacktrace() {
		return stacktrace_;
	}
	public I_ThrowableInfo getCause() {
		return cause_;
	}
	public void setClassName(String className) {
		this.className_ = className;
	}
	public void setMessage(String message) {
		this.message_ = message;
	}
	public void setStacktrace(String stacktrace) {
		this.stacktrace_ = stacktrace;
	}
	public void setCause(I_ThrowableInfo cause) {
		this.cause_ = cause;
	}

  @Override
  public I_MatchType getMatchType() {
    return matchType_;
  }

  public void setMatchType(I_MatchType matchType) {
    this.matchType_ = matchType;
  }
}
