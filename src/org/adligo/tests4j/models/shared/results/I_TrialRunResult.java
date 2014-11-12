package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;

/**
 * the summary result of a trial run
 * @author scott
 *  
 *  //TODO extract TimeFormat
 *    for getRunTimeSecs()
 *    and methods to it getRunTimeMintues(), getRunTimeHours(), getRunTimeDays()
 *    
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
	public List<I_PackageCoverageBrief> getCoverage();
	
	/**
	 * @return true if any coverage was recorded.
	 */
	public boolean hasCoverage();
	
	/**
	 * the total percentage of all
	 * coverage units which were
	 * covered by the trial run. 
	 * @return
	 */
	public double getCoveragePercentage();
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
	public int getTests();
	/**
	 * the number of tests ignored.
	 * @return
	 */
	public int getTestsIgnored();
	/**
	 * the number of tests that passed.
	 * @return
	 */
	public int getTestsPassed();
	/**
	 * the number of test failures.
	 * @return
	 */
	public int getTestsFailed();
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
	
	/**
	 * 
	 * @return the names of the passing trials,
	 * so we can make sure all trials were ran for
	 * a specific package.
	 * 
	 */
	public Set<String> getPassingTrials();

  /**
   * 
   * @return the names of the failing trials,
   * so we can make sure all trials were ran for
   * a specific package.
   * 
   */
  public Set<String> getFailingTrials();
    
  /**
   * 
   * @return the names of the passing or 
   * failing trials which have a @IgnoredTrial
   * or a @IgnoreTest on one of the test methods.
   * 
   */
  public Set<String> getIgnoredTrials();
}