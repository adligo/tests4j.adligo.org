package org.adligo.tests4j.shared.en;

import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;

/**
 * EOS = end of sentence.
 * 
 * @author scott
 *
 */
public class Tests4J_ReportMessages implements I_Tests4J_ReportMessages {
  private static final String ON_THREAD_Z = " on thread <Z/> ";
	private static final String TRIAL_DESCRIPTIONS_IN_STMT = "trial descriptions";
	private static final String NON_META_TRIALS = "non-meta trials";
	private static final String PROCESS_V_HAS_X_RUNNABLES_RUNNING_AND_Y_Z_DONE = 
	    "Process <V/> has <X/> runnables running and <Y/> <Z/> done.";
	private static final String STARTING_PROCESS_X_WITH_Y_THREADS = 
	    "Starting process <X/> with <Y/> threads.";
	private static final String THE_FOLLOWING_ACTUAL_LINE_NUMBERS_NOT_IN_THE_EXPECTED_TEXT = 
	    "The following actual line numbers not in the expected text: ";
	private static final String THE_FOLLOWING_EXPECTED_LINE_NUMBERS_WERE_MISSING = 
	    "The following expected line numbers were missing: ";
	private static final String THE_FOLLOWING_TEXT_WAS_NOT_FOUND_IN_THE_ACTUAL_TEXT = 
	    "The following text was NOT found in the actual text;";
	private static final String THE_STACK_TRACE_FOLLOWS = "The stack trace follows;";
	private static final String DIFFERENCES = "Differences;";
	private static final String CLASS = "Class: ";
	private static final String ACTUAL = "Actual;";
	private static final String EXPECTED = "Expected;";
	private static final String DONE = "100.0% done.";
	private static final String FAILED = "Failed!";
	private static final String FAILED_EOS = " failed.";
	private static final String INDENT = "\t";
	private static final String METADATA_CALCULATED = " Metadata Calculated: ";
	private static final String PASSED = "Passed!";
	private static final String PASSED_EOS = " passed.";
	private static final String PCT_COMPLETE = "% complete...";
	private static final String TEST = " Test: ";
	private static final String TESTS = " Tests: ";
	private static final String TRIAL = " Trial: ";
	private static final String TRIALS = " Trials: ";
	private static final String STARTING_TRIAL = " starting Trial: ";
	private static final String STARTING_TEST = " starting Test: ";
	
	

	
	Tests4J_ReportMessages() {}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getPctDoneEOS()
	 */
	@Override
	public String getDoneEOS() {
		return DONE;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getPctComplete()
	 */
	@Override
	public String getPctComplete() {
		return PCT_COMPLETE;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getPassedEOS()
	 */
	@Override
	public String getPassedEOS() {
		return PASSED_EOS;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getFailedEOS()
	 */
	@Override
	public String getFailedEOS() {
		return FAILED_EOS;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTestsHeading()
	 */
	@Override
	public String getTestsHeading() {
		return TESTS;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTrialsHeading()
	 */
	@Override
	public String getTrialsHeading() {
		return TRIALS;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getMetadataCalculatedHeading()
	 */
	@Override
	public String getMetadataCalculatedHeading() {
		return METADATA_CALCULATED;
	}
	
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getFailedFlag()
	 */
	@Override
	public String getFailedFlag() {
		return FAILED;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getPassedFlag()
	 */
	@Override
	public String getPassedFlag() {
		return PASSED;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getIndentFlag()
	 */
	@Override
	public String getIndent() {
		return INDENT;
	}
	
	/* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTestHeading()
   */
	@Override
	public String getTestHeading() {
		return TEST;
	}

	/* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTrialHeading()
   */
  @Override
	public String getTrialHeading() {
		return TRIAL;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getStartingTrial()
   */
  @Override
	public String getStartingTrial() {
		return STARTING_TRIAL;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getStartingTest()
   */
  @Override
	public String getStartingTest() {
		return STARTING_TEST;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getExpected()
   */
  @Override
	public String getExpected() {
		return EXPECTED;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getActual()
   */
  @Override
	public String getActual() {
		return ACTUAL;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getClassHeadding()
   */
  @Override
	public String getClassHeadding() {
		return CLASS;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getDifferences()
   */
  @Override
	public String getDifferences() {
		return DIFFERENCES;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTheFollowingExpectedLineNumbersWereMissing()
   */
  @Override
	public String getTheFollowingExpectedLineNumbersWereMissing() {
		return THE_FOLLOWING_EXPECTED_LINE_NUMBERS_WERE_MISSING;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTheFollowingTextWasNotFoundInTheActualText()
   */
  @Override
	public String getTheFollowingTextWasNotFoundInTheActualText() {
	   return THE_FOLLOWING_TEXT_WAS_NOT_FOUND_IN_THE_ACTUAL_TEXT;
	}
	 
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTheStackTraceFollows()
   */
  @Override
	public String getTheStackTraceFollows() {
	  return THE_STACK_TRACE_FOLLOWS;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTheFollowingActualLineNumberNotExpected()
   */
  @Override
	public String getTheFollowingActualLineNumberNotExpected() {
		return THE_FOLLOWING_ACTUAL_LINE_NUMBERS_NOT_IN_THE_EXPECTED_TEXT;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getStartingProcessXWithYThreads()
   */
  @Override
	public String getStartingProcessXWithYThreads() {
		return STARTING_PROCESS_X_WITH_Y_THREADS;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getProcessVhasXRunnablesRunningAndYZdone()
   */
  @Override
	public String getProcessVhasXRunnablesRunningAndYZdone() {
		return PROCESS_V_HAS_X_RUNNABLES_RUNNING_AND_Y_Z_DONE;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getNonMetaTrials()
   */
  @Override
	public String getNonMetaTrials() {
		return NON_META_TRIALS;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getTrialDescriptionsInStatement()
   */
  @Override
	public String getTrialDescriptionsInStatement() {
		return TRIAL_DESCRIPTIONS_IN_STMT;
	}
	
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ReportMessages#getOnThreadZ()
   */
  @Override
	public String getOnThreadZ() {
		return ON_THREAD_Z;
	}
	
}
