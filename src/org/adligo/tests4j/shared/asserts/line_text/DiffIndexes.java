package org.adligo.tests4j.shared.asserts.line_text;

import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.StringMethods;

/**
 * a immutable class to represent 
 * where something was different.
 * This class can only take one of the following forms
 * 
 * difference match
 * match difference
 * match difference match
 * difference match difference
 * 
 * @author scott
 *
 */
public class DiffIndexes implements I_DiffIndexes {
	public static final String EITHER_THE_MATCH_OF_DIFF_MUST_START_AT_THE_LEFT = "Either the Match of Diff must start at the left.";
	public static final String FOR_LEFT_DIFFS_THE_DIFF_RIGHT_TO_LEFT_MUST_BE_IMMEDIATELY_FOLLOWED_BY_THE_RIGHT_TO_LEFT_MATCH = "For left diffs, the diff right to left must be immediately followed by the right to left match.";
	public static final String FOR_LEFT_MATCHES_THE_MATCH_RIGHT_TO_LEFT_MUST_BE_IMMEDIATELY_FOLLOWED_BY_THE_LEFT_TO_RIGHT_DIFF = "For left matches, the match right to left must be immediately followed by the left to right diff.";
	public static final String MATCH_ERROR_PART_ONE = "The Matches and diffs may not overlap as follows;";
	public static final String MATCH_L2R_DIFF_L2R_MATCH_R2L_DIFF_R2L = 
			" matchLeftToRight, diffLeftToRight, matchRightToLeft, diffRightToLeft.";
	public static final String DIFF_L2R_MATCH_L2R_DIFF_R2L_MATCH_R2L = 
			" diffLeftToRight, matchLeftToRight, diffRightToLeft, matchRightToLeft.";
	
	
	public static final String WHEN_THERE_ARE_NO_MATCHES_THE_DIFFS_MUST_BOUND_THE_STRING = "When there are no matches, the diffs must bound the String.";
	public static final String MATCH_LEFT_TO_RIGHT_NEEDS_CORRESPONDING_RIGHT_TO_LEFT_MATCH = "Match left to right, needs corresponding right to left match.";
	public static final String DIFF_LEFT_TO_RIGHT_NEEDS_CORRESPONDING_RIGHT_TO_LEFT_DIFF = "Diff left to right, needs corresponding right to left diff.";
	public static final String START_END_DIFF_REQUIRES_POSITIVE_INDEXES = "DiffIndexes requires positive indexes.";
	public static final String [] EMPTY_STRING_ARRAY = new String [] {};
	private static final I_System SYS = new DefaultSystem();
	/**
	 * @see I_DiffIndexes#getDiffLeftToRight()
	 */
	private Integer diffLeftToRight;
	/**
	 * @see I_DiffIndexes#getDiffRightToLeft()
	 */
	private Integer diffRightToLeft;
	/**
	 * @see I_DiffIndexes#getMatchLeftToRight()
	 */
	private Integer matchLeftToRight;
	/**
	 * @see I_DiffIndexes#getMatchRightToLeft()
	 */
	private Integer matchRightToLeft;
	
	//yea all indexes are null
	public DiffIndexes() {}
	
	@SuppressWarnings("boxing")
  public DiffIndexes(final Integer pDiffLeftToRight, final Integer pDiffRightToLeft,
			final Integer pMatchLeftToRight, final Integer pMatchRightToLeft) {
		if (pDiffLeftToRight != null) {
			if (pDiffLeftToRight < 0) {
				throw new IllegalArgumentException(START_END_DIFF_REQUIRES_POSITIVE_INDEXES);
			}
		}
		diffLeftToRight = pDiffLeftToRight;
		if (pDiffRightToLeft != null) {
			if (pDiffRightToLeft < 0) {
				throw new IllegalArgumentException(START_END_DIFF_REQUIRES_POSITIVE_INDEXES);
			}
		}
		diffRightToLeft = pDiffRightToLeft;
		if (pMatchLeftToRight != null) {
			if (pMatchLeftToRight < 0) {
				throw new IllegalArgumentException(START_END_DIFF_REQUIRES_POSITIVE_INDEXES);
			}
		}
		matchLeftToRight = pMatchLeftToRight;
		if (pMatchRightToLeft != null) {
			if (pMatchRightToLeft < 0) {
				throw new IllegalArgumentException(START_END_DIFF_REQUIRES_POSITIVE_INDEXES);
			}
		}
		matchRightToLeft = pMatchRightToLeft;
		if ( diffLeftToRight != null) {
			if (diffRightToLeft == null) {
				throw new IllegalArgumentException(DIFF_LEFT_TO_RIGHT_NEEDS_CORRESPONDING_RIGHT_TO_LEFT_DIFF);
			}
		}
		if ( matchLeftToRight != null) {
			if (matchRightToLeft == null) {
				throw new IllegalArgumentException(MATCH_LEFT_TO_RIGHT_NEEDS_CORRESPONDING_RIGHT_TO_LEFT_MATCH);
			}
		}
		if (!isEmpty()) {
			if (matchLeftToRight != null) {
				if (matchLeftToRight == 0) {
					//the match start at the left
					if (diffLeftToRight == null) {
						//matches with out diffs
					} else {
						if (matchRightToLeft < diffLeftToRight) {
							// the diff is only at the right, 
							if (matchRightToLeft + 1 != diffLeftToRight) {
								throw new IllegalArgumentException(FOR_LEFT_MATCHES_THE_MATCH_RIGHT_TO_LEFT_MUST_BE_IMMEDIATELY_FOLLOWED_BY_THE_LEFT_TO_RIGHT_DIFF);
							}
						} else if (matchRightToLeft < diffRightToLeft){
							throw new IllegalArgumentException(MATCH_ERROR_PART_ONE + 
							    SYS.lineSeperator() +
									MATCH_L2R_DIFF_L2R_MATCH_R2L_DIFF_R2L);
						}
					}
				} else {
					if (diffLeftToRight == 0) {
						//diff start at the left, and theres a match
						if (diffRightToLeft < matchLeftToRight) {
							if (diffRightToLeft + 1 != matchLeftToRight) {
								throw new IllegalArgumentException(FOR_LEFT_DIFFS_THE_DIFF_RIGHT_TO_LEFT_MUST_BE_IMMEDIATELY_FOLLOWED_BY_THE_RIGHT_TO_LEFT_MATCH);
							}
						} else if (diffRightToLeft < matchRightToLeft) {
							throw new IllegalArgumentException(MATCH_ERROR_PART_ONE +
							    SYS.lineSeperator() +
									DIFF_L2R_MATCH_L2R_DIFF_R2L_MATCH_R2L);
						}
					} else {
						//the match start at the right
						if (diffLeftToRight != null) {
							if (diffLeftToRight != 0) {
								throw new IllegalArgumentException(EITHER_THE_MATCH_OF_DIFF_MUST_START_AT_THE_LEFT);
							}
						}
					}
				}
			} else {
				//matchs are null, but there are diffs, then they must go left to right
				if (diffLeftToRight > diffRightToLeft) {
					throw new IllegalArgumentException(WHEN_THERE_ARE_NO_MATCHES_THE_DIFFS_MUST_BOUND_THE_STRING);
				}
			}
			
		}
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes#getDiffLeftToRight()
	 */
	@Override
	public Integer getDiffLeftToRight() {
		return diffLeftToRight;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes#getDiffRightToLeft()
	 */
	@Override
	public Integer getDiffRightToLeft() {
		return diffRightToLeft;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes#getMatchLeftToRight()
	 */
	@Override
	public Integer getMatchLeftToRight() {
		return matchLeftToRight;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes#getMatchRightToLeft()
	 */
	@Override
	public Integer getMatchRightToLeft() {
		return matchRightToLeft;
	}
	
	@SuppressWarnings("boxing")
  @Override
	public String[] getDifferences(String line) {
		if (diffLeftToRight == null || StringMethods.isEmpty(line)) {
			return EMPTY_STRING_ARRAY;
		}
		if (matchLeftToRight == null) {
			//no match so 
			return new String[] {line.substring(diffLeftToRight, diffRightToLeft + 1)};
		}
		if (diffLeftToRight == 0 && diffRightToLeft == line.length() - 1) {
			//left and right ends
			return new String[] {line.substring(0, matchLeftToRight), line.substring(matchRightToLeft + 1, line.length())};
		} else {
			//middle chars are different, so single match either left side only, right side only or middle
			return new String[] {line.substring(diffLeftToRight, diffRightToLeft + 1)};
		}
	}
	
	@SuppressWarnings("boxing")
  @Override
	public String[] getMatches(String line) {
		if (matchLeftToRight == null  || StringMethods.isEmpty(line)) {
			return EMPTY_STRING_ARRAY;
		}
		if (diffLeftToRight == null) {
			return new String[] {line.substring(matchLeftToRight, matchRightToLeft + 1)};
		}
		//theres a diff
		if (matchLeftToRight == 0 && matchRightToLeft == line.length() - 1) {
			//the left and right ends matched
			return new String[] {line.substring(0, diffLeftToRight), line.substring(diffRightToLeft + 1, line.length())};
		} else {
			//right only, left only,  or middle matched
			return new String[] {line.substring(matchLeftToRight, matchRightToLeft + 1)};
		} 
	}

	@Override
	public String toString() {
		return "DiffIndexes [diffLeftToRight=" + diffLeftToRight
				+ ", diffRightToLeft=" + diffRightToLeft
				+ ", matchLeftToRight=" + matchLeftToRight
				+ ", matchRightToLeft=" + matchRightToLeft + "]";
	}

	@Override
	public boolean isEmpty() {
		if (diffLeftToRight == null && diffRightToLeft == null && matchLeftToRight == null && matchRightToLeft == null) {
			return true;
		}
		return false;
	}
}
