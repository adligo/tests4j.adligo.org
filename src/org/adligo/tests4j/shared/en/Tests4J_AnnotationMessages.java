package org.adligo.tests4j.shared.en;

import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;

public class Tests4J_AnnotationMessages implements I_Tests4J_AnnotationMessages {

	private static final String THE_ALLOWED_DEPENDENCIES_GROUP_IS_NOT_A_SINGLETON_ADD_A_INSTANCE_FIELD_FOR_BETTER_MEMORY_USAGE = "The @AllowedDependencies group is not a singleton (add a INSTANCE field for better memory usage)";
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
	private static final String BEFORE_TRIAL_HAS_PARAMS = "Methods annotated with @BeforeTrial must take a single Map<String,Object> parameter.";
	
	private static final String MIN_COVERAGE_MUST_BE_BETWEEN_0_0_AND_100_0_PERCENT = "The minCoverage must be between 0.0 and 100.0 percent.";

	private static final String TRIAL_TYPE_ANNOTATION_MISSING = 
			"The trial is missing a @TrialType annotation.";
	
	
	private static final String TRIALS_MAY_HAVE_ONLY_ONE_BEFORE_TRIAL = 
			"Trials may only have one method annotated with @BeforeTrial.";
	private static final String TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL = 
			"Trials may only have one method annotated with @AfterTrial.";
	private static final String API_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_SCOPE_ANNOTATION = 
			"ApiTrials must be annotated with a @PackageScope annotation.";
	private static final String SOURCE_FILE_TRIALS_MUST_BE_ANNOTATED_WITH_SOURCE_FILE_SCOPE = 
			"SourceFileTrials must be annotated with @SourceFileScope.";
	
	private static final String USE_CASE_TRIAL_TESTS_MUST_BE_ANNOTATED_WITH_USE_CASE_SCOPE = 
			"UseCaseTrial @Test methods must be annotated with @UseCaseScope.";
	private static final String USE_CASE_SCOPE_EMPTY_NAME = 
			"@UseCaseScope annotations must contain a non empty name.";
	private static final String USE_CASE_SCOPE_NAME_UNKNOWN = 
			"@UseCaseScope annotations must match the name of a use case in a path from a " +
			"system or project in the requirements xml file.";

	private static final String SOURCE_FILE_SCOPE_EMPTY_CLASS = 
			"@SourceFileScope annotations must contain a non empty sourceClass.";

	private static final String PACKAGE_SCOPE_ANNOTATION_EMPTY_PACKAGE = 
			"@PackageScope annotations must contain a non empty packageName.";
	
	Tests4J_AnnotationMessages() {
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
	public String getBeforeTrialHasWrongParams() {
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
	
	public String getMinCoverageMustBeBetweenZeroAndOneHundred() {
		return MIN_COVERAGE_MUST_BE_BETWEEN_0_0_AND_100_0_PERCENT;
	}
	

	@Override
	public String getMultipleAfterTrial() {
		return TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL;
	}
	
	@Override
	public String getMultipleBeforeTrial() {
		return TRIALS_MAY_HAVE_ONLY_ONE_BEFORE_TRIAL;
	}
	
	@Override
	public String getNoSourceFileScope() {
		return SOURCE_FILE_TRIALS_MUST_BE_ANNOTATED_WITH_SOURCE_FILE_SCOPE;
	}
	@Override
	public String getNoPackageScope() {
		return API_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_SCOPE_ANNOTATION;
	}
	
	@Override
	public String getNoUseCaseScope() {
		return USE_CASE_TRIAL_TESTS_MUST_BE_ANNOTATED_WITH_USE_CASE_SCOPE;
	}
	
	public String getPackageScopeEmptyName() {
		return PACKAGE_SCOPE_ANNOTATION_EMPTY_PACKAGE;
	}
	
	public String getSourceFileScopeEmptyClass() {
		return SOURCE_FILE_SCOPE_EMPTY_CLASS;
	}	
	
	@Override
	public String getUseCaseScopeEmptyName() {
		return USE_CASE_SCOPE_EMPTY_NAME;
	}

	@Override
	public String getUseCaseScopeNameUnknown() {
		return USE_CASE_SCOPE_NAME_UNKNOWN;
	}
	
	public String getTrialTypeMissing() {
		return TRIAL_TYPE_ANNOTATION_MISSING;
	}

	@Override
	public String getAllowedDependenciesIsNotASingletonWarning() {
		return THE_ALLOWED_DEPENDENCIES_GROUP_IS_NOT_A_SINGLETON_ADD_A_INSTANCE_FIELD_FOR_BETTER_MEMORY_USAGE;
	}
	
}
