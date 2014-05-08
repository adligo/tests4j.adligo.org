package org.adligo.tests4j.models.shared.coverage;

/**
 * immutable @see {@link I_LineCoverage}
 * @author scott
 *
 */
public class LineCoverage implements I_LineCoverage {
	private LineCoverageMutant mutant;
	
	public LineCoverage() {
		mutant = new LineCoverageMutant();
	}
	
	public LineCoverage(I_LineCoverage p) {
		mutant = new LineCoverageMutant(p);
	}

	public Boolean getCovered() {
		return mutant.getCovered();
	}

	public short getCoverageUnits() {
		return mutant.getCoverageUnits();
	}

	public short getCoveredCoverageUnits() {
		return mutant.getCoveredCoverageUnits();
	}

	public short getBranches() {
		return mutant.getBranches();
	}

	public short getCoveredBranches() {
		return mutant.getCoveredBranches();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean hasSegments() {
		return mutant.hasSegments();
	}

	public short getLineCoverageSegmentCount() {
		return mutant.getLineCoverageSegmentCount();
	}

	public I_LineCoverageSegment getLineCoverageSegment(int p) {
		return mutant.getLineCoverageSegment(p);
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}

	@Override
	public String toString() {
		return mutant.toString(this);
	}
}
