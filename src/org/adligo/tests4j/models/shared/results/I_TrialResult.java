package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialType;

public interface I_TrialResult {

	public abstract String getName();


	public abstract TrialType getType();

	public abstract List<I_TestResult> getResults();

	public abstract boolean isIgnored();

	public abstract String getBeforeTrialOutput();

	public abstract String getAfterTrialOutput();
	
	public I_TrialFailure getFailure();
	public boolean isPassed();
	public int getTestCount();
	public int getTestFailureCount();
	public int getAssertionCount();
	public int getUniqueAssertionCount();
	public I_TrialResult clone(boolean cloneRelations);
	public boolean isHadAfterTrialTests();
	public boolean isRanAfterTrialTests();
	
	public abstract boolean hasRecordedCoverage();
}