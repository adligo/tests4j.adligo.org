package org.adligo.jtests.models.shared.results;

import org.adligo.jtests.base.shared.asserts.I_AssertData;

public interface I_TestFailure {

	public abstract String getMessage();

	public abstract I_AssertData getData();

	public abstract Throwable getLocationFailed();

	public abstract Throwable getException();

}