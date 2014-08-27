package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverage;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependencies;

public class SourceFileTrialResult extends BaseTrialResult implements I_SourceFileTrialResult {
	private SourceFileTrialResultMutant mutant;
	private SourceFileCoverage coverage;
	private I_ClassDependencies dependencies;
	
	public SourceFileTrialResult() {
		mutant = new SourceFileTrialResultMutant();
	}
	
	public SourceFileTrialResult(I_SourceFileTrialResult p) {
		super(p);
		mutant = new SourceFileTrialResultMutant(p, false);
		if (p.hasRecordedCoverage()) {
			coverage = new SourceFileCoverage( p.getSourceFileCoverage());
		}
		dependencies = p.getDependencies();
	}
	
	public I_SourceFileCoverage getSourceFileCoverage() {
		return coverage;
	}

	public String getSourceFileName() {
		return mutant.getSourceFileName();
	}
	
	@Override
	public boolean hasRecordedCoverage() {
		if (coverage == null) {
			return false;
		}
		return true;
	}

	public I_ClassDependencies getDependencies() {
		return dependencies;
	}

	public void setDependencies(I_ClassDependencies dependencies) {
		this.dependencies = dependencies;
	}

}
