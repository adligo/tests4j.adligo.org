package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_LineDiffTextDisplayConstants;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

import sun.util.logging.resources.logging;

import com.sun.medialib.mlib.Constants;
/**
 * is NOT thread safe
 * @author scott
 *
 */
public class LineDiffTextDisplay {
	private I_Tests4J_LineDiffTextDisplayConstants constants =
			Tests4J_Constants.CONSTANTS.getLineDiffTextDisplayMessages();
	private I_TextLines actualLines;
	private I_TextLines exampleLines;
	private int expected;
	private String expectedLine;
	private Integer actual;
	private String actualLine;
	
	public void display(I_LineOut out, I_TextLinesCompareResult result, int diffLimit) {
		actualLines = result.getActualLines();
		exampleLines = result.getExpectedLines();
		
		List<I_LineDiff> diffs =  result.getLineDiffs();
		
		int diffCount = 0;
		for (I_LineDiff diff: diffs) {
			if (diff != null) {
				LineDiffType type = diff.getType();
				if (diffCount > diffLimit) {
					return;
				}
				I_DiffIndexesPair pair = diff.getIndexes();
				if (type == null) {
					out.println(constants.getError());
				} else if ( type != LineDiffType.MATCH) {
					
					Integer expected = diff.getExpectedLineNbr();
					Integer actual = diff.getActualLineNbr();
				
					String expectedLine = getExpectedLine(exampleLines,
							expected);
					
					String actualLine = getActualLine(actualLines, actual);
					
					if (expectedLine == null && actualLine == null) {
						out.println(constants.getError());
					} else {
					
						switch (type) {
							case PARTIAL_MATCH:
								if (pair == null) {
									out.println(constants.getError());
									break;
								}
								if (expectedLine == null || actualLine == null) {
									out.println(constants.getError());
									break;
								} 
								diffCount++;
								
								I_DiffIndexes exampleIndexes = pair.getExpected();
								I_DiffIndexes actualIndexes = pair.getActual();
								
								try {
									out.println(constants.getTheLineOfTextIsDifferent());
									
									String [] exampleDiffs = exampleIndexes.getDifferences(expectedLine);
									
									out.println(constants.getExpected() + " " + expected);
									out.println(expectedLine);
									if (exampleDiffs.length >= 1) {
										out.println(constants.getDifferences());
										for (int i = 0; i < exampleDiffs.length; i++) {
											out.println("'" + exampleDiffs[i] + "'");
										}
									}
									out.println(constants.getActual()  + " " + actual);
									out.println(actualLine);
									String [] actualDiffs = actualIndexes.getDifferences(actualLine);
									if (actualDiffs.length >= 1) {
										out.println(constants.getDifferences());
										for (int i = 0; i < actualDiffs.length; i++) {
											out.println("'" + actualDiffs[i] + "'");
										}	
									}
								} catch (StringIndexOutOfBoundsException x) {
									out.println(constants.getError());
								}
								break;
							case MISSING_ACTUAL_LINE:
								if (actualLine != null) {
									diffCount++;
									out.println(constants.getTheFollowingActualLineOfTextIsMissing() + actual);
									out.println(actualLine);
								} else {
									out.println(constants.getError());
								}
								break;
							case MISSING_EXPECTED_LINE:
								if (expectedLine != null) {
									diffCount++;
									out.println(constants.getTheFollowingExpectedLineOfTextIsMissing() + expected);
									out.println(expectedLine);
								} else {
									out.println(constants.getError());
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
