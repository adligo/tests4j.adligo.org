package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;

public class Tests4J_AssertionInputMessages implements I_Tests4J_AssertionInputMessages {

	private static final String EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_THROWABLE_CLASS = "ExpectedThrownData requires a non null throwable class.";
	private static final String THE_EXPECTED_VALUE_SHOULD_NEVER_BE_NULL_TRY_ASSERT_NULL = "The expected value should never be null, try assertNull().";
	private static final String NO_EVALUATOR_COULD_BE_FOUND_FOR_THE_FOLLOWING_CLASS = "No evaluator could be found for the following class; ";
	private static final String THE_CLASS_OF_THE_ACTUAL_VALUE_IS_NOT_ASSIGNABLE_FROM_THE_CLASS_OF_THE_EXPECTED_VALUE = "The class of the actual value is not assignable from the class of the expected value.";
	private static final String THE_ACTUAL_VALUE_IS_NULL_AND_SHOULD_NOT_BE = "The actual value is null and should NOT be.";
	private static final String I_THROWER_IS_REQUIRED = "I_Thrower is required.";
	
	Tests4J_AssertionInputMessages() {
	}
	

	public String getTheActualValueIsNull() {
		return THE_ACTUAL_VALUE_IS_NULL_AND_SHOULD_NOT_BE;
	}
	
	public String getTheActualClassIsNotAssignableFromTheExpectedClass() {
		return THE_CLASS_OF_THE_ACTUAL_VALUE_IS_NOT_ASSIGNABLE_FROM_THE_CLASS_OF_THE_EXPECTED_VALUE;
	}
	
	@Override
	public String getExpectedThrownDataRequiresThrowable() {
		return EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_THROWABLE_CLASS;
	}


	public String getNoEvaluatorFoundForClass() {
		return NO_EVALUATOR_COULD_BE_FOUND_FOR_THE_FOLLOWING_CLASS;
	}
	
	public String getTheExpectedValueShouldNeverBeNull() {
		return THE_EXPECTED_VALUE_SHOULD_NEVER_BE_NULL_TRY_ASSERT_NULL;
	}
	

	public String getIThrowerIsRequired() {
		return I_THROWER_IS_REQUIRED;
	}
}
