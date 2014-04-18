package org.adligo.tests4j.models.shared.coverage;

/**
 * if the source code was available
 * this will give information about the 
 * mapping of the byte code instructions 
 * back to the source code
 * @author scott
 *
 */
public interface I_SourceInstructionCoverage {
	/**
	 * the character where the instruction starts
	 * @return
	 */
	public int getStart();
	/**
	 * the character where the instruction ends
	 * @return
	 */
	public int getEnd();
}