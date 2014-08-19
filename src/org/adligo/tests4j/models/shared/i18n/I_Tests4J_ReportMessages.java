package org.adligo.tests4j.models.shared.i18n;

public interface I_Tests4J_ReportMessages {

	public abstract String getDoneEOS();

	public abstract String getPctComplete();

	public abstract String getPassedEOS();

	public abstract String getFailedEOS();

	public abstract String getTestsHeading();

	public abstract String getTrialsHeading();

	public abstract String getMetadataCalculatedHeading();

	public abstract String getTestHeading();

	public abstract String getTrialHeading();
	
	public abstract String getFailedFlag();

	public abstract String getPassedFlag();

	public abstract String getIndent();
	
	public String getStartingTrial();
	
	public String getStartingTest();

	public String getExpected();
	
	public String getActual();
	
	public String getClassHeadding();
	
	public String getDifferences();
	
	public String getTheFollowingExpectedLineNumbersWereMissing();
	
	public String getTheFollowingActualLineNumberNotExpected();
	
	public String getStartingProcessXWithYThreads();
	
	public String getProcessVhasXRunnablesRunningAndYZdone();
	
	public String getNonMetaTrials();
	
	public String getTrialDescriptionsInStatement();
}