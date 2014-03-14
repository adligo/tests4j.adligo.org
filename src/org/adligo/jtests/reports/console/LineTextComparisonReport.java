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
				out.println("Line Number " + linenumber + " should be;");
				StartEndDiffPair pair = line.getStartEndDiffs();
				
				print(out, err, example, pair.getExample());
				out.println("but was;");
				print(out, err, actual, pair.getActual());
			}
		} 
	}
	
	private static void print(PrintStream out, PrintStream err, String content, StartEndDiff diff) {
		String beforeDiff = content.substring(0, diff.getStart());
		print(out, beforeDiff);
		String inDiff = content.substring(diff.getStart(), diff.getEnd());
		print(err, inDiff);
		String afterDiff = content.substring(diff.getEnd(), content.length());
		print(out, afterDiff);
	}
	
	private synchronized static void print(PrintStream out, String content) {
		out.print(content);
	}
}
