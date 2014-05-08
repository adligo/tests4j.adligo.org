package org.adligo.tests4j.models.shared.coverage;

/**
 * 
 * @author scott
 *
 */
public class LineCoverageMutant implements I_LineCoverage {
	/**
	 * @see I_LineCoverage#getCovered()
	 */
	private Boolean covered;
	/**
	 * @see I_LineCoverage#getCoverageUnits()
	 */
	private short cus;
	/**
	 * @see I_LineCoverage#getCoveredCoverageUnits()
	 */
	private short covered_cus;
	/**
	 * @see I_LineCoverage#getBranches()
	 */
	private short branches;
	/**
	 * @see I_LineCoverage#getCoveredBranches()
	 */
	private short covered_branches;
	/**
	 * @see I_LineCoverage#hasSegments()
	 */
	private boolean hasSegments = false;
	
	public LineCoverageMutant() {}

	public LineCoverageMutant(I_LineCoverage lc) {
		covered = lc.getCovered();
		cus = lc.getCoverageUnits();
		covered_cus = lc.getCoveredCoverageUnits();
		branches = lc.getBranches();
		covered_branches = lc.getCoveredBranches();
		hasSegments = lc.hasSegments();
	}
	/**
	 * @see I_LineCoverage#getCovered()
	 */
	@Override
	public Boolean getCovered() {
		return covered;
	}

	/**
	 * @see I_LineCoverage#getCoverageUnits()
	 */
	@Override
	public short getCoverageUnits() {
		return cus;
	}

	/**
	 * @see I_LineCoverage#getCoveredCoverageUnits()
	 */
	@Override
	public short getCoveredCoverageUnits() {
		return covered_cus;
	}

	/**
	 * @see I_LineCoverage#getBranches()
	 */
	@Override
	public short getBranches() {
		return branches;
	}

	/**
	 * @see I_LineCoverage#getCoveredBranches()
	 */
	@Override
	public short getCoveredBranches() {
		return covered_branches;
	}

	/**
	 * @see I_LineCoverage#hasSegments()
	 */
	@Override
	public boolean hasSegments() {
		return hasSegments;
	}

	/**
	 * @see I_LineCoverage#getLineCoverageSegmentCount()
	 */
	@Override
	public short getLineCoverageSegmentCount() {
		return 0;
	}

	/**
	 * @see I_LineCoverage#getLineCoverageSegment(int)
	 */
	@Override
	public I_LineCoverageSegment getLineCoverageSegment(int p) {
		throw new IllegalStateException(this.getClass() + 
				".getLineCoverageSegment(int p) is not implemented,"
				+ "please override it if you have implemented line coverage segments.");
	}

	public void setCovered(Boolean covered) {
		this.covered = covered;
	}

	public void setCoverageUnits(short p) {
		this.cus = p;
	}

	public void setCoveredCoverageUnits(short p) {
		this.covered_cus = p;
	}

	public void setBranches(short p) {
		this.branches = p;
	}

	public void setCovered_branches(short p) {
		this.covered_branches = p;
	}

	public void setHasSegments(boolean p) {
		this.hasSegments = p;
	}

	@Override
	public String toString() {
		return toString(this);
	}

	public String toString(I_LineCoverage p) {
		return p.getClass().getName() + " [covered=" + p.getCovered() + ", cus=" + 
				p.getCoverageUnits() +
				", covered_cus=" + p.getCoveredCoverageUnits() +
				", branches=" + p.getBranches() +
				", covered_branches=" + p.getCoveredBranches() + 
				", hasSegments=" + p.hasSegments() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + branches;
		result = prime * result + ((covered == null) ? 0 : covered.hashCode());
		result = prime * result + covered_branches;
		result = prime * result + covered_cus;
		result = prime * result + cus;
		result = prime * result + (hasSegments ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof I_LineCoverage) {
			I_LineCoverage other = (I_LineCoverage) obj;
			if (branches != other.getBranches())
				return false;
			if (covered == null) {
				if (other.getCovered() != null)
					return false;
			} else if (!covered.equals(other.getCovered()))
				return false;
			if (covered_branches != other.getCoveredBranches())
				return false;
			if (covered_cus != other.getCoveredCoverageUnits())
				return false;
			if (cus != other.getCoverageUnits())
				return false;
			if (hasSegments != other.hasSegments())
				return false;
		} else {
			return false;
		}
		return true;
	}
}
