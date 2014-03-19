package org.adligo.jtests.models.shared.results;

import org.adligo.jtests.models.shared.asserts.I_AssertionData;

public interface I_TestFailure extends I_TrialFailure {

	public abstract I_AssertionData getData();

	public abstract Throwable getLocationFailed();

}