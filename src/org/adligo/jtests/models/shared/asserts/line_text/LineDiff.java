package org.adligo.jtests.models.shared.asserts.line_text;


public class LineDiff {
	private String example;
	private String actual;
	private int lineNumber;
	private StartEndDiffPair startEndDiffs;
	
	public LineDiff(String pExample, String pActual, int pLineNumber, StartEndDiffPair startEnds) {
		lineNumber = pLineNumber;
		startEndDiffs = startEnds;
		example = pExample;
		actual = pActual;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public StartEndDiffPair getStartEndDiffs() {
		return startEndDiffs;
	}

	public String getExample() {
		return example;
	}

	public String getActual() {
		return actual;
	}
}
