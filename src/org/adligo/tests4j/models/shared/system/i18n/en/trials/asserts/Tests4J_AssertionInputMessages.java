package org.adligo.tests4j.models.shared.system.i18n.en.trials.asserts;

import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionInputMessages;

public class Tests4J_AssertionInputMessages implements I_Tests4J_AssertionInputMessages {

	public static final String EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_MESSAGE = "ExpectedThrownData requires a non null message.";
	public static final String EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_THROWABLE_CLASS = "ExpectedThrownData requires a non null throwable class.";

	@Override
	public String getExpectedThrownDataRequiresThrowable() {
		return EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_THROWABLE_CLASS;
	}

	@Override
	public String getExpectedThrownDataRequiresMessage() {
		return EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_MESSAGE;
	}

}
