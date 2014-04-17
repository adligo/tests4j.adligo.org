package org.adligo.tests4j.models.shared.coverage;

import java.util.ArrayList;
import java.util.List;

public class LineCoverageMutant {
	private LineStatus status;
	private List<I_InstructionCoverage> coverageDetail = new ArrayList<I_InstructionCoverage>();
	
	public LineStatus getStatus() {
		return status;
	}
	public List<I_InstructionCoverage> getCoverageDetail() {
		return coverageDetail;
	}
	public void setStatus(LineStatus status) {
		this.status = status;
	}
	public void setCoverageDetail(List<I_InstructionCoverage> coverageDetail) {
		this.coverageDetail = coverageDetail;
	}
}
