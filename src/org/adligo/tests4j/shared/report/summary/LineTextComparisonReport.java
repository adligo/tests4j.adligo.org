package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;

public class LineTextComparisonReport {

	public static void display(I_Tests4J_Reporter out, I_TextLinesCompareResult result) {
		/*
		out.log("Expected;");
		out.log(result.getExpected());
		out.log("Actual;");
		out.log(result.getActual());
		
		List<LineDiff> lineDiffs = result.getLineDiffs();
		if (lineDiffs.size() >= 1) {
			for (LineDiff line: lineDiffs) {
				String example = line.getExample();
				String actual = line.getActual();
				int linenumber = line.getLineNumber();
				out.log("Line Number " + linenumber + " should have;");
				StartEndDiffPair pair = line.getStartEndDiffs();
				
				print(out, example, pair.getExample());
				out.log("but had;");
				print(out, actual, pair.getActual());
			}
		} 
		*/
	}
	
	private static void print(I_Tests4J_Reporter out, String content, I_DiffIndexes diff) {
		String inDiff = content.substring(diff.getStart(), diff.getEnd());
		out.log(inDiff);
	}
	
}
