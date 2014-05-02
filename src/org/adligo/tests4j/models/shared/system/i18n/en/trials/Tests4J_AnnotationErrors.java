package org.adligo.tests4j.models.shared.system.i18n.en.trials;

import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TestMethodErrors;

public class Tests4J_AnnotationErrors extends AbstractTests4J_MethodErrors 
	implements I_Tests4J_TestMethodErrors {

	public static final String THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_TIMEOUT = "The test method may not have a negative timeout.";
	public static final String METHODS_ANNOTATED_WITH_TEST_MUST_NOT_TAKE_ANY_PARAMETERS = "Methods Annotated with @Test must not take any parameters";
	public static final String METHODS_ANNOTATED_WITH_TEST_MUST_BE_PUBLIC = "Methods Annotated with @Test must be public.";
	public static final String METHODS_ANNOTATED_WITH_TEST_MUST_NOT_BE_ABSTRACT = "Methods Annotated with @Test must NOT be abstract.";
	public static final String METHODS_ANNOTATED_WITH_TEST_MUST_NOT_BE_STATIC = "Methods Annotated with @Test must NOT be static.";

	@Override
	public String getIsStatic() {
		return METHODS_ANNOTATED_WITH_TEST_MUST_NOT_BE_STATIC;
	}

	@Override
	public String getIsAbstract() {
		return METHODS_ANNOTATED_WITH_TEST_MUST_NOT_BE_ABSTRACT;
	}

	@Override
	public String getIsNotPublic() {
		return METHODS_ANNOTATED_WITH_TEST_MUST_BE_PUBLIC;
	}

	@Override
	public String getHasParams() {
		return METHODS_ANNOTATED_WITH_TEST_MUST_NOT_TAKE_ANY_PARAMETERS;
	}

	@Override
	public String getHasNegativeTimeout() {
		return THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_TIMEOUT;
	}
	
}
