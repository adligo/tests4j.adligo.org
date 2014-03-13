package org.adligo.jtests.base.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineTextCompairResult {
	private boolean matched;
	private int exampleLines;
	private int actualLines;
	private List<LineDiff> lineDiffs = new ArrayList<LineDiff>();
	
	public LineTextCompairResult(boolean pMatched, int pExample, int pActual, List<LineDiff> pLinesDiff) {
		matched = pMatched;
		exampleLines = pExample;
		actualLines = pActual;
		lineDiffs.addAll(pLinesDiff);
	}
	
	public boolean isMatched() {
		return matched;
	}
	public int getExampleLines() {
		return exampleLines;
	}
	public int getActualLines() {
		return actualLines;
	}
	public List<LineDiff> getLineDiffs() {
		return  Collections.unmodifiableList(lineDiffs);
	}
	
}
