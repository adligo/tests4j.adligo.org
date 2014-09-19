package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;

public class Tests4J_ResultMessages implements I_Tests4J_ResultMessages {
	private static final String CALLED_METHOD_OR_FIELD_OUTSIDE_OF_ALLOWED_DEPENDENCIES = "Called method or field outside of @AllowedDependencies.";
	private static final String SOURCE_CLASS_HAS_A_CIRCULAR_DEPENDENCY_DETECTED = "Source class has a circular dependency detected.";
	private static final String THE_CODE_COVERAGE_SHOULD_BE_ABOVE_THE_EXPECTED_VALUE = "The code coverage should be above the expected value.";
	private static final String THE_TEST_TIMED_OUT = "The test timed out.";
	private static final String THE_TRIAL_TIMED_OUT = "The trial timed out.";
	
	private static final String THE_EXPECTED_THROWABLE_DID_NOT_MATCH_THE_ACTUAL_THROWABLE_OR_NOTHING_WAS_THROWN = "The expected throwable did not match the actual throwable, or nothing was thrown.";
	private static final String THE_EXPECTED_THROWABLE_MESSAGE_WAS_NOT_EQUAL_WITH_THE_ACTUAL = "The expected throwable message was NOT equals to the actual thrown message.";
	private static final String THE_EXPECTED_THROWABLE_MESSAGE_WAS_NOT_UNIFORM_WITH_THE_ACTUAL = "The expected throwable message was NOT uniform with the actual thrown message.";
	
	private static final String THE_EXPECTED_THROWN_CLASS_DID_NOT_MATCH_THE_ACTUAL_THROWN_CLASS = "The expected thrown class did NOT match the actual thrown class.";
	private static final String AN_UNEXPECTED_EXCEPTION_WAS_THROWN = "An unexpected exception was thrown.";
	private static final String NO_THROWABLES_WERE_THROWN_FROM_THE_I_THROWER = "No throwables were thrown from the I_Thrower.";
	private static final String THE_TEXT_WAS_UNIFORM = "The text was uniform.";
	private static final String THE_TEXT_WAS_NOT_UNIFORM = "The text was NOT uniform.";
	
	private static final String THE_EXPECTED_THROWABLE_DATA_WAS_NOT_UNIFORM_WITH_THE_ACTUAL = "Either no throwable was thrown or the expected throwable was NOT uniform with the actual throwable.";
	
	private static final String THE_COLLECTION_SHOULD_CONTAIN_THE_VALUE = "The collection should contain the value.";
	private static final String THE_ACTUAL_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_THE_EXPECTED_VALUE = "The actual value should be greater than OR equal to the expected value.";
	private static final String THE_OBJECTS_SHOULD_NOT_BE_UNIFORM = "The objects should NOT be uniform.";
	private static final String THE_OBJECTS_SHOULD_BE_UNIFORM = "The objects should be uniform.";
	private static final String THE_OBJECTS_SHOULD_NOT_BE_THE_SAME = "The objects should NOT be the same.";
	private static final String THE_OBJECTS_SHOULD_NOT_BE_EQUAL = "The objects should NOT be equal.";
	private static final String THE_OBJECTS_SHOULD_BE_THE_SAME = "The objects should be the same.";
	private static final String THE_OBJECTS_SHOULD_BE_EQUAL = "The objects should be equal.";
	private static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The value should NOT be null.";
	private static final String THE_VALUE_SHOULD_BE_NULL = "The value should be null.";
	private static final String THE_VALUE_SHOULD_BE_TRUE = "The value should be true.";
	private static final String THE_VALUE_SHOULD_BE_FALSE = "The value should be false.";
	
	Tests4J_ResultMessages() {}
	
	@Override
	public String getTheExpectedThrowableDataWasNotUniformTheActual() {
		return THE_EXPECTED_THROWABLE_DATA_WAS_NOT_UNIFORM_WITH_THE_ACTUAL;
	}

	public String getTheCollectionShouldContainTheValue() {
		return THE_COLLECTION_SHOULD_CONTAIN_THE_VALUE;
	}
	
	public String getTheActualShouldBeGreaterThanOrEqualToTheExpected() {
		return THE_ACTUAL_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_THE_EXPECTED_VALUE;
	}
	public String getTheObjectsShould_NOT_BeUniform() {
		return THE_OBJECTS_SHOULD_NOT_BE_UNIFORM;
	}
	public String getTheObjectsShouldBeUniform() {
		return THE_OBJECTS_SHOULD_BE_UNIFORM;
	}
	public String getTheObjectsShould_NOT_BeTheSame() {
		return THE_OBJECTS_SHOULD_NOT_BE_THE_SAME;
	}
	public String getTheObjectsShould_NOT_BeEqual() {
		return THE_OBJECTS_SHOULD_NOT_BE_EQUAL;
	}
	public String getTheObjectsShouldBeTheSame() {
		return THE_OBJECTS_SHOULD_BE_THE_SAME;
	}
	public String getTheObjectsShouldBeEqual() {
		return THE_OBJECTS_SHOULD_BE_EQUAL;
	}
	public String getTheValueShould_NOT_BeNull() {
		return THE_VALUE_SHOULD_NOT_BE_NULL;
	}
	public String getTheValueShouldBeNull() {
		return THE_VALUE_SHOULD_BE_NULL;
	}
	public String getTheValueShouldBeTrue() {
		return THE_VALUE_SHOULD_BE_TRUE;
	}
	public String getTheValueShouldBeFalse() {
		return THE_VALUE_SHOULD_BE_FALSE;
	}
	
	public String getTheTextWasNOT_Uniform() {
		return THE_TEXT_WAS_NOT_UNIFORM;
	}
	
	public String getTheTextWasUniform() {
		return THE_TEXT_WAS_UNIFORM;
	}
	
	public String getNothingWasThrown() {
		return NO_THROWABLES_WERE_THROWN_FROM_THE_I_THROWER;
	}
	
	public String getThrowableClassMismatch() {
		return THE_EXPECTED_THROWN_CLASS_DID_NOT_MATCH_THE_ACTUAL_THROWN_CLASS;
	}
	
	public String getThrowableMessageNotEquals() {
		return THE_EXPECTED_THROWABLE_MESSAGE_WAS_NOT_EQUAL_WITH_THE_ACTUAL;
	}
	
	public String getTheExpectedThrowableDidNotMatch() {
		return THE_EXPECTED_THROWABLE_DID_NOT_MATCH_THE_ACTUAL_THROWABLE_OR_NOTHING_WAS_THROWN;
	}
	public String getAnUnexpectedExceptionWasThrown() {
		return AN_UNEXPECTED_EXCEPTION_WAS_THROWN;
	}
	
	public String getTestTimedOut() {
		return THE_TEST_TIMED_OUT;
	}
	
	public String getTrialTimedOut() {
		return THE_TRIAL_TIMED_OUT;
	}
	
	public String getCodeCoveragIsOff() {
		return THE_CODE_COVERAGE_SHOULD_BE_ABOVE_THE_EXPECTED_VALUE;
	}
	
	public String getThrowableMessageNotUniform() {
		return THE_EXPECTED_THROWABLE_MESSAGE_WAS_NOT_UNIFORM_WITH_THE_ACTUAL;
	}
	
	public String getSourceClassHasCircularDependency() {
		return SOURCE_CLASS_HAS_A_CIRCULAR_DEPENDENCY_DETECTED;
	}
	
	public String getCalledMethodOrFieldsOutsideOfAllowedDepenencies() {
		 return CALLED_METHOD_OR_FIELD_OUTSIDE_OF_ALLOWED_DEPENDENCIES;
	}
}
