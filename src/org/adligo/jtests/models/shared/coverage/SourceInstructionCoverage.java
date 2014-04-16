package org.adligo.jtests.models.shared.coverage;

import org.adligo.jtests.models.shared.common.I_Immutable;

public class SourceInstructionCoverage implements I_SourceInstructionCoverage, I_Immutable {
	private int start;
	private int end;
	
	public SourceInstructionCoverage(int s, int e) {
		start = s;
		end = e;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
}
