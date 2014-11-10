package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;

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

	public List<I_PackageCoverageBrief> getCoverage() {
		return Collections.unmodifiableList(mutant.getCoverage());
	}

	public int getTrials() {
		return mutant.getTrials();
	}

	public int getTests() {
		return mutant.getTests();
	}

	public long getAsserts() {
		return mutant.getAsserts();
	}

	public int getTestFailures() {
		return mutant.getTestFailures();
	}

	public int getTrialFailures() {
		return mutant.getTrialFailures();
	}

	public long getUniqueAsserts() {
		return mutant.getUniqueAsserts();
	}

	public int getTrialsPassed() {
		return mutant.getTrialsPassed();
	}

	public int getTestsPassed() {
		return mutant.getTestsPassed();
	}

	public int getTrialsIgnored() {
		return mutant.getTrialsIgnored();
	}

	public int getTestsIgnored() {
		return mutant.getTestsIgnored();
	}

	public boolean hasCoverage() {
		return mutant.hasCoverage();
	}

	public double getCoveragePercentage() {
		return mutant.getCoveragePercentage();
	}

	public Set<String> getPassingTrials() {
		return Collections.unmodifiableSet(mutant.getPassingTrials());
	}

}
