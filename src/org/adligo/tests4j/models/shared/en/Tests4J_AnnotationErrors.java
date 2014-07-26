package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;

public class Tests4J_AnnotationErrors implements I_Tests4J_AnnotationErrors {

	private static final String THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_TIMEOUT = "Methods annotated with @Test may not have a negative timeout.";
	private static final String METHODS_ANNOTATED_WITH_TEST_MUST_NOT_TAKE_ANY_PARAMETERS = "Methods annotated with @Test must not take any parameters.";
	private static final String METHODS_ANNOTATED_WITH_TEST_MUST_BE_PUBLIC = "Methods annotated with @Test must be public.";
	private static final String METHODS_ANNOTATED_WITH_TEST_MUST_NOT_BE_ABSTRACT = "Methods annotated with @Test must NOT be abstract.";
	private static final String METHODS_ANNOTATED_WITH_TEST_MUST_NOT_BE_STATIC = "Methods annotated with @Test must NOT be static.";
	private static final String WAS_NOT_ANNOTATED_CORRECTLY = " was not annotated correctly.";
	private static final String AFTER_TRIAL_NOT_PUBLIC = "Methods annotated with @AfterTrial must be public.";
	private static final String AFTER_TRIAL_NOT_STATIC = "Methods annotated with @AfterTrial must be static.";
	private static final String AFTER_TRIAL_HAS_PARAMS = "Methods annotated with @AfterTrial must NOT take any parameters.";
	
	private static final String BEFORE_TRIAL_NOT_PUBLIC = "Methods annotated with @BeforeTrial must be public.";
	private static final String BEFORE_TRIAL_NOT_STATIC = "Methods annotated with @BeforeTrial must be static.";
	private static final String BEFORE_TRIAL_HAS_PARAMS = "Methods annotated with @BeforeTrial must NOT take any parameters.";
	
	Tests4J_AnnotationErrors() {
	}
	
	@Override
	public String getAfterTrialHasParams() {
		return AFTER_TRIAL_HAS_PARAMS;
	}

	@Override
	public String getAfterTrialNotStatic() {
		return AFTER_TRIAL_NOT_STATIC;
	}

	@Override
	public String getAfterTrialNotPublic() {
		return AFTER_TRIAL_NOT_PUBLIC;
	}
	
	@Override
	public String getBeforeTrialHasParams() {
		return BEFORE_TRIAL_HAS_PARAMS;
	}

	@Override
	public String getBeforeTrialNotStatic() {
		return BEFORE_TRIAL_NOT_STATIC;
	}

	@Override
	public String getBeforeTrialNotPublic() {
		return BEFORE_TRIAL_NOT_PUBLIC;
	}

	@Override
	public String getWasAnnotatedIncorrectly() {
		return WAS_NOT_ANNOTATED_CORRECTLY;
	}
	
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
