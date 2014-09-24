package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiffType;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
/**
 * is NOT thread safe
 * @author scott
 *
 */
public class LineDiffTextDisplay {
	private I_Tests4J_LineDiffTextDisplayMessages constants =
			Tests4J_Constants.CONSTANTS.getLineDiffTextDisplayMessages();
	private I_TextLines actualLines;
	private I_TextLines exampleLines;

	
	@SuppressWarnings("incomplete-switch")
	public void display(I_Tests4J_Log out, I_TextLinesCompareResult result, int diffLimit) {
		actualLines = result.getActualLines();
		exampleLines = result.getExpectedLines();
		
		List<I_LineDiff> diffs =  result.getLineDiffs();
		
		int diffCount = 0;
		for (I_LineDiff diff: diffs) {
			if (diff != null) {
				I_LineDiffType pType = diff.getType();
				LineDiffType type = LineDiffType.get(pType);
				if (diffCount > diffLimit) {
					return;
				}
				I_DiffIndexesPair pair = diff.getIndexes();
				if (type == null) {
					out.log(constants.getError());
				} else if ( type != LineDiffType.Match) {
					
					Integer expected = diff.getExpectedLineNbr();
					Integer actual = diff.getActualLineNbr();
				
					String expectedLine = getExpectedLine(exampleLines,
							expected);
					
					String actualLine = getActualLine(actualLines, actual);
					
					if (expectedLine == null && actualLine == null) {
						out.log(constants.getError());
					} else {
					
						switch (type) {
							case PartialMatch:
								if (pair == null) {
									out.log(constants.getError());
									break;
								}
								if (expectedLine == null || actualLine == null) {
									out.log(constants.getError());
									break;
								} 
								diffCount++;
								
								I_DiffIndexes exampleIndexes = pair.getExpected();
								I_DiffIndexes actualIndexes = pair.getActual();
								
								try {
									out.log(constants.getTheLineOfTextIsDifferent());
									
									String [] exampleDiffs = exampleIndexes.getDifferences(expectedLine);
									
									out.log(constants.getExpected() + " " + expected);
									out.log(expectedLine);
									if (exampleDiffs.length >= 1) {
										out.log(constants.getDifferences());
										for (int i = 0; i < exampleDiffs.length; i++) {
											out.log("'" + exampleDiffs[i] + "'");
										}
									}
									out.log(constants.getActual()  + " " + actual);
									out.log(actualLine);
									String [] actualDiffs = actualIndexes.getDifferences(actualLine);
									if (actualDiffs.length >= 1) {
										out.log(constants.getDifferences());
										for (int i = 0; i < actualDiffs.length; i++) {
											out.log("'" + actualDiffs[i] + "'");
										}	
									}
								} catch (StringIndexOutOfBoundsException x) {
									out.log(constants.getError());
								}
								break;
							case MissingActualLine:
								if (actualLine != null) {
									diffCount++;
									out.log(constants.getTheFollowingActualLineOfTextIsMissing() + actual);
									out.log(actualLine);
								} else {
									out.log(constants.getError());
								}
								break;
							case MissingExpectedLine:
								if (expectedLine != null) {
									diffCount++;
									out.log(constants.getTheFollowingExpectedLineOfTextIsMissing() + expected);
									out.log(expectedLine);
								} else {
									out.log(constants.getError());
								}
								break;
						}
					}
				}
			}
		}
	}

	public String getActualLine(I_TextLines actualLines, Integer actual) {
		String actualLine = null;
		if (actual != null ) {
			if (actual >= 0 && actual < actualLines.getLines()) {
				actualLine = actualLines.getLine(actual);
			}
		}
		return actualLine;
	}

	public String getExpectedLine(I_TextLines exampleLines, int expected) {
		String expectedLine = null;
		if (expected >= 0 && expected < exampleLines.getLines()) {
			expectedLine = exampleLines.getLine(expected);
		}
		return expectedLine;
	}

	
}
