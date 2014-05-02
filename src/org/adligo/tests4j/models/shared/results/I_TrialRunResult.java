package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

/**
 * the summary result of a trial run
 * @author scott
 *
 */
public interface I_TrialRunResult {
	/**
	 * the time stamp (System.currentTimeMillis()
	 * when this run started.
	 * @return
	 */
	public long getStartTime();
	/**
	 * the milliseconds that this trial run took.
	 * @return
	 */
	public long getRunTime();
	/**
	 * the Seconds that this trial run took.
	 * @return
	 */
	public BigDecimal getRunTimeSecs();
	/**
	 * the code covered by this entire test run.
	 * @return
	 */
	public List<I_PackageCoverage> getCoverage();
	/**
	 * all of the trials including ignored trials.
	 * @return
	 */
	public int getTrials();
	/**
	 * the number of trials which were ignored.
	 * @return
	 */
	public int getTrialsIgnored();
	/**
	 * the number of trials that passed
	 * @return
	 */
	public int getTrialsPassed();
	/**
	 * then number of trials tha failed.
	 * @return
	 */
	public int getTrialFailures();
	
	/**
	 * all of the tests including ignored tests.
	 * @return
	 */
	public long getTests();
	/**
	 * the number of tests ignored.
	 * @return
	 */
	public long getTestsIgnored();
	/**
	 * the number of tests that passed.
	 * @return
	 */
	public long getTestsPassed();
	/**
	 * the numbe of test failures.
	 * @return
	 */
	public long getTestFailures();
	/**
	 * the number of completed assertX methods.
	 * @return
	 */
	public long getAsserts();
	/**
	 * the number of unique assertions
	 * where uniqueness is calculated based on
	 * the hashCode of the AssertCommand instance
	 * and is specific to a Trial.   So
	 * if two different trials call assertEquals('a','a');
	 * they would be considered two unique asserts.
	 * But if a single trial called assertEquals('a','a');
	 * they would be considered a single unique assert.
	 * 
	 * @return
	 */
	public long getUniqueAsserts();
	


}
