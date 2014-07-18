package org.adligo.tests4j.models.shared.asserts.line_text;

import java.io.IOException;

import org.adligo.tests4j.models.shared.common.StringMethods;



/**
 * a immutable class with  a pair of start end diffs
 * that can be used to show where a line is different.
 * 
 * @author scott
 *
 */
public class DiffIndexesPair implements I_DiffIndexesPair {
	public static final String NOTHING_DIFFERENT_FOUND = "Nothing different found.";
	private I_DiffIndexes expected;
	private I_DiffIndexes actual;
	
	private Integer expectedLeftToRightMatch;
	private Integer expectedLeftToRightDiff;
	private Integer actualLeftToRightMatch;
	private Integer actualLeftToRightDiff;
	
	private Integer expectedRightToLeftMatch;
	private Integer expectedRightToLeftDiff;
	private Integer actualRightToLeftMatch;
	private Integer actualRightToLeftDiff;

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
	public DiffIndexesPair(String expectedLine, String actualLine) {
		if (expectedLine.equals(actualLine)) {
			throw new IllegalArgumentException("There is no difference expectedLine.equals(actualLine).");
		}
		int actualLength = actualLine.length();
		if (actualLength == 0) {
			createExpectedAllDifferent(expectedLine);
			actual = new DiffIndexes();
			return;
		}
		int expectedLength = expectedLine.length();
		if (expectedLength == 0) {
			createActualAllDifferent(actualLine);
			expected = new DiffIndexes();
			return;
		}
		if (expectedLine.length() == actualLine.length()) {
			compareCharByChar(expectedLine, actualLine);
			return;
		}
		
		
		int actualInExpectd = expectedLine.indexOf(actualLine);
		int expectedInActual = actualLine.indexOf(expectedLine);
		
		if (actualInExpectd == -1 && expectedInActual == -1) {
			compareCharByChar(expectedLine, actualLine);
			return;
		} else {
			if (actualInExpectd == -1) {
				//expected in actual, but actual not in expected
				createExpectedAllMatch(expectedLine);
				//expected in actual exists
				if (expectedInActual == 0) {
					//at left
					actual = new DiffIndexes(expectedLine.length() + 1, actualLine.length(), 0, expectedLine.length());
				} else if (actualLine.length() == expectedInActual + expectedLine.length()){
					//at right
					actual = new DiffIndexes(0, expectedInActual - 1, expectedInActual, actualLine.length() -1);
				} else {
					//in middle
					actual = new DiffIndexes(0, expectedLine.length(), expectedInActual, expectedInActual + actualLine.length());
				}				
			} else {
	
				//actual line in expec
				createActualAllMatch(actualLine);
	
				//actual in expected exists,
				if (actualInExpectd == 0) {
					//at left
					expected = new DiffIndexes(0, actualLine.length(), actualLine.length() + 1, expectedLine.length());
				} else if (expectedLine.length() == actualInExpectd + actualLine.length()){
					//at right
					expected = new DiffIndexes(0, actualInExpectd - 1, actualInExpectd, expectedLine.length() -1);
				} else {
					//in middle
					expected = new DiffIndexes(0, actualLine.length(), actualInExpectd, expectedLine.length());
				}
			}
		}
	}


	private void compareCharByChar(String expectedLine, String actualLine) {
		char [] expectedChars = expectedLine.toCharArray();
		char [] actualChars = actualLine.toCharArray();
		
		findLeftToRightDiffAndMatch(expectedChars, actualChars);
		findRightToLeftDiffAndMatch(expectedChars, actualChars);
		
		
		DiffIndexesMutant dim = new DiffIndexesMutant(expectedLeftToRightDiff, expectedRightToLeftDiff, 
				expectedLeftToRightMatch, expectedRightToLeftMatch);
		dim.rejustify(expectedLine);
		expectedLeftToRightDiff = dim.getDiffLeftToRight();
		expectedRightToLeftDiff = dim.getDiffRightToLeft();
		expectedLeftToRightMatch = dim.getMatchLeftToRight();
		expectedRightToLeftMatch =  dim.getMatchRightToLeft();
		
		dim = new DiffIndexesMutant(actualLeftToRightDiff, actualRightToLeftDiff, 
				actualLeftToRightMatch, actualRightToLeftMatch);
		dim.rejustify(actualLine);
		actualLeftToRightDiff = dim.getDiffLeftToRight();
		actualRightToLeftDiff = dim.getDiffRightToLeft();
		actualLeftToRightMatch = dim.getMatchLeftToRight();
		actualRightToLeftMatch =  dim.getMatchRightToLeft();
		
		fixInconsitentMatch(expectedLine, actualLine);
		
		expected = new DiffIndexes(expectedLeftToRightDiff, expectedRightToLeftDiff, 
				expectedLeftToRightMatch, expectedRightToLeftMatch);
		actual =  new DiffIndexes(actualLeftToRightDiff, actualRightToLeftDiff, 
				actualLeftToRightMatch, actualRightToLeftMatch);
	}


	


	private void fixInconsitentMatch(String expectedLine, String actualLine) {
		String expectedMiddleMatch = null;
		String actualMiddleMatch = null;
		// fix matches on the inside, that are false positives
		//which only have the end chars matching (not a sequence of chars)
		if (expectedLeftToRightDiff != null && expectedRightToLeftDiff != null) {
			if (expectedLeftToRightMatch != null && expectedRightToLeftMatch != null) {
				int expectedLineLength = expectedLine.length();
				if (expectedLeftToRightDiff.intValue() == 0 && expectedRightToLeftDiff.intValue() == expectedLine.length() - 1) {
					//the match text is in the middle
					//the diffs bound the lines, so there may be internal matches
					expectedMiddleMatch = expectedLine.substring(expectedLeftToRightMatch, expectedRightToLeftMatch + 1);
				} 
			} 
		}
		
		if (actualLeftToRightDiff != null && actualRightToLeftDiff != null) {
			if (actualLeftToRightMatch != null && actualRightToLeftMatch != null) {
				if (actualLeftToRightDiff.intValue() == 0 && actualRightToLeftDiff.intValue() == actualLine.length() - 1) {
				//the match text is in the middle
				//the diffs bound the lines, so there may be internal matches
					actualMiddleMatch = actualLine.substring(actualLeftToRightMatch, actualRightToLeftMatch + 1);
				} 
			}
		}
		if (expectedMiddleMatch != null) {
			if (!expectedMiddleMatch.equals(actualMiddleMatch)) {
				//it is a false positive, move rightToLeftMatches to the leftToRightMatchs
				expectedRightToLeftMatch = expectedLeftToRightMatch;
				actualRightToLeftMatch = actualLeftToRightMatch;
			}
		}
	}


	private void findLeftToRightDiffAndMatch(char[] expectedChars,
			char[] actualChars) {
		
		char e = expectedChars[0];
		char a = actualChars[0];
		if (e == a) {
			
			expectedLeftToRightMatch = 0;
			actualLeftToRightMatch = 0;
			//find left to right diffs, as match chars must proceed
			if (actualChars.length <= expectedChars.length) {
				for (int i = 0; i < expectedChars.length; i++) {
					if (i > actualChars.length) {
						break;
					}
					e = expectedChars[i];
					a = actualChars[i];
					if (e != a) {
						expectedLeftToRightDiff = i;
						actualLeftToRightDiff = i;
						return;
					}
				}
				if (expectedLeftToRightDiff == -1) {
					expectedLeftToRightDiff = expectedChars.length;
					//yes actual = expected here, since the actual diff 
					//is where the expected chars end.
					actualLeftToRightDiff = expectedChars.length;
				}
			} else {
				for (int i = 0; i < actualChars.length; i++) {
					if (i > expectedChars.length) {
						break;
					}
					e = expectedChars[i];
					a = actualChars[i];
					if (e != a) {
						expectedLeftToRightDiff = i;
						actualLeftToRightDiff = i;
						return;
					}
				}
				if (expectedLeftToRightDiff == -1) {
					
					//yes expected = actual here, since the expected diff 
					//is where the actual chars end.
					expectedLeftToRightDiff = actualChars.length;
					actualLeftToRightDiff = actualChars.length;
				}
			}
			return;
		} else {
			//first char is different 
			expectedLeftToRightDiff = 0;
			actualLeftToRightDiff = 0;
			for (int i = 0; i < expectedChars.length; i++) {
				e = expectedChars[i];
				if (expectedLeftToRightMatch == null) {
					for (int j = 0; j < actualChars.length; j++) {
						a = actualChars[j];
						if (e == a) {
							expectedLeftToRightMatch = i;
							actualLeftToRightMatch = j;
							break;
						}
					}
				}
			}
			
		}
	}
	
	private void findRightToLeftDiffAndMatch(char[] expectedChars,
			char[] actualChars) {
		
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
		
		
		char e = expectedCharsReversed[0];
		char a = actualCharsReversed[0];
		if (e == a) {
			
			expectedRightToLeftMatch = expectedChars.length -1;
			actualRightToLeftMatch = actualChars.length -1;
			
			//find right to left diffs, as match chars must proceed to the right
			if (actualChars.length <= expectedChars.length) {
				for (int i = 0; i < expectedCharsReversed.length; i++) {
					if (i >= actualChars.length) {
						break;
					}
					e = expectedCharsReversed[i];
					a = actualCharsReversed[i];
					if (e != a) {
						expectedRightToLeftDiff = expectedChars.length - 1 - i;
						actualRightToLeftDiff = actualChars.length - 1 -i;
						return;
					}
				}
				if (expectedRightToLeftDiff == -1) {
					//the actual line is shorter, but matches from right to left
					expectedRightToLeftDiff = expectedChars.length - actualChars.length - 1;
					actualRightToLeftDiff = 0;
				}
			} else {
				for (int i = 1; i < actualCharsReversed.length; i++) {
					if (i >= expectedChars.length) {
						break;
					}
					e = expectedCharsReversed[i];
					a = actualCharsReversed[i];
					if (e != a) {
						expectedRightToLeftDiff = expectedChars.length-i -1;
						actualRightToLeftDiff = actualChars.length -i -1;
						return;
					}
				}
				if (expectedRightToLeftDiff == -1) {
					//the actual line is longer than the expected but they match
					expectedRightToLeftDiff = 0;
					actualRightToLeftDiff = actualChars.length - expectedChars.length - 1;
				}
			}
		} else {
			//the left most chars are different
			expectedRightToLeftDiff = expectedChars.length  -1;
			actualRightToLeftDiff = actualChars.length -1;
			for (int i = 0; i < expectedCharsReversed.length; i++) {
				e = expectedCharsReversed[i];
				if (expectedRightToLeftMatch == null) {
					for (int j = 0; j < actualCharsReversed.length; j++) {
						a = actualCharsReversed[j];
						if (e == a) {
							expectedRightToLeftMatch = expectedChars.length  -1 - i;
							actualRightToLeftMatch = actualChars.length -1 - j;
							break;
						}
					}
				}
			}
		}
	}
	
	
	private void createActualAllDifferent(String actualLine) {
		if (actualLine.length() == 0) {
			actual = new DiffIndexes();
		} else {
			actual = new DiffIndexes(0, actualLine.length(), null, null);
		}
	}

	private void createActualAllMatch(String actualLine) {
		actual = new DiffIndexes(null, null, 0, actualLine.length() -1);
	}
	
	private void createExpectedAllDifferent(String expectedLine) {
		if (expectedLine.length() == 0 ) {
			expected = new DiffIndexes();
		} else {
			expected = new DiffIndexes(0, expectedLine.length(), null, null);
		}
	}

	private void createExpectedAllMatch(String expectedLine) {
		expected = new DiffIndexes(null, null, 0, expectedLine.length() - 1);
	}
	
	public DiffIndexesPair(final I_DiffIndexes pExpected, final I_DiffIndexes pActual) {
		expected = pExpected;
		actual = pActual;
	}

	
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiffPair#getExample()
	 */
	@Override
	public I_DiffIndexes getExpected() {
		return expected;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiffPair#getActual()
	 */
	@Override
	public I_DiffIndexes getActual() {
		return actual;
	}
}
