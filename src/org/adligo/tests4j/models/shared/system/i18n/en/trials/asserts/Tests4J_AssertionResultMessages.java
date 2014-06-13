package org.adligo.tests4j.models.shared.system.i18n.en.trials.asserts;

import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionResultMessages;

public class Tests4J_AssertionResultMessages implements I_Tests4J_AssertionResultMessages {

	@Override
	public String getTheExpectedThrowableDataDidNotMatchTheActual() {
		return "Either no Throwable was thrown or the expected Throwable data did not match the actual Throwable data.";
	}

}
