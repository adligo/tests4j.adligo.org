package org.adligo.jtests.models.shared.results;

import java.math.BigDecimal;
import java.util.List;

import org.adligo.jtests.models.shared.common.TrialType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public class TestRunResult implements I_TestRunResult {
	private TestRunResultMutant mutant;
	
	public TestRunResult(I_TestRunResult result) {
		mutant = new TestRunResultMutant(result);
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
