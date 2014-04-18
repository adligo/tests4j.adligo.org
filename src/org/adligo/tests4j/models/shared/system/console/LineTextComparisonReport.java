package org.adligo.tests4j.models.shared.system.console;

import java.util.List;

import org.adligo.tests4j.models.shared.asserts.line_text.LineDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.StartEndDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.StartEndDiffPair;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

public class LineTextComparisonReport {

	public static void display(I_Tests4J_Logger out, LineTextCompareResult result) {
		out.log("Expected;");
		out.log(result.getExample());
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
	}
	
	private static void print(I_Tests4J_Logger out, String content, StartEndDiff diff) {
		String inDiff = content.substring(diff.getStart(), diff.getEnd());
		out.log(inDiff);
	}
	
}