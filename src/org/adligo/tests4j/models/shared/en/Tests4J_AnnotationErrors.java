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
	
	private static final String USE_CASE_TRIALS_MUST_BE_ANNOTATED_WITH_USE_CASE_SCOPE = 
			"UseCaseTrials must be annotated with @UseCaseScope.";
	private static final String USE_CASE_SCOPE_EMPTY_SYSTEM = 
			"@UseCaseScope annotations must contain a non empty system.";
	private static final String USE_CASE_SCOPE_EMPTY_NOWN = 
			"@UseCaseScope annotations must contain a non empty nown.";
	private static final String USE_CASE_SCOPE_EMPTY_VERB = 
			"@UseCaseScope annotations must contain a non empty verb.";

	private static final String SOURCE_FILE_SCOPE_EMPTY_CLASS = 
			"@SourceFileScope annotations must contain a non empty sourceClass.";

	private static final String PACKAGE_SCOPE_ANNOTATION_EMPTY_PACKAGE = 
			"@PackageScope annotations must contain a non empty packageName.";
	
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
		return USE_CASE_TRIALS_MUST_BE_ANNOTATED_WITH_USE_CASE_SCOPE;
	}
	
	public String getPackageScopeEmptyName() {
		return PACKAGE_SCOPE_ANNOTATION_EMPTY_PACKAGE;
	}
	
	public String getSourceFileScopeEmptyClass() {
		return SOURCE_FILE_SCOPE_EMPTY_CLASS;
	}	
	
	public String getUseCaseScopeEmptyNown() {
		return USE_CASE_SCOPE_EMPTY_NOWN;
	}

	public String getUseCaseScopeEmptySystem() {
		return USE_CASE_SCOPE_EMPTY_SYSTEM;
	}
	
	public String getUseCaseScopeEmptyVerb() {
		return USE_CASE_SCOPE_EMPTY_VERB;
	}
	
	public String getTrialTypeMissing() {
		return TRIAL_TYPE_ANNOTATION_MISSING;
	}
	
}
