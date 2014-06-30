package org.adligo.tests4j.models.shared.asserts.common;

public interface I_ThrownAssertionData extends I_AssertionData {
	public static final String EXPECTED_THROWABLE_CLASS = "expected_throwable_class";
	public static final String EXPECTED_MESSAGE = "expected_message";
	public static final String ACTUAL_THROWABLE_CLASS = "actual_throwable_class";
	public static final String ACTUAL_MESSAGE = "actual_message";
	
	public abstract Class<? extends Throwable> getExpectedThrowable();

	public abstract String getExpectedMessage();

	public abstract Class<? extends Throwable> getActualThrowable();

	public abstract String getActualMessage();

}