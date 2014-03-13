package org.adligo.jtests.models.shared.results;

public interface I_TestFailure {

	public abstract String getMessage();

	public abstract Object getExpected();

	public abstract Object getActual();

	public abstract Throwable getLocationFailed();

	public abstract Throwable getException();

}