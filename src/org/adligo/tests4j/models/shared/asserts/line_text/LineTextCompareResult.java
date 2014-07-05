package org.adligo.tests4j.models.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineTextCompareResult implements I_LineTextCompareResult {
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
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#isMatched()
	 */
	@Override
	public boolean isMatched() {
		return matched;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getExampleLines()
	 */
	@Override
	public int getExpectedLines() {
		return exampleLines;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getActualLines()
	 */
	@Override
	public int getActualLines() {
		return actualLines;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getLineDiffs()
	 */
	@Override
	public List<LineDiff> getLineDiffs() {
		return  Collections.unmodifiableList(lineDiffs);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getExample()
	 */
	@Override
	public String getExpected() {
		return example;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getActual()
	 */
	@Override
	public String getActual() {
		return actual;
	}
	
}
