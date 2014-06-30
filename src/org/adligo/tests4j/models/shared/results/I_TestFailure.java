package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;

public interface I_TestFailure extends I_TrialFailure {

	public abstract I_AssertionData getData();

	public abstract Throwable getLocationFailed();

}