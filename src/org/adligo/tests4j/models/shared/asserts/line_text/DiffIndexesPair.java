package org.adligo.tests4j.models.shared.asserts.line_text;

import java.io.IOException;



/**
 * a immutable class with  a pair of start end diffs
 * that can be used to show where a line is different.
 * 
 * @author scott
 *
 */
public class DiffIndexesPair implements I_DiffIndexesPair {
	public static final String NOTHING_DIFFERENT_FOUND = "Nothing different found.";
	private I_DiffIndexes example;
	private I_DiffIndexes actual;
	
	/**
	 * threadsafe method to compare two lines of text
	 * assumes line feeds have been pulled out and that 
	 * these strings represent single lines.
	 * 
	 * @param expectedLine
	 * @param actualLine
	 * @return null when there is no overlap
	 * @throws IOException when the expectedLine and actualLine have no matching values
	 */
	public DiffIndexesPair(String expectedLine, String actualLine) throws IOException {
		char [] expectedChars = expectedLine.toCharArray();
		char [] actualChars = actualLine.toCharArray();
		
		int endLeftToRightMatch = -1;
		for (int i = 0; i < expectedChars.length; i++) {
			if (i >= 1) {
				if (endLeftToRightMatch == -1) {
					break;
				}
			}
			char e = expectedChars[i];
			if (endLeftToRightMatch < actualChars.length - 1) {
				if (endLeftToRightMatch == -1) {
					char a = actualChars[0];
					if (e != a) {
						break;
					} else {
						endLeftToRightMatch = 1;
					}
				} else {
					char a = actualChars[endLeftToRightMatch];
					if (e != a) {
						break;
					} else {
						endLeftToRightMatch++;
					}
				}
			}
		}
		
		char [] expectedCharsReversed = new char[expectedChars.length];
		int counter = 0;
		for (int i = expectedChars.length -1; i >= 0; i--) {
			expectedCharsReversed[counter++] = expectedChars[i];
		}
		char [] actualCharsReversed = new char[actualChars.length];
		counter = 0;
		for (int i = actualChars.length -1; i >= 0; i--) {
			actualCharsReversed[counter++] = actualChars[i];
		}
		
		int endMatch = -1;
		for (int i = 0; i < expectedCharsReversed.length; i++) {
			char e = expectedCharsReversed[i];
			if (endMatch < actualCharsReversed.length) {
				if (endMatch == -1) {
					char a = actualCharsReversed[0];
					if (e != a) {
						break;
					} else {
						endMatch = 1;
					}
				} else {
					char a = actualCharsReversed[endMatch];
					if (e != a) {
						break;
					} else {	
						endMatch++;
					}
				}
			}
		}
		if (endLeftToRightMatch >= 0) {
			int expectedStart = endLeftToRightMatch;
			int actualStart = endLeftToRightMatch;
			
			int expectedEnd = expectedChars.length - 1;
			int actualEnd = actualChars.length - 1;
			if (endMatch >= 0) {
				expectedEnd = expectedEnd - endMatch;
				actualEnd = actualEnd - endMatch;
			}
			
			if (expectedStart <= expectedEnd && actualStart <= actualEnd) {
				example = new DiffIndexes(expectedStart, expectedEnd);
				actual = new DiffIndexes(actualStart, actualEnd);
				return;
			}
		} else if (endMatch != -1) {
			int expectedStart = 0;
			int expectedEnd = expectedChars.length - 1 - endMatch;
			int actualStart = 0;
			int actualEnd = actualChars.length - 1 - endMatch;
			if (expectedStart <= expectedEnd && actualStart <= actualEnd) {
				example = new DiffIndexes(expectedStart, expectedEnd);
				actual = new DiffIndexes(actualStart, actualEnd);
				return;
			}
		}
		throw new IOException(NOTHING_DIFFERENT_FOUND);
	}
	
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
