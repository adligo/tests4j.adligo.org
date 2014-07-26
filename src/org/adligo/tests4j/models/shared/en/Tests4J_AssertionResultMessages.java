package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionResultMessages;

public class Tests4J_AssertionResultMessages implements I_Tests4J_AssertionResultMessages {

	private static final String AN_UNEXPECTED_EXCEPTION_WAS_THROWN = "An Unexpected Exception was thrown.";
	private static final String NO_THROWABLES_WERE_THROWN_FROM_THE_I_THROWER = "No Throwables were thrown from the I_Thrower.";
	private static final String THE_TEXT_WAS_UNIFORM = "The text was uniform.";
	private static final String THE_TEXT_WAS_NOT_UNIFORM = "The text was NOT uniform.";
	
	private static final String THE_EXPECTED_THROWABLE_DATA_DID_NOT_MATCH_THE_ACTUAL = "Either no Throwable was thrown or the expected Throwable was NOT uniform with the actual Throwable.";
	private static final String THE_COLLECTION_SHOULD_CONTAIN_THE_VALUE = "The Collection should contain the Value.";
	private static final String THE_ACTUAL_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_THE_EXPECTED_VALUE = "The actual Value should be greater than OR equal to the expected Value.";
	private static final String THE_OBJECTS_SHOULD_NOT_BE_UNIFORM = "The Objects should NOT be uniform.";
	private static final String THE_OBJECTS_SHOULD_BE_UNIFORM = "The Objects should be uniform.";
	private static final String THE_OBJECTS_SHOULD_NOT_BE_THE_SAME = "The Objects should NOT be the same.";
	private static final String THE_OBJECTS_SHOULD_NOT_BE_EQUAL = "The Objects should NOT be equal.";
	private static final String THE_OBJECTS_SHOULD_BE_THE_SAME = "The Objects should be the same.";
	private static final String THE_OBJECTS_SHOULD_BE_EQUAL = "The Objects should be equal.";
	private static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The Value should NOT be null.";
	private static final String THE_VALUE_SHOULD_BE_NULL = "The Value should be null.";
	private static final String THE_VALUE_SHOULD_BE_TRUE = "The Value should be true.";
	private static final String THE_VALUE_SHOULD_BE_FALSE = "The Value should be false.";
	
	Tests4J_AssertionResultMessages() {}
	
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
	
}
