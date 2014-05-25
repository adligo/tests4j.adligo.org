package org.adligo.tests4j.models.shared.results.feedback;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverageMutant;

public class SourceFileTrial_TestsResultsMutant extends TestsResultsMutant implements I_SourceFileTrial_TestsResults {
	private SourceFileCoverageMutant coverage;

	public SourceFileTrial_TestsResultsMutant() {}
	
	public SourceFileTrial_TestsResultsMutant(I_SourceFileTrial_TestsResults p) {
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
