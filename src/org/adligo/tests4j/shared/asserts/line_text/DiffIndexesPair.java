package org.adligo.tests4j.shared.asserts.line_text;

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
	private I_DiffIndexes expected_;
	private I_DiffIndexes actual_;
	
	private Integer expectedLeftToRightMatch_;
	private Integer expectedLeftToRightDiff_;
	private Integer actualLeftToRightMatch_;
	private Integer actualLeftToRightDiff_;
	
	private Integer expectedRightToLeftMatch_;
	private Integer expectedRightToLeftDiff_;
	private Integer actualRightToLeftMatch_;
	private Integer actualRightToLeftDiff_;

	private Integer expectedLen_;
	private Integer actualLen_;
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
	@SuppressWarnings("boxing")
  public DiffIndexesPair(String expectedLine, String actualLine) {
		if (expectedLine.equals(actualLine)) {
			throw new IllegalArgumentException("There is no difference expectedLine.equals(actualLine).");
		}
		actualLen_ = actualLine.length();
		if (actualLen_ == 0) {
			createExpectedAllDifferent(expectedLine);
			actual_ = new DiffIndexes();
			return;
		}
		expectedLen_ = expectedLine.length();
		if (expectedLen_ == 0) {
			createActualAllDifferent(actualLine);
			expected_ = new DiffIndexes();
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
					actual_ = new DiffIndexes(expectedLine.length(), actualLine.length() -1, 0, expectedLine.length() -1);
				} else if (actualLine.length() == expectedInActual + expectedLine.length()){
					//at right
					actual_ = new DiffIndexes(0, expectedInActual - 1, expectedInActual, actualLine.length() -1);
				} else {
					//in middle
				  actual_ = new DiffIndexes(0, expectedInActual + expectedLen_, expectedInActual, expectedInActual + expectedLen_ -1);
				}				
			} else {
	
				//actual line in expec
				createActualAllMatch(actualLine);
	
				//actual in expected exists,
				if (actualInExpectd == 0) {
					//at left
					expected_ = new DiffIndexes( actualLine.length() , expectedLine.length() - 1, 0, actualLine.length() - 1);
				} else if (expectedLine.length() == actualInExpectd + actualLine.length()){
					//at right
					expected_ = new DiffIndexes(0, actualInExpectd - 1, actualInExpectd, expectedLine.length() -1);
				} else {
					//in middle
					expected_ = new DiffIndexes(0, expectedLine.length() -1, actualInExpectd, actualInExpectd + actualLen_ -1);
				}
			}
		}
	}


	private void compareCharByChar(String expectedLine, String actualLine) {
		char [] expectedChars = expectedLine.toCharArray();
		char [] actualChars = actualLine.toCharArray();
		
		findLeftToRightDiffAndMatch(expectedChars, actualChars);
		findRightToLeftDiffAndMatch(expectedChars, actualChars);
		
		
		DiffIndexesMutant dim = new DiffIndexesMutant(expectedLeftToRightDiff_, expectedRightToLeftDiff_, 
				expectedLeftToRightMatch_, expectedRightToLeftMatch_);
		dim.rejustify(expectedLine);
		expectedLeftToRightDiff_ = dim.getDiffLeftToRight();
		expectedRightToLeftDiff_ = dim.getDiffRightToLeft();
		expectedLeftToRightMatch_ = dim.getMatchLeftToRight();
		expectedRightToLeftMatch_ =  dim.getMatchRightToLeft();
		
		dim = new DiffIndexesMutant(actualLeftToRightDiff_, actualRightToLeftDiff_, 
				actualLeftToRightMatch_, actualRightToLeftMatch_);
		dim.rejustify(actualLine);
		actualLeftToRightDiff_ = dim.getDiffLeftToRight();
		actualRightToLeftDiff_ = dim.getDiffRightToLeft();
		actualLeftToRightMatch_ = dim.getMatchLeftToRight();
		actualRightToLeftMatch_ =  dim.getMatchRightToLeft();
		
		fixInconsitentMatch(expectedLine, actualLine);
		
		expected_ = new DiffIndexes(expectedLeftToRightDiff_, expectedRightToLeftDiff_, 
				expectedLeftToRightMatch_, expectedRightToLeftMatch_);
		actual_ =  new DiffIndexes(actualLeftToRightDiff_, actualRightToLeftDiff_, 
				actualLeftToRightMatch_, actualRightToLeftMatch_);
	}


	


	@SuppressWarnings("boxing")
  private void fixInconsitentMatch(String expectedLine, String actualLine) {
		
		
		String expectedMiddleMatch = null;
		String actualMiddleMatch = null;
		// fix matches on the inside, that are false positives
		//which only have the end chars matching (not a sequence of chars)
		if (expectedLeftToRightDiff_ != null && expectedRightToLeftDiff_ != null) {
			if (expectedLeftToRightMatch_ != null && expectedRightToLeftMatch_ != null) {
				int expectedLineLength = expectedLine.length();
				if (expectedLeftToRightDiff_.intValue() == 0 && expectedRightToLeftDiff_.intValue() == expectedLineLength - 1) {
					//the match text is in the middle
					//the diffs bound the lines, so there may be internal matches
					expectedMiddleMatch = expectedLine.substring(expectedLeftToRightMatch_, expectedRightToLeftMatch_ + 1);
				} 
			} 
		}
		
		if (actualLeftToRightDiff_ != null && actualRightToLeftDiff_ != null) {
			if (actualLeftToRightMatch_ != null && actualRightToLeftMatch_ != null) {
				if (actualLeftToRightDiff_.intValue() == 0 && actualRightToLeftDiff_.intValue() == actualLine.length() - 1) {
				//the match text is in the middle
				//the diffs bound the lines, so there may be internal matches
					actualMiddleMatch = actualLine.substring(actualLeftToRightMatch_, actualRightToLeftMatch_ + 1);
				} 
			}
		}
		if (expectedMiddleMatch != null) {
			if (!expectedMiddleMatch.equals(actualMiddleMatch)) {
				//it is a false positive, move rightToLeftMatches to the leftToRightMatchs
				expectedRightToLeftMatch_ = expectedLeftToRightMatch_;
				actualRightToLeftMatch_ = actualLeftToRightMatch_;
				return;
			}
		}
		if (expectedMiddleMatch == null && expectedMiddleMatch == null) {
			if (expectedLeftToRightMatch_ != null && actualLeftToRightMatch_ != null) {
				if (expectedLeftToRightMatch_.intValue() == 0 && actualLeftToRightMatch_.intValue() == 0) {
					//the left side matched
					if (expectedRightToLeftDiff_ != null && actualRightToLeftDiff_ != null) {
						//the right to left had a diff,
						if (expectedLine.length() - 1 == expectedRightToLeftDiff_.intValue() && 
								actualLine.length() - 1 == actualRightToLeftDiff_.intValue()) {
								//its a left only diff
								expectedRightToLeftMatch_ = expectedLeftToRightDiff_ - 1;
								actualRightToLeftMatch_ = actualLeftToRightDiff_ - 1;
						}
					}
				}
			}
		}
	}


	@SuppressWarnings("boxing")
  private void findLeftToRightDiffAndMatch(char[] expectedChars,
			char[] actualChars) {
		
		char e = expectedChars[0];
		char a = actualChars[0];
		if (e == a) {
			
			expectedLeftToRightMatch_ = 0;
			actualLeftToRightMatch_ = 0;
			//find left to right diffs, as match chars must proceed
			if (actualChars.length <= expectedChars.length) {
				for (int i = 0; i < expectedChars.length; i++) {
					if (i > actualChars.length) {
						break;
					}
					e = expectedChars[i];
					a = actualChars[i];
					if (e != a) {
						expectedLeftToRightDiff_ = i;
						actualLeftToRightDiff_ = i;
						return;
					}
				}
				if (expectedLeftToRightDiff_ == -1) {
					expectedLeftToRightDiff_ = expectedChars.length;
					//yes actual = expected here, since the actual diff 
					//is where the expected chars end.
					actualLeftToRightDiff_ = expectedChars.length;
				}
			} else {
				for (int i = 0; i < actualChars.length; i++) {
					if (i > expectedChars.length) {
						break;
					}
					e = expectedChars[i];
					a = actualChars[i];
					if (e != a) {
						expectedLeftToRightDiff_ = i;
						actualLeftToRightDiff_ = i;
						return;
					}
				}
				if (expectedLeftToRightDiff_ == -1) {
					
					//yes expected = actual here, since the expected diff 
					//is where the actual chars end.
					expectedLeftToRightDiff_ = actualChars.length;
					actualLeftToRightDiff_ = actualChars.length;
				}
			}
			return;
		} else {
			//first char is different 
			expectedLeftToRightDiff_ = 0;
			actualLeftToRightDiff_ = 0;
			
			Integer firstMatchI = null;
			Integer firstMatchJ = null;
			for (int i = 0; i < expectedChars.length; i++) {
				e = expectedChars[i];
				if (expectedLeftToRightMatch_ == null) {
					for (int j = 0; j < actualChars.length; j++) {
						a = actualChars[j];
						if (e == a) {
							if (firstMatchI == null && firstMatchJ == null) {
								firstMatchI = i;
								firstMatchJ = j;
							}
							//i found a char match, but
							//i want a subString match 
							String ec = new String(expectedChars);
							String ac = new String(actualChars);
							
							String eRight = ec.substring(i, ec.length());
							String aRight = ac.substring(j, ac.length());
							if (eRight.equals(aRight)) {
								//its a simple right left match
								expectedLeftToRightMatch_ = i;
								actualLeftToRightMatch_ = j;
								break;
							} else if (eRight.length() > aRight.length()){
								int idx = eRight.indexOf(aRight);
								if (idx != -1) {
									//its a complex right side match
									expectedLeftToRightMatch_ = expectedChars.length  - aRight.length();
									actualLeftToRightMatch_ = actualChars.length - aRight.length();
									break;
								}
							} else {
								if (eRight.length() != 1) {
									int idx = aRight.indexOf(eRight);
									if (idx != -1) {
										//its a complex right side match
										expectedLeftToRightMatch_ = expectedChars.length  - eRight.length();
										actualLeftToRightMatch_ = actualChars.length - eRight.length();
										break;
									}
								}
							}
							
						}
					}
				}
			}
			if (actualLeftToRightMatch_ == null) {
				if (firstMatchI != null && firstMatchJ != null) {
					//its a complex middle match
					expectedLeftToRightMatch_ = firstMatchI;
					actualLeftToRightMatch_ = firstMatchJ;
				}
			}
		}
	}
	
	@SuppressWarnings("boxing")
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
			
			expectedRightToLeftMatch_ = expectedChars.length -1;
			actualRightToLeftMatch_ = actualChars.length -1;
			
			//find right to left diffs, as match chars must proceed to the right
			if (actualChars.length <= expectedChars.length) {
				for (int i = 0; i < expectedCharsReversed.length; i++) {
					if (i >= actualChars.length) {
						break;
					}
					e = expectedCharsReversed[i];
					a = actualCharsReversed[i];
					if (e != a) {
						expectedRightToLeftDiff_ = expectedChars.length - 1 - i;
						actualRightToLeftDiff_ = actualChars.length - 1 -i;
						return;
					}
				}
				if (expectedRightToLeftDiff_ == -1) {
					//the actual line is shorter, but matches from right to left
					expectedRightToLeftDiff_ = expectedChars.length - actualChars.length - 1;
					actualRightToLeftDiff_ = 0;
				}
			} else {
				for (int i = 1; i < actualCharsReversed.length; i++) {
					if (i >= expectedChars.length) {
						break;
					}
					e = expectedCharsReversed[i];
					a = actualCharsReversed[i];
					if (e != a) {
						expectedRightToLeftDiff_ = expectedChars.length-i -1;
						actualRightToLeftDiff_ = actualChars.length -i -1;
						return;
					}
				}
				if (expectedRightToLeftDiff_ == -1) {
					//the actual line is longer than the expected but they match
					expectedRightToLeftDiff_ = 0;
					actualRightToLeftDiff_ = actualChars.length - expectedChars.length - 1;
				}
			}
		} else {
			//the left most chars are different
			expectedRightToLeftDiff_ = expectedChars.length  -1;
			actualRightToLeftDiff_ = actualChars.length -1;
			for (int i = 0; i < expectedCharsReversed.length; i++) {
				e = expectedCharsReversed[i];
				if (expectedRightToLeftMatch_ == null) {
					for (int j = 0; j < actualCharsReversed.length; j++) {
						a = actualCharsReversed[j];
						if (e == a) {
							expectedRightToLeftMatch_ = expectedChars.length  -1 - i;
							actualRightToLeftMatch_ = actualChars.length -1 - j;
							break;
						}
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("boxing")
  private void createActualAllDifferent(String actualLine) {
		if (actualLine.length() == 0) {
			actual_ = new DiffIndexes();
		} else {
			actual_ = new DiffIndexes(0, actualLine.length(), null, null);
		}
	}

	@SuppressWarnings("boxing")
  private void createActualAllMatch(String actualLine) {
		actual_ = new DiffIndexes(null, null, 0, actualLine.length() -1);
	}
	
	@SuppressWarnings("boxing")
  private void createExpectedAllDifferent(String expectedLine) {
		if (expectedLine.length() == 0 ) {
			expected_ = new DiffIndexes();
		} else {
			expected_ = new DiffIndexes(0, expectedLine.length(), null, null);
		}
	}

	@SuppressWarnings("boxing")
  private void createExpectedAllMatch(String expectedLine) {
		expected_ = new DiffIndexes(null, null, 0, expectedLine.length() - 1);
	}
	
	public DiffIndexesPair(final I_DiffIndexes pExpected, final I_DiffIndexes pActual) {
		expected_ = pExpected;
		actual_ = pActual;
	}

	
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiffPair#getExample()
	 */
	@Override
	public I_DiffIndexes getExpected() {
		return expected_;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_StartEndDiffPair#getActual()
	 */
	@Override
	public I_DiffIndexes getActual() {
		return actual_;
	}
}
