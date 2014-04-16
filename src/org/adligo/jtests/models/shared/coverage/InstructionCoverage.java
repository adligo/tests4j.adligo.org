package org.adligo.jtests.models.shared.coverage;

public class InstructionCoverage implements I_InstructionCoverage {
	private InstructionCoverageMutant mutant;
	
	public InstructionCoverage() {
		mutant = new InstructionCoverageMutant();
	}
	
	public InstructionCoverage(I_InstructionCoverage p) {
		mutant = new InstructionCoverageMutant(p);
	}

	public boolean isCovered() {
		return mutant.isCovered();
	}

	public I_SourceInstructionCoverage getSourceInstructionCoverage() {
		return mutant.getSourceInstructionCoverage();
	}
	
	
}
