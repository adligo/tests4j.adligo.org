package org.adligo.tests4j.shared.en;

import org.adligo.tests4j.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;

public class Tests4J_LineDiffTextDisplayMessages implements I_Tests4J_LineDiffTextDisplayMessages {
	private static final String THE_FOLLOWING_EXPECTED_LINE_IS_MISSING_AFTER_THE_ACTUAL_TEXT = "The following expected line of text is missing in the actual lines of text; ";
	private static final String THE_FOLLOWING_ACTUAL_LINE_IS_MISSING_IN_THE_EXPECTED_TEXT = "The following actual line of text is missing in the expected lines of text; ";
	private static final String ACTUAL = "Actual;";
	private static final String EXPECTED = "Expected;";
	private static final String DIFFERENCES = "Differences;";
	private static final String LINES_ARE_DIFFERENT_FROM_THE_ACTUAL_LINE_AS_FOLLOWS = "The lines of text are different as follows;";
	private static final String TESTS4J_ERROR = "There is a Tests4j error reporting text line differences, please submit a test case.";
	
	Tests4J_LineDiffTextDisplayMessages () {} 
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_LineDiffTextDisplayConstants#getTheFollowingExpectedLineIsMissing()
	 */
	@Override
	public String getTheFollowingExpectedLineOfTextIsMissing() {
		return THE_FOLLOWING_EXPECTED_LINE_IS_MISSING_AFTER_THE_ACTUAL_TEXT;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_LineDiffTextDisplayConstants#getTheFollowingActualLineIsMissing()
	 */
	@Override
	public String getTheFollowingActualLineOfTextIsMissing() {
		return THE_FOLLOWING_ACTUAL_LINE_IS_MISSING_IN_THE_EXPECTED_TEXT;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_LineDiffTextDisplayConstants#getActual()
	 */
	@Override
	public String getActual() {
		return ACTUAL;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_LineDiffTextDisplayConstants#getExpected()
	 */
	@Override
	public String getExpected() {
		return EXPECTED;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_LineDiffTextDisplayConstants#getTheLinesAreDifferent()
	 */
	@Override
	public String getTheLineOfTextIsDifferent() {
		return LINES_ARE_DIFFERENT_FROM_THE_ACTUAL_LINE_AS_FOLLOWS;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_LineDiffTextDisplayConstants#getDifferences()
	 */
	@Override
	public String getDifferences() {
		return DIFFERENCES;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_LineDiffTextDisplayConstants#getError()
	 */
	@Override
	public String getError() {
		return TESTS4J_ERROR;
	}
}
