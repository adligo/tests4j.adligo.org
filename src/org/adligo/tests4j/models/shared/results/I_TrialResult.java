package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.shared.common.I_TrialType;

public interface I_TrialResult {

	/**
	 * The unique name of the trial run
	 * ie if a trial is run more than once
	 * org/adligo/tests4j_tests/trials_api/AssertionsFail_Trial[0]
	 * org/adligo/tests4j_tests/trials_api/AssertionsFail_Trial[1]
	 * org/adligo/tests4j_tests/trials_api/AssertionsFail_Trial[2]
	 * exc
	 * @return
	 */
	public abstract String getName();
	/**
	 * the name of the trial class ie
	 * org.adligo.tests4j_tests.trials_api.AssertionsFail_Trial
	 * @return
	 */
	public abstract String getTrialClassName();

	public abstract I_TrialType getType();

	public abstract List<I_TestResult> getResults();

	public abstract boolean isIgnored();

	public abstract String getBeforeTrialOutput();

	public abstract String getAfterTrialOutput();
	
	public List<I_TrialFailure> getFailures();
	public boolean isPassed();
	public int getTestCount();
	public int getTestFailureCount();
	public int getAssertionCount();
	public int getUniqueAssertionCount();
	public I_TrialResult clone(boolean cloneRelations);
	
	public abstract boolean hasRecordedCoverage();
}