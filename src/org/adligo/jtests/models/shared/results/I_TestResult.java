package org.adligo.jtests.models.shared.results;

import java.util.List;

import org.adligo.jtests.models.shared.common.TestType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public interface I_TestResult {

	public abstract String getTestName();

	public abstract String getTestedClassName();

	public abstract String getTestedPackageName();

	public abstract TestType getTestType();

	public abstract List<I_ExhibitResult> getExhibitResults();

	public abstract boolean isIgnored();

	public abstract String getBeforeTestOutput();

	public abstract String getAfterTestOutput();
	
	public I_PackageCoverage getPackageCoverage();
	public I_ClassCoverage getClassCoverage();
	
	public I_TestFailure getFailure();
	public boolean isPassed();
	public int getExhibitCount();
	public int getAssertionCount();
}