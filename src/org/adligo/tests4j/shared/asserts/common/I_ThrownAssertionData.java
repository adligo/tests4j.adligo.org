package org.adligo.tests4j.shared.asserts.common;

public interface I_ThrownAssertionData extends I_AssertionData {
	public abstract I_ExpectedThrowable getExpected();

	public abstract Throwable getActual();

	public String getFailureReason();

	public int getFailureThrowable();

}