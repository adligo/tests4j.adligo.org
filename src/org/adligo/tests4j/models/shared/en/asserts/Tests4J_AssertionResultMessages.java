package org.adligo.tests4j.models.shared.en.asserts;

import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;

public class Tests4J_AssertionResultMessages implements I_Tests4J_AssertionResultMessages {

	public static final String I_THROWER_IS_REQUIRED = "I_Thrower is required.";
	public static final String AN_UNEXPECTED_EXCEPTION_WAS_THROWN = "An Unexpected Exception was thrown.";
	public static final String NO_THROWABLES_WERE_THROWN_FROM_THE_I_THROWER = "No Throwables were thrown from the I_Thrower.";
	public static final String THE_TEXT_WAS_UNIFORM = "The text was uniform.";
	public static final String THE_TEXT_WAS_NOT_UNIFORM = "The text was NOT uniform.";
	public static final String NO_EVALUATOR_COULD_BE_FOUND_FOR_THE_FOLLOWING_CLASS = "No Evaluator could be found for the following class; ";
	public static final String THE_CLASS_OF_THE_ACTUAL_VALUE_IS_NOT_ASSIGNABLE_FROM_THE_CLASS_OF_THE_EXPECTED_VALUE = "The class of the actual value is not assignable from the class of the expected value.";
	public static final String THE_ACTUAL_VALUE_IS_NULL_AND_SHOULD_NOT_BE = "The Actual Value is null and should NOT be.";
	public static final String THE_EXPECTED_VALUE_SHOULD_NEVER_BE_NULL_TRY_ASSERT_NULL = "The Expected Value should never be null, try assertNull().";
	public static final String THE_EXPECTED_THROWABLE_DATA_DID_NOT_MATCH_THE_ACTUAL = "Either no Throwable was thrown or the expected Throwable was NOT uniform with the actual Throwable.";
	public static final String THE_COLLECTION_SHOULD_CONTAIN_THE_VALUE = "The Collection should contain the Value.";
	public static final String THE_ACTUAL_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_THE_EXPECTED_VALUE = "The actual Value should be greater than OR equal to the expected Value.";
	public static final String THE_OBJECTS_SHOULD_NOT_BE_UNIFORM = "The Objects should NOT be uniform.";
	public static final String THE_OBJECTS_SHOULD_BE_UNIFORM = "The Objects should be uniform.";
	public static final String THE_OBJECTS_SHOULD_NOT_BE_THE_SAME = "The Objects should NOT be the same.";
	public static final String THE_OBJECTS_SHOULD_NOT_BE_EQUAL = "The Objects should NOT be equal.";
	public static final String THE_OBJECTS_SHOULD_BE_THE_SAME = "The Objects should be the same.";
	public static final String THE_OBJECTS_SHOULD_BE_EQUAL = "The Objects should be equal.";
	public static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The Value should NOT be null.";
	public static final String THE_VALUE_SHOULD_BE_NULL = "The Value should be null.";
	public static final String THE_VALUE_SHOULD_BE_TRUE = "The Value should be true.";
	public static final String THE_VALUE_SHOULD_BE_FALSE = "The Value should be false.";
	
	@Override
	public String getTheExpectedThrowableDataDidNotMatchTheActual() {
		return THE_EXPECTED_THROWABLE_DATA_DID_NOT_MATCH_THE_ACTUAL;
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
	
	public String getNoEvaluatorFoundForClass() {
		return NO_EVALUATOR_COULD_BE_FOUND_FOR_THE_FOLLOWING_CLASS;
	}
	
	public String getTheExpectedValueShouldNeverBeNull() {
		return THE_EXPECTED_VALUE_SHOULD_NEVER_BE_NULL_TRY_ASSERT_NULL;
	}
	
	public String getTheActualValueIsNull() {
		return THE_ACTUAL_VALUE_IS_NULL_AND_SHOULD_NOT_BE;
	}
	
	public String getTheActualClassIsNotAssignableFromTheExpectedClass() {
		return THE_CLASS_OF_THE_ACTUAL_VALUE_IS_NOT_ASSIGNABLE_FROM_THE_CLASS_OF_THE_EXPECTED_VALUE;
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
	
	public String getAnUnexpectedExceptionWasThrown() {
		return AN_UNEXPECTED_EXCEPTION_WAS_THROWN;
	}
	
	public String getIThrowerIsRequired() {
		return I_THROWER_IS_REQUIRED;
	}
}
