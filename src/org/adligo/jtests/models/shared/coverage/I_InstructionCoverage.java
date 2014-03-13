package org.adligo.jtests.models.shared.coverage;

public interface I_InstructionCoverage {
	public int getSourceStartInstruction();
	public int getSourceEndInstruction();
	public boolean covered();
}
