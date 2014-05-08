package org.adligo.tests4j.models.shared.coverage;

public class LineCoverageSegment implements I_LineCoverageSegment {
	private LineCoverageSegmentMutant mutant;
	
	public LineCoverageSegment() {
		mutant = new LineCoverageSegmentMutant();
	}
	
	public LineCoverageSegment(I_LineCoverageSegment p) {
		mutant = new LineCoverageSegmentMutant(p);
	}

	public Boolean getCovered() {
		return mutant.getCovered();
	}

	public short getStart() {
		return mutant.getStart();
	}

	public short getEnd() {
		return mutant.getEnd();
	}

	public String toString() {
		return mutant.toString(this);
	}
	
}
