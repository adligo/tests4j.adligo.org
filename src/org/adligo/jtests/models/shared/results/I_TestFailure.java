package org.adligo.jtests.models.shared.results;

import org.adligo.jtests.models.shared.asserts.I_AssertionData;

public interface I_TestFailure {

	public abstract String getMessage();

	public abstract I_AssertionData getData();

	public abstract Throwable getLocationFailed();

	public abstract Throwable getException();

}