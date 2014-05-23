package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverageMutant;

public class SourceFileTrial_TestRunInfoMutant extends TestRunInfoMutant implements I_SourceFileTrial_TestRunInfo {
	private SourceFileCoverageMutant coverage;

	public SourceFileTrial_TestRunInfoMutant() {}
	
	public SourceFileTrial_TestRunInfoMutant(I_SourceFileTrial_TestRunInfo p) {
		super(p);
		setCoverage(p.getCoverage());
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_SourceFileTrial_TestRunInfo#getCoverage()
	 */
	@Override
	public SourceFileCoverageMutant getCoverage() {
		return coverage;
	}

	public void setCoverage(SourceFileCoverageMutant coverage) {
		this.coverage = coverage;
	}
	
	public void setCoverage(I_SourceFileCoverage p) {
		this.coverage = new SourceFileCoverageMutant(p);
	}
}
