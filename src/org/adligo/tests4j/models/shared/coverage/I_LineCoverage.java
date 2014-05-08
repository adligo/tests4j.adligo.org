package org.adligo.tests4j.models.shared.coverage;


/**
 * Note I am basing the original implementaion on EclEmma's
 * ICounter and ILine in it's 
 * org.jacoco.core package
 * which seem to be what gives us the coverage
 * 
 * @author scott
 *
 */
public interface I_LineCoverage {
	
	/**
	 * true for empty or fully covered lines
	 * null for partially covered lines
	 * false for lines with no coverage
	 * 
	 * also note this should always return null 
	 * when there are line segments.
	 * @return
	 */
	public Boolean getCovered();
	
	/**
	 * A coverage unit is either a instruction
	 *  or a branch in jacoco's lingo.
	 *  
	 * @return
	 */
	public short getCoverageUnits();
	/**
	 * A coverage unit is either a instruction
	 *  or a branch in jacoco's lingo.
	 *  
	 *  
	 * @return the number of coverage units actually covered.
	 */
	public short getCoveredCoverageUnits();
	/**
	 * the number of branches on this line
	 * @return
	 */
	public short getBranches();
	/**
	 * the number of branches covered on this line
	 * @return
	 */
	public short getCoveredBranches();
	
	/**
	 * if false this line coverage pertains to the whole line
	 * @return
	 */
	public boolean hasSegments();

	/**
	 * the number of line segments
	 * @return
	 */
	public short getLineCoverageSegmentCount();
	/**
	 * the line segment
	 * 
	 * @param p
	 * @return
	 */
	public I_LineCoverageSegment getLineCoverageSegment(short p);
	

}
