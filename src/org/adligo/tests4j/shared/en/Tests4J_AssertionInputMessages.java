package org.adligo.tests4j.shared.en;

import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;

public class Tests4J_AssertionInputMessages implements I_Tests4J_AssertionInputMessages {

	private static final String EXPECTED_THROWN_DATA_INSTANCES_WITH_A_MATCH_TYPE_OF_EQUALS_OR_CONTAINS_REQUIRE_A_THROWABLE_MESSAGE =
      "ExpectedThrownData instances with a MatchType of Equals or Contains require a throwable message.";
  private static final String THE_EXPECTED_THROWN_DATA_FOR_A_ASSERT_THROWN_UNIFORM_METHOD_MUST_HAVE_A_MESSAGE_MATCH_TYPE_OF_ANY_EQUALS_OR_NULL =
      "The expected thrown data for a assertThrownUniform method must have a message match type of any, equals or null.";
  private static final String EXPECTED_THROWN_DATA_REQUIRES_A_NON_NULL_THROWABLE_CLASS = "ExpectedThrownData requires a non null throwable class.";
	private static final String THE_EXPECTED_VALUE_SHOULD_NEVER_BE_NULL_TRY_ASSERT_NULL = "The expected value should never be null, try assertNull().";
	private static final String NO_EVALUATOR_COULD_BE_FOUND_FOR_THE_FOLLOWING_CLASS = "No evaluator could be found for the following class; ";
	private static final String THE_CLASS_OF_THE_ACTUAL_VALUE_IS_NOT_ASSIGNABLE_FROM_THE_CLASS_OF_THE_EXPECTED_VALUE = "The class of the actual value is not assignable from the class of the expected value.";
	private static final String THE_ACTUAL_VALUE_IS_NULL_AND_SHOULD_NOT_BE = "The actual value is null and should NOT be.";
	private static final String I_THROWER_IS_REQUIRED = "I_Thrower is required.";
	private static final String THE_UNIFORM_THROWN_EVALUATOR_IS_NULL = "The uniform thown evaluator is null.";
	
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
	
	public String getTheUniformThrownEvaluatorIsNull() {
		return THE_UNIFORM_THROWN_EVALUATOR_IS_NULL;
	}
	
	public String getThrownUniformExpectedThrownDataMustBeMatchTypeAnyEqualsOrNull() {
	  return THE_EXPECTED_THROWN_DATA_FOR_A_ASSERT_THROWN_UNIFORM_METHOD_MUST_HAVE_A_MESSAGE_MATCH_TYPE_OF_ANY_EQUALS_OR_NULL;
	}
	
	public String getExpectedThrownDataWithEqualsOrContainMatchTypesRequireAMessage() {
	  return EXPECTED_THROWN_DATA_INSTANCES_WITH_A_MATCH_TYPE_OF_EQUALS_OR_CONTAINS_REQUIRE_A_THROWABLE_MESSAGE;
	}
}
