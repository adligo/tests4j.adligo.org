package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.models.shared.common.StringMethods;

public class LineDiffTextDisplay {

	public static void display(I_LineOut out, I_TextLinesCompareResult result) {
		I_TextLines actualLines = result.getActualLines();
		I_TextLines exampleLines = result.getExpectedLines();
		
		List<I_LineDiff> diffs =  result.getLineDiffs();
		
		for (I_LineDiff diff: diffs) {
			if (diff != null) {
				LineDiffType type = diff.getType();
				if (type != LineDiffType.MATCH) {
					int expected = diff.getExampleLineNbr();
					String expectedLine = null;
					if (expected >= 0 && expected < exampleLines.getLines()) {
						expectedLine = exampleLines.getLine(expected);
					}
					Integer actual = diff.getActualLineNbr();
					String actualLine = null;
					if (actual != null ) {
						if (actual >= 0 && actual < actualLines.getLines()) {
							actualLine = actualLines.getLine(actual);
						}
					}
					
					switch (type) {
						case PARTIAL_MATCH:
							I_DiffIndexesPair pair = diff.getIndexes();
							I_DiffIndexes exampleIndexes = pair.getExpected();
							I_DiffIndexes actualIndexes = pair.getActual();
							if (exampleIndexes == null || actualIndexes == null || actualLine == null) {
								out.println("Expected line " + expected + " is different from the actual line as follows;");
								out.println("Expected; '" + expectedLine + "'");
								out.println("Actual; '" + actualLine + "'");
							} else {
								out.println("Expected line " + expected + " is different from the actual line as follows;");
								
								if (exampleIndexes.getDiffLeftToRight() == null) {
									out.println("Expected; '" + expectedLine + "'");
								} else {
									String [] exampleDiffs = exampleIndexes.getDifferences(expectedLine);
									
									for (int i = 0; i < exampleDiffs.length; i++) {
										out.println("Expected; '" + exampleDiffs[i] + "'");
									}
								}
								
								if (actualIndexes.getDiffLeftToRight() == null) {
									out.println("Actual; '" + actualLine + "'");
								} else {
									String [] actualDiffs = actualIndexes.getDifferences(actualLine);
									
									for (int i = 0; i < actualDiffs.length; i++) {
										out.println("Actual; '" + actualDiffs[i] + "'");
									}
								}
							}
							break;
						case MISSING_ACTUAL_LINE:
								out.println("The following actual line " + actual + " is missing in the expected text;");
								out.println(actualLine);
							break;
						case MISSING_EXAMPLE_LINE:
							out.println("The following example line " + expected + " is missing after the actual text;");
							out.println(expectedLine);
							break;
					}
					break;
				}
			}
		}
	}
	
	
}
