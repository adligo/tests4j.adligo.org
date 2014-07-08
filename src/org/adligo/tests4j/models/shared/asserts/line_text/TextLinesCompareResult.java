package org.adligo.tests4j.models.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * a immutable class to represent the 
 * result of a TextLinesCompare.
 * 
 * @author scott
 *
 */
public class TextLinesCompareResult implements I_TextLinesCompareResult {
	private I_TextLines example;
	private I_TextLines actual;
	private boolean matched;
	private List<I_LineDiff> lineDiffs = new ArrayList<I_LineDiff>();
	
	public TextLinesCompareResult(I_TextLines pExamle, I_TextLines pActualString,boolean pMatched, List<I_LineDiff> list) {
		example = pExamle;
		actual = pActualString;
		matched = pMatched;
		lineDiffs.addAll(list);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#isMatched()
	 */
	@Override
	public boolean isMatched() {
		return matched;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getLineDiffs()
	 */
	@Override
	public List<I_LineDiff> getLineDiffs() {
		return  Collections.unmodifiableList(lineDiffs);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getExpectedLines()
	 */
	@Override
	public I_TextLines getExpectedLines() {
		return example;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult#getActualLines()
	 */
	@Override
	public I_TextLines getActualLines() {
		return actual;
	}
	
}