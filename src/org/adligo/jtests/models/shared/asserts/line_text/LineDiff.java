package org.adligo.jtests.models.shared.asserts.line_text;


public class LineDiff {
	private int lineNumber;
	private StartEndDiffPair startEndDiffs;
	
	public LineDiff(int pLineNumber, StartEndDiffPair startEnds) {
		lineNumber = pLineNumber;
		startEndDiffs = startEnds;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public StartEndDiffPair getStartEndDiffs() {
		return startEndDiffs;
	}
}
