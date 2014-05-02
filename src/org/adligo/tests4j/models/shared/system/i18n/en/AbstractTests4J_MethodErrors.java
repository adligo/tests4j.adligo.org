package org.adligo.tests4j.models.shared.system.i18n.en;

import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_MethodErrors;

public class AbstractTests4J_MethodErrors implements I_Tests4J_MethodErrors {

	public static final String WAS_NOT_ANNOTATED_CORRECTLY = " was not annotated correctly.";

	@Override
	public String getWasAnnotatedIncorrectly() {
		return WAS_NOT_ANNOTATED_CORRECTLY;
	}

}
