package org.adligo.tests4j.models.shared.asserts.line_text;

/**
 * a immutable class with  a pair of start end diffs
 * that can be used to show where a line is different.
 * 
 * @author scott
 *
 */
public class DiffIndexesPair implements I_DiffIndexesPair {
	private I_DiffIndexes example;
	private I_DiffIndexes actual;
	
	public DiffIndexesPair(final I_DiffIndexes pExample, final I_DiffIndexes pActual) {
		example = pExample;
		actual = pActual;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiffPair#getExample()
	 */
	@Override
	public I_DiffIndexes getExample() {
		return example;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiffPair#getActual()
	 */
	@Override
	public I_DiffIndexes getActual() {
		return actual;
	}
}
