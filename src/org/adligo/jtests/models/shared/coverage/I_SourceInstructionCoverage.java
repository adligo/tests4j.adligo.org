package org.adligo.jtests.models.shared.coverage;

/**
 * if the source code was available
 * this will give information about the 
 * mapping of the byte code instructions 
 * back to the source code
 * @author scott
 *
 */
public interface I_SourceInstructionCoverage {
	public int getSourceStartInstruction();
	public int getSourceEndInstruction();
}
