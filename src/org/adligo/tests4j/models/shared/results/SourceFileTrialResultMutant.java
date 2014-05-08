package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverageMutant;

public class SourceFileTrialResultMutant extends TrialResultMutant implements I_SourceFileTrialResult {
	private SourceFileCoverageMutant coverage;
	private String sourceFileName;
	
	public SourceFileTrialResultMutant() {}
	
	public SourceFileTrialResultMutant(I_SourceFileTrialResult p) {
		super(p);
		coverage = new SourceFileCoverageMutant(p.getSourceFileCoverage());
	}

	@Override
	public I_SourceFileCoverage getSourceFileCoverage() {
		return coverage;
	}

	public void setSourceFileCoverage(I_SourceFileCoverage p) {
		this.coverage = new SourceFileCoverageMutant(p);
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(this, sb);
		sb.append(",coverage=");
		sb.append(coverage);
		sb.append(",sourceFileName=");
		sb.append(sourceFileName);
		sb.append("]");
		return sb.toString();
	}
}
