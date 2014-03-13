package org.adligo.jtests.models.shared.results;

import java.util.List;

import org.adligo.jtests.models.shared.common.TestType;

public interface I_TestResult {

	public abstract String getTestName();

	public abstract String getTestedClassName();

	public abstract String getTestedPackageName();

	public abstract String getTestedUseCaseName();

	public abstract TestType getTestType();

	public abstract List<ExhibitResultMutant> getExhibitResults();

	public abstract boolean isIgnored();

	public abstract String getBeforeTestOutput();

	public abstract String getAfterTestOutput();
	
	public I_Failure getFailure();
}