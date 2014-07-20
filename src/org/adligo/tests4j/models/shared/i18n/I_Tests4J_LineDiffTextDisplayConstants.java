package org.adligo.tests4j.models.shared.i18n;

public interface I_Tests4J_LineDiffTextDisplayConstants {

	public abstract String getTheFollowingExpectedLineOfTextIsMissing();

	public abstract String getTheFollowingActualLineOfTextIsMissing();

	public abstract String getActual();

	public abstract String getExpected();

	public abstract String getTheLineOfTextIsDifferent();

	public String getDifferences();
	
	public String getError();
}