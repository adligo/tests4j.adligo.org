package org.adligo.jtests.base.shared.results;

public interface I_Failure {

	public abstract String getMessage();

	public abstract Object getExpected();

	public abstract Object getActual();

	public abstract Throwable getLocationFailed();

	public abstract Throwable getException();

}