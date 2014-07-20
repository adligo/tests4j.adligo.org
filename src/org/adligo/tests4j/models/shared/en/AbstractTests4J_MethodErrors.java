package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;

public class AbstractTests4J_MethodErrors implements I_Tests4J_AnnotationErrors {

	public static final String WAS_NOT_ANNOTATED_CORRECTLY = " was not annotated correctly.";

	@Override
	public String getWasAnnotatedIncorrectly() {
		return WAS_NOT_ANNOTATED_CORRECTLY;
	}

}
