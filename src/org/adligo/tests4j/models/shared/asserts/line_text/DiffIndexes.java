package org.adligo.tests4j.models.shared.asserts.line_text;

/**
 * a immutable class to represent 
 * where something was different
 * @author scott
 *
 */
public class DiffIndexes implements I_DiffIndexes {
	public static final String START_END_DIFF_REQUIRES_THE_START_TO_BE_BEFORE_THE_END = "StartEndDiff requires the start to be before the end.";
	public static final String START_END_DIFF_REQUIRES_POSITIVE_INDEXES = "StartEndDiff requires positive indexes.";
	/**
	 * the starting difference index
	 */
	private int start;
	/**
	 * the ending difference index
	 */
	private int end;

	public DiffIndexes(final int pStart, final int pEnd) {
		if (pStart < 0) {
			throw new IllegalArgumentException(START_END_DIFF_REQUIRES_POSITIVE_INDEXES);
		}
		start = pStart;
		if (pEnd < 0) {
			throw new IllegalArgumentException(START_END_DIFF_REQUIRES_POSITIVE_INDEXES);
		}
		end = pEnd;
		
		if (pEnd <= pStart) {
			throw new IllegalArgumentException(START_END_DIFF_REQUIRES_THE_START_TO_BE_BEFORE_THE_END);
		}
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiff#getStart()
	 */
	@Override
	public int getStart() {
		return start;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiff#getEnd()
	 */
	@Override
	public int getEnd() {
		return end;
	}
}
