package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AfterTrialTestsErrors;

public class Tests4J_AfterTrialTestsErrors extends AbstractTests4J_MethodErrors
	implements I_Tests4J_AfterTrialTestsErrors {
	private static final String USE_CASE_TRIAL_HAS_AFTER_TRIAL_TESTS = 
			"Use Case Trial Methods may not be Annotated with @AfterTrialTests.";
	private static final String AFTER_SOURCE_FILE_TRIAL_TESTS_HAS_WRONG_PARAMS = 
			"Source File Trial Methods Annotated with @AfterTrialTests must take a single parameter I_AfterSourceFileTrialCoverage.";
	private static final String AFTER_API_TRIAL_TESTS_HAS_WRONG_PARAMS = 
			"Api Trial Methods Annotated with @AfterTrialTests must take a single parameter I_AfterApiTrialCoverage.";
	private static final String AFTER_TRIAL_TESTS_IS_STATIC = 
			"Methods Annotated with @AfterTrialTests must NOT be static.";
	private static final String AFTER_TRIAL_TESTS_IS_ABSTRACT = 
			"Methods Annotated with @AfterTrialTests must NOT be abstract.";	
	private static final String AFTER_TRIAL_TESTS_IS_NOT_PUBLIC = 
			"Methods Annotated with @AfterTrialTests must be public.";	
	
	public static final String AFTER_TRIAL_TESTS_NOT_STATIC = 
			"Methods Annotated with @AfterTrialTests must be static.";
	
	@Override
	public String getUseCaseTrialHasAfterTrialTests() {
		return USE_CASE_TRIAL_HAS_AFTER_TRIAL_TESTS;
	}

	@Override
	public String getSourceFileTrialHasWrongParams() {
		return AFTER_SOURCE_FILE_TRIAL_TESTS_HAS_WRONG_PARAMS;
	}

	@Override
	public String getApiTrialTestsHasWrongParams() {
		return AFTER_API_TRIAL_TESTS_HAS_WRONG_PARAMS;
	}

	@Override
	public String getIsStatic() {
		return AFTER_TRIAL_TESTS_IS_STATIC;
	}

	@Override
	public String getIsAbstract() {
		return AFTER_TRIAL_TESTS_IS_ABSTRACT;
	}

	@Override
	public String getIsNotPublic() {
		return AFTER_TRIAL_TESTS_IS_NOT_PUBLIC;
	}

}
