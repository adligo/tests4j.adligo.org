package org.adligo.tests4j.models.shared.coverage;

public class LineCoverageSegmentMutant implements I_LineCoverageSegment {
	private Boolean covered;
	private short start;
	private short end;
	
	public LineCoverageSegmentMutant() {}
	
	public LineCoverageSegmentMutant(I_LineCoverageSegment p) {
		covered = p.getCovered();
		start = p.getStart();
		end = p.getEnd();
	}
	
	public Boolean getCovered() {
		return covered;
	}
	public short getStart() {
		return start;
	}
	public short getEnd() {
		return end;
	}
	public void setCovered(Boolean covered) {
		this.covered = covered;
	}
	public void setStart(byte start) {
		this.start = start;
	}
	public void setEnd(byte end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return toString(this);
	}

	public String toString(I_LineCoverageSegment p) {
		return p.getClass().getName() + " [covered=" + p.getCovered() + ", start="
				+ p.getStart() + ", end=" + p.getEnd() + "]";
	}
}
