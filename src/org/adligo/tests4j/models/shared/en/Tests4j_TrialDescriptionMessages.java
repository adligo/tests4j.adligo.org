package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_TrialDescriptionMessages;

public class Tests4j_TrialDescriptionMessages implements I_Tests4J_TrialDescriptionMessages {
	private static final String MIN_COVERAGE_MUST_BE_BETWEEN_0_0_AND_100_0_PERCENT = "minCoverage must be between 0.0 and 100.0 percent.";

	private static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = 
			"Classes which implement I_AbstractTrial must have a zero argument constructor.";
	
	private static final String IS_MISSING_TRIAL_TYPE_ANNOTATION = 
				" is missing a @TrialType annotation.";

	private static final String THE_TRIAL = "The trail ";
	
	private static final String TRIAL_NO_TEST = 
			"Trial Classes must have at least one method annotated with @Test.";

	private static final String TRIALS_MAY_HAVE_ONLY_ONE_BEFORE_TRIAL = 
			"Trials may only have one method annotated with @BeforeTrial.";
	private static final String TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL_TESTS = 
			"Trials may only have one method annotated with @AfterTrialTests.";
	private static final String TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL = 
			"Trials may only have one method annotated with @AfterTrial.";
	
	private static final String USE_CASE_TRIALS_MUST_BE_ANNOTATED_WITH_USE_CASE_SCOPE = 
			"UseCaseTrials must be annotated with @UseCaseScope.";
	private static final String USE_CASE_SCOPE_EMPTY_SYSTEM = 
			"@UseCaseScope annotations must contain a non empty system.";
	private static final String USE_CASE_SCOPE_EMPTY_NOWN = 
			"@UseCaseScope annotations must contain a non empty nown.";
	private static final String USE_CASE_SCOPE_EMPTY_VERB = 
			"@UseCaseScope annotations must contain a non empty verb.";
	private static final String SOURCE_FILE_TRIALS_MUST_BE_ANNOTATED_WITH_SOURCE_FILE_SCOPE = 
			"SourceFileTrials must be annotated with @SourceFileScope.";
	private static final String SOURCE_FILE_SCOPE_EMPTY_CLASS = 
			"@SourceFileScope annotations must contain a non empty sourceClass.";
	private static final String API_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_SCOPE_ANNOTATION = 
			"ApiTrials must be annotated with a @PackageScope annotation.";
	private static final String PACKAGE_SCOPE_ANNOTATION_EMPTY_PACKAGE = 
			"@PackageScope annotations must contain a non empty packageName.";
	
	

	Tests4j_TrialDescriptionMessages() {}
	
	@Override
	public String getNoUseCaseScope() {
		return USE_CASE_TRIALS_MUST_BE_ANNOTATED_WITH_USE_CASE_SCOPE;
	}
	
	public String getUseCaseScopeEmptySystem() {
		return USE_CASE_SCOPE_EMPTY_SYSTEM;
	}
	
	public String getUseCaseScopeEmptyNown() {
		return USE_CASE_SCOPE_EMPTY_NOWN;
	}
	
	public String getUseCaseScopeEmptyVerb() {
		return USE_CASE_SCOPE_EMPTY_VERB;
	}
	
	@Override
	public String getNoPackageScope() {
		return API_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_SCOPE_ANNOTATION;
	}
	
	public String getPackageScopeEmptyName() {
		return PACKAGE_SCOPE_ANNOTATION_EMPTY_PACKAGE;
	}
	
	@Override
	public String getNoSourceFileScope() {
		return SOURCE_FILE_TRIALS_MUST_BE_ANNOTATED_WITH_SOURCE_FILE_SCOPE;
	}
	
	public String getSourceFileScopeEmptyClass() {
		return SOURCE_FILE_SCOPE_EMPTY_CLASS;
	}
	
	@Override
	public String getBadConstuctor() {
		return CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR;
	}
	
	@Override
	public String getMultipleBeforeTrial() {
		return TRIALS_MAY_HAVE_ONLY_ONE_BEFORE_TRIAL;
	}
	
	@Override
	public String getMultipleAfterTrialTests() {
		return TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL_TESTS;
	}
	
	@Override
	public String getMultipleAfterTrial() {
		return TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL;
	}
	
	@Override
	public String getNoTests() {
		return TRIAL_NO_TEST;
	}
	
	public String getMissingTypeAnnotationPre() {
		return THE_TRIAL;
	}
	
	public String getMissingTypeAnnotationPost() {
		return IS_MISSING_TRIAL_TYPE_ANNOTATION;
	}
	
	public String getMinCoverageMustBeBetweenZeroAndOneHundred() {
		return MIN_COVERAGE_MUST_BE_BETWEEN_0_0_AND_100_0_PERCENT;
	}
}
