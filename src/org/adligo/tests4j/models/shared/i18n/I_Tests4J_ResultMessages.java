package org.adligo.tests4j.models.shared.i18n;

public interface I_Tests4J_ResultMessages {
	public String getTheExpectedThrowableDataWasNotUniformTheActual();
	public String getTheCollectionShouldContainTheValue();
	public String getTheActualShouldBeGreaterThanOrEqualToTheExpected();
	public String getTheObjectsShould_NOT_BeUniform();
	public String getTheObjectsShouldBeUniform();
	public String getTheObjectsShould_NOT_BeTheSame();
	public String getTheObjectsShould_NOT_BeEqual();
	public String getTheObjectsShouldBeTheSame();
	public String getTheObjectsShouldBeEqual();
	public String getTheValueShould_NOT_BeNull();
	public String getTheValueShouldBeNull();
	public String getTheValueShouldBeTrue();
	public String getTheValueShouldBeFalse();
	
	public String getTheTextWasNOT_Uniform();
	public String getTheTextWasUniform();
	public String getNothingWasThrown();
	public String getThrowableClassMismatch();
	public String getThrowableMessageNotEquals();
	public String getThrowableMessageNotUniform();
	public String getTheExpectedThrowableDidNotMatch();
	public String getAnUnexpectedExceptionWasThrown();
	
	public String getTestTimedOut();
	public String getTrialTimedOut();
	public String getCodeCoveragIsOff();
	public String getSourceClassHasCircularDependency();
	public String getCalledMethodOrFieldsOutsideOfAllowedDepenencies();
}
