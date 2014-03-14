package org.adligo.jtests.models.shared.asserts.line_text;

public class StartEndDiff {
	private int start;
	private int end;

	public StartEndDiff(int pStart, int pEnd) {
		start = pStart;
		end = pEnd;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
}
