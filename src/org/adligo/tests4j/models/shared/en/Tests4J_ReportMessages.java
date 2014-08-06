package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;

/**
 * EOS = end of sentence.
 * 
 * @author scott
 *
 */
public class Tests4J_ReportMessages implements I_Tests4J_ReportMessages {
	private static final String THE_FOLLOWING_ACTUAL_LINE_NUMBERS_NOT_IN_THE_EXPECTED_TEXT = "The following actual line numbers not in the expected text: ";

	private static final String THE_FOLLOWING_EXPECTED_LINE_NUMBERS_WERE_MISSING = "The following expected line numbers were missing: ";

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

	@Override
	public String getTestHeading() {
		return TEST;
	}

	@Override
	public String getTrialHeading() {
		return TRIAL;
	}
	
	public String getStartingTrial() {
		return STARTING_TRIAL;
	}
	
	public String getStartingTest() {
		return STARTING_TEST;
	}
	
	public String getExpected() {
		return EXPECTED;
	}
	
	public String getActual() {
		return ACTUAL;
	}
	
	public String getClassHeadding() {
		return CLASS;
	}
	
	public String getDifferences() {
		return DIFFERENCES;
	}
	
	public String getTheFollowingExpectedLineNumbersWereMissing() {
		return THE_FOLLOWING_EXPECTED_LINE_NUMBERS_WERE_MISSING;
	}
	
	public String getTheFollowingActualLineNumberNotExpected() {
		return THE_FOLLOWING_ACTUAL_LINE_NUMBERS_NOT_IN_THE_EXPECTED_TEXT;
	}
}
