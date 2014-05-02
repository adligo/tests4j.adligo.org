package org.adligo.tests4j.models.shared.system.i18n.en.trials;

import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_AnnotationErrors;

public class AbstractTests4J_MethodErrors implements I_Tests4J_AnnotationErrors {

	public static final String WAS_NOT_ANNOTATED_CORRECTLY = " was not annotated correctly.";

	@Override
	public String getWasAnnotatedIncorrectly() {
		return WAS_NOT_ANNOTATED_CORRECTLY;
	}

}
