package org.adligo.jtests.models.shared.results;


public interface I_TrialFailure {

	public abstract String getMessage();

	public abstract Throwable getException();

}