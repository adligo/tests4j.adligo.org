package org.adligo.tests4j.models.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LineTextCompare  {
	
	public static LineTextCompareResult compare(String example, String actual) {
		String exStripped  = replaceAllLineSeperatorsWithUnix(example);
		String exActual  = replaceAllLineSeperatorsWithUnix(actual);
		
		LineText exampleLT = new LineText(exStripped);
		LineText actualLT = new LineText(exActual);
		
		List<LineDiff> toRet = new ArrayList<LineDiff>();
		for (int i = 0; i < exampleLT.getLines(); i++) {
			String exampleLine = exampleLT.getLine(i);
			String actualLine = actualLT.getLine(i);
			
			char [] exampleChars = exampleLine.toCharArray();
			char [] actualChars = actualLine.toCharArray();
			
			Integer startDiff = null;
			for (int j = 0; j < exampleChars.length; j++) {
				char c = exampleChars[j];
				char a = actualChars[j];
				if (c != a) {
					startDiff = j;
					break;
				} 
			}
			
			if (startDiff != null) {
				String exampleRev = reverse(exampleLine);
				String actualRev = reverse(actualLine);
				
				exampleChars = exampleRev.toCharArray();
				actualChars = actualRev.toCharArray();
				Integer fromEndDiff = null;
				for (int j = 0; j < exampleChars.length; j++) {
					char c = exampleChars[j];
					char a = actualChars[j];
					if (c != a) {
						fromEndDiff = j;
						break;
					} 
				}
				StartEndDiff exampleDiff = new StartEndDiff(startDiff, exampleLine.length() - fromEndDiff);
				StartEndDiff actualDiff = new StartEndDiff(startDiff, actualLine.length() - fromEndDiff);
				toRet.add(new LineDiff(exampleLine, actualLine,
						i, new StartEndDiffPair(exampleDiff, actualDiff)));
			}
		}
		boolean passed = true;
		if (toRet.size() >= 1) {
			passed = false;
		}
		if (exampleLT.getLines() != actualLT.getLines()) {
			passed = false;
		}
		return new LineTextCompareResult(example, actual, passed, 
				exampleLT.getLines(), actualLT.getLines(), toRet);
	}

	private static String replaceAllLineSeperatorsWithUnix(String p) {
		char[] chars = p.toCharArray();
		
		StringBuilder sb = new StringBuilder();
		boolean lastCharWasLineSeperator = false;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '\r') {
				if (!lastCharWasLineSeperator) {
					sb.append(c);
					lastCharWasLineSeperator = true;
				}
			} else if (c == '\n') {
				if (!lastCharWasLineSeperator) {
					sb.append(c);
					lastCharWasLineSeperator = true;
				}
			} else {
				sb.append(c);
				lastCharWasLineSeperator = false;
			}
		}
		return sb.toString();
	}
	
	private static String reverse(String p) {
		char[] chars = p.toCharArray();
		
		StringBuilder sb = new StringBuilder();
		for (int i = chars.length - 1; i >= 0; i--) {
			char c = chars[i];
			sb.append(c);
		}
		return sb.toString();
	}
}
