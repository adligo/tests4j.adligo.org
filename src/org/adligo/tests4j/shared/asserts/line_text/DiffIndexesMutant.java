package org.adligo.tests4j.shared.asserts.line_text;

public class DiffIndexesMutant implements I_DiffIndexes {
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
	
	public DiffIndexesMutant() {}
	
	public DiffIndexesMutant(final Integer pDiffLeftToRight, final Integer pDiffRightToLeft,
			final Integer pMatchLeftToRight, final Integer pMatchRightToLeft) {
		
		diffLeftToRight = pDiffLeftToRight;
		diffRightToLeft = pDiffRightToLeft;
		matchLeftToRight = pMatchLeftToRight;
		matchRightToLeft = pMatchRightToLeft;
		
	}
	/**
	 * makes sure that nothing is wrong with all of the indexes 
	 * by the end of the method.
	 * @param line
	 */
	public void rejustify(String line) {
		//fix overlap issues
		if (diffLeftToRight != null && diffRightToLeft != null) {
			if (matchLeftToRight != null && matchRightToLeft != null) {
				if (matchLeftToRight == 0) {
					if (diffLeftToRight == 0){
						matchRightToLeft = 0;
						diffLeftToRight = 1;
					} else if (matchRightToLeft == line.length() - 1) {
						if (diffRightToLeft == matchRightToLeft) {
							matchRightToLeft = diffLeftToRight -1;
						} else {
							//diff in the middle, so make sure its in the middle
							if (diffLeftToRight == 0) {
								diffLeftToRight =  1;
							}
							if (diffRightToLeft == matchRightToLeft) {
								diffRightToLeft = diffRightToLeft-1;
							}
						}
					} else {
						//diff at the right
						if (matchRightToLeft > diffLeftToRight) {
							matchRightToLeft = diffLeftToRight - 1;
						}
					}
				} else if (diffLeftToRight == 0) {
					
					if (diffRightToLeft == line.length() - 1) {
						//the end char could be off
						if (diffRightToLeft == matchRightToLeft) {
							matchRightToLeft--;
						}
					} else {
						matchRightToLeft = line.length() -1 ;
						matchLeftToRight = diffRightToLeft + 1;
					}
				}
				
				//rejustify so that left to right is always correct
				if (matchLeftToRight > matchRightToLeft) {
					matchRightToLeft = matchLeftToRight;
				}
			} 
			
			//the +1 here is for when abce is compared vs abcde or vice versa
			//since the left to right should actually be above the right to left 
			//to track the slot for a missing string
			if (diffLeftToRight > diffRightToLeft + 1) {
				diffRightToLeft = diffLeftToRight;
			}
		}
		
		if (matchLeftToRight != null && matchRightToLeft != null) { 
			//rejustify so that left to right is always correct
			if (matchLeftToRight > matchRightToLeft) {
				matchRightToLeft = matchLeftToRight;
			}
		}
		
		
	}

	public Integer getDiffLeftToRight() {
		return diffLeftToRight;
	}

	public Integer getDiffRightToLeft() {
		return diffRightToLeft;
	}

	public Integer getMatchLeftToRight() {
		return matchLeftToRight;
	}

	public Integer getMatchRightToLeft() {
		return matchRightToLeft;
	}

	@Override
	public String[] getDifferences(String line) {
		DiffIndexes temp = new DiffIndexes(diffLeftToRight, diffRightToLeft, matchLeftToRight, matchRightToLeft);
		return temp.getDifferences(line);
	}

	@Override
	public String[] getMatches(String line) {
		DiffIndexes temp = new DiffIndexes(diffLeftToRight, diffRightToLeft, matchLeftToRight, matchRightToLeft);
		return temp.getMatches(line);
	}

	@Override
	public boolean isEmpty() {
		DiffIndexes temp = new DiffIndexes(diffLeftToRight, diffRightToLeft, matchLeftToRight, matchRightToLeft);
		return temp.isEmpty();
	}

	public void setDiffLeftToRight(Integer diffLeftToRight) {
		this.diffLeftToRight = diffLeftToRight;
	}

	public void setDiffRightToLeft(Integer diffRightToLeft) {
		this.diffRightToLeft = diffRightToLeft;
	}

	public void setMatchLeftToRight(Integer matchLeftToRight) {
		this.matchLeftToRight = matchLeftToRight;
	}

	public void setMatchRightToLeft(Integer matchRightToLeft) {
		this.matchRightToLeft = matchRightToLeft;
	}
}