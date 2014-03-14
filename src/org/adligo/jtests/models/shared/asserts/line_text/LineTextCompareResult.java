package org.adligo.jtests.models.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineTextCompareResult {
	private String example;
	private String actual;
	private boolean matched;
	private int exampleLines;
	private int actualLines;
	private List<LineDiff> lineDiffs = new ArrayList<LineDiff>();
	
	public LineTextCompareResult(String pExamle, String pActualString,boolean pMatched, int pExample, int pActual, List<LineDiff> pLinesDiff) {
		example = pExamle;
		actual = pActualString;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public String getExample() {
		return example;
	}

	public String getActual() {
		return actual;
	}
	
}
