package org.adligo.tests4j.models.shared.system.i18n.en.trials;

import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_AfterTrialTestsErrors;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TestMethodErrors;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4j_AfterTrialErrors;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4j_BeforeTrialErrors;

public class Tests4j_TrialDescriptionMessages 
	extends Tests4J_AnnotationErrors implements I_Tests4J_TrialDescriptionMessages {
	public static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = 
			"Classes which implement I_AbstractTrial must have a zero argument constructor.";
	
	public static final String REFERS_TO_A_NULL_TRIAL_TYPE_ENUM = 
			" refers to a null TrialTypeEnum type.";
	public static final String IS_MISSING_TRIAL_TYPE_ANNOTATION = 
				" is missing a @TrialType annotation.";

	public static final String IS_MISSING_A_TRIAL_TYPE = " is missing a TrialTypeEnum.";
	public static final String THE_TRIAL = "The trail ";
	
	public static final String TRIAL_NO_TEST = 
			"Trial Classes must have at least one method annotated with @Test.";

	public static final String TRIALS_MAY_HAVE_ONLY_ONE_BEFORE_TRIAL = 
			"Trials may only have one method annotated with @BeforeTrial.";
	public static final String TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL_TESTS = 
			"Trials may only have one method annotated with @AfterTrialTests.";
	public static final String TRIALS_MAY_HAVE_ONLY_ONE_AFTER_TRIAL = 
			"Trials may only have one method annotated with @AfterTrial.";
	
	public static final String USE_CASE_TRIALS_MUST_BE_ANNOTATED_WITH_USE_CASE_SCOPE = 
			"UseCaseTrials must be annotated with @UseCaseScope.";
	public static final String USE_CASE_SCOPE_EMPTY_SYSTEM = 
			"@UseCaseScope annotations must contain a non empty system.";
	public static final String USE_CASE_SCOPE_EMPTY_NOWN = 
			"@UseCaseScope annotations must contain a non empty nown.";
	public static final String USE_CASE_SCOPE_EMPTY_VERB = 
			"@UseCaseScope annotations must contain a non empty verb.";
	public static final String SOURCE_FILE_TRIALS_MUST_BE_ANNOTATED_WITH_SOURCE_FILE_SCOPE = 
			"SourceFileTrials must be annotated with @SourceFileScope.";
	public static final String SOURCE_FILE_SCOPE_EMPTY_CLASS = 
			"@SourceFileScope annotations must contain a non empty sourceClass.";
	public static final String API_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_SCOPE_ANNOTATION = 
			"ApiTrials must be annotated with a @PackageScope annotation.";
	public static final String PACKAGE_SCOPE_ANNOTATION_EMPTY_PACKAGE = 
			"@PackageScope annotations must contain a non empty packageName.";
	public static final String WAS_NOT_ANNOTATED_CORRECTLY = 
			" was not annotated correctly.";
	
	

	private Tests4J_BeforeTrialErrors beforeTrialErrors = new Tests4J_BeforeTrialErrors();
	private Tests4J_AnnotationErrors testMethodErrors = new Tests4J_AnnotationErrors();
	private Tests4J_AfterTrialTestsErrors afterTrialTestsErrors = new Tests4J_AfterTrialTestsErrors();
	private Tests4J_AfterTrialErrors afterTrialErrors = new Tests4J_AfterTrialErrors();


	@Override
	public I_Tests4j_BeforeTrialErrors getBeforeTrialErrors() {
		return beforeTrialErrors;
	}

	@Override
	public I_Tests4J_TestMethodErrors getTestMethodErrors() {
		return testMethodErrors;
	}

	@Override
	public I_Tests4J_AfterTrialTestsErrors getAfterTrialTestsErrors() {
		return afterTrialTestsErrors;
	}

	@Override
	public I_Tests4j_AfterTrialErrors getAfterTrialErrors() {
		return afterTrialErrors;
	}
	
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
}
