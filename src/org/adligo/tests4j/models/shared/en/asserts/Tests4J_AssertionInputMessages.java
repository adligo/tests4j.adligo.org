package org.adligo.tests4j.models.shared.en.asserts;

import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionInputMessages;

public class Tests4J_AssertionInputMessages implements I_Tests4J_AssertionInputMessages {

	public static final String EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_THROWABLE_CLASS = "ExpectedThrownData requires a non null throwable class.";

	@Override
	public String getExpectedThrownDataRequiresThrowable() {
		return EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_THROWABLE_CLASS;
	}


}
