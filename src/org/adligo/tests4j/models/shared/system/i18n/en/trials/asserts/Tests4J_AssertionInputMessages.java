package org.adligo.tests4j.models.shared.system.i18n.en.trials.asserts;

import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionInputMessages;

public class Tests4J_AssertionInputMessages implements I_Tests4J_AssertionInputMessages {

	@Override
	public String getExpectedThrownDataRequiresThrowable() {
		return "ExpectedThrownData requires a non null throwable class.";
	}

	@Override
	public String getExpectedThrownDataRequiresMessage() {
		return "ExpectedThrownData requires a non null message.";
	}

}
