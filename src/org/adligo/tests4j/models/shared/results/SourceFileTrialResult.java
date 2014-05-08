package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverage;

public class SourceFileTrialResult extends BaseTrialResult implements I_SourceFileTrialResult {
	private SourceFileTrialResultMutant mutant;
	private SourceFileCoverage coverage;
	
	public SourceFileTrialResult() {
		mutant = new SourceFileTrialResultMutant();
	}
	
	public SourceFileTrialResult(I_SourceFileTrialResult p) {
		super(p);
		mutant = new SourceFileTrialResultMutant(p, false);
		I_SourceFileCoverage sourceFileCoverage =  p.getSourceFileCoverage();
		if (sourceFileCoverage != null) {
			coverage = new SourceFileCoverage(sourceFileCoverage);
		}
	}
	
	public I_SourceFileCoverage getSourceFileCoverage() {
		return coverage;
	}

	public String getSourceFileName() {
		return mutant.getSourceFileName();
	}

}
