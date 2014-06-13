package org.adligo.tests4j.models.shared.system.i18n.en.trials.asserts;

import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionResultMessages;

public class Tests4J_AssertionResultMessages implements I_Tests4J_AssertionResultMessages {

	public static final String THE_EXPECTED_THROWABLE_DATA_DID_NOT_MATCH_THE_ACTUAL = "Either no Throwable was thrown or the expected Throwable data did not match the actual Throwable data.";
	public static final String THE_COLLECTION_SHOULD_CONTAIN_THE_VALUE = "The collection should contain the value";
	public static final String THE_ACTUAL_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_THE_EXPECTED_VALUE = "The actual value should be greater than or equal to the expected value.";
	public static final String THE_FIRST_BYTE_SHOULD_NOT_BE_LESS_THAN_THE_SECOND_BYTE = "The first Byte should NOT be less than the second Byte.";
	public static final String NOT_GREATER_THAN_BYTE = "The first byte should NOT be greater than the last byte";
	public static final String THE_EXPECTED_BYTE_SHOULD_BE_LESS_THAN_THE_ACTUAL_BYTE = "The first Byte should be less than the second byte";
	public static final String THE_TWO_OBJECTS_SHOULD_NOT_BE_UNIFORM = "The two objects should not be uniform.";
	public static final String THE_TWO_OBJECTS_SHOULD_BE_UNIFORM = "The two objects should be uniform.";
	public static final String THE_TWO_OBJECTS_SHOULD_NOT_BE_THE_SAME = "The Two Objects should Not be the same.";
	public static final String THE_TWO_OBJECTS_SHOULD_NOT_BE_EQUAL = "The two objects should Not be Equal.";
	public static final String THE_TWO_OBJECTS_SHOULD_BE_THE_SAME = "The two objects should be the same.";
	public static final String THESE_OBJECT_SHOULD_EQUALS = "These object should equals.";
	public static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The value should not be null.";
	public static final String THE_VALUE_SHOULD_BE_NULL = "The value should be null.";
	public static final String THE_VALUE_SHOULD_BE_TRUE = "The value should be true.";
	public static final String THE_VALUE_SHOULD_BE_FALSE = "The value should be false.";
	
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
	public String getTheTwoObjectsShould_NOT_BeUniform() {
		return THE_TWO_OBJECTS_SHOULD_NOT_BE_UNIFORM;
	}
	public String getTheTwoObjectsShouldBeUniform() {
		return THE_TWO_OBJECTS_SHOULD_BE_UNIFORM;
	}
	public String getTheTwoObjectsShould_NOT_BeTheSame() {
		return THE_TWO_OBJECTS_SHOULD_NOT_BE_THE_SAME;
	}
	public String getTheTwoObjectsShould_NOT_BeEqual() {
		return THE_TWO_OBJECTS_SHOULD_NOT_BE_EQUAL;
	}
	public String getTheTwoObjectsShouldBeTheSame() {
		return THE_TWO_OBJECTS_SHOULD_BE_THE_SAME;
	}
	public String getTheTwoObjectsShouldBeEqual() {
		return THESE_OBJECT_SHOULD_EQUALS;
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
}
