package org.adligo.tests4j.models.shared.coverage;

public class InstructionCoverageMutant implements I_InstructionCoverage {
	private boolean covered;
	private I_SourceInstructionCoverage sourceInstructionCoverage;
	
	public InstructionCoverageMutant() {}
	
	public InstructionCoverageMutant(I_InstructionCoverage p) {
		covered = p.isCovered();
		sourceInstructionCoverage = p.getSourceInstructionCoverage();
	}
	
	public boolean isCovered() {
		return covered;
	}
	public I_SourceInstructionCoverage getSourceInstructionCoverage() {
		return sourceInstructionCoverage;
	}
	public void setCovered(boolean covered) {
		this.covered = covered;
	}
	public void setSourceInstructionCoverage(
			I_SourceInstructionCoverage sourceInstructionCoverage) {
		this.sourceInstructionCoverage = sourceInstructionCoverage;
	}
	
	
}
