package org.adligo.jtests.reports.console;

import java.io.PrintStream;
import java.util.List;

import org.adligo.jtests.models.shared.asserts.line_text.LineDiff;
import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.jtests.models.shared.asserts.line_text.StartEndDiff;
import org.adligo.jtests.models.shared.asserts.line_text.StartEndDiffPair;

public class LineTextComparisonReport {

	public static void display(PrintStream out, PrintStream err, LineTextCompareResult result) {
		out.println("Expected;");
		out.println(result.getExample());
		out.println("Actual;");
		out.println(result.getActual());
		
		List<LineDiff> lineDiffs = result.getLineDiffs();
		if (lineDiffs.size() >= 1) {
			for (LineDiff line: lineDiffs) {
				String example = line.getExample();
				String actual = line.getActual();
				int linenumber = line.getLineNumber();
				out.println("Line Number " + linenumber + " have be;");
				StartEndDiffPair pair = line.getStartEndDiffs();
				
				print(out, example, pair.getExample());
				out.println("but had;");
				print(out, actual, pair.getActual());
			}
		} 
	}
	
	private static void print(PrintStream out, String content, StartEndDiff diff) {
		String inDiff = content.substring(diff.getStart(), diff.getEnd());
		out.println(inDiff);
	}
	
}
