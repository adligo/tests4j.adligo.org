package org.adligo.jtests.models.shared.coverage;

public interface I_InstructionCoverage {
	/**
	 * may return null if the source code was not available
	 * @return
	 */
	public I_SourceInstructionCoverage getSourceInstructionCoverage();
	public boolean isCovered();
}
