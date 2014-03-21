package org.adligo.jtests.models.shared.results;

import java.math.BigDecimal;
import java.util.List;

import org.adligo.jtests.models.shared.common.TrialType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public class TrialRunResult implements I_TrialRunResult {
	private TrialRunResultMutant mutant;
	
	public TrialRunResult(I_TrialRunResult result) {
		mutant = new TrialRunResultMutant(result);
	}

	public long getStartTime() {
		return mutant.getStartTime();
	}

	public long getRunTime() {
		return mutant.getRunTime();
	}

	@Override
	public BigDecimal getRunTimeSecs() {
		return mutant.getRunTimeSecs();
	}

}