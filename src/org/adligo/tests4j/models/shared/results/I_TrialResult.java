package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_ClassCoverage;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public interface I_TrialResult {

	public abstract String getName();

	public abstract String getTestedClassName();

	public abstract String getTestedPackageName();

	public abstract TrialTypeEnum getType();

	public abstract List<I_TestResult> getResults();

	public abstract boolean isIgnored();

	public abstract String getBeforeTestOutput();

	public abstract String getAfterTestOutput();
	
	public I_PackageCoverage getPackageCoverage();
	public I_ClassCoverage getClassCoverage();
	
	public I_TrialFailure getFailure();
	public boolean isPassed();
	public int getTestCount();
	public int getTestFailureCount();
	public int getAssertionCount();
	public int getUniqueAssertionCount();
}