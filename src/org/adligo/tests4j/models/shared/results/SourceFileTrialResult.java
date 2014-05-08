package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;

public class SourceFileTrialResult {
	private SourceFileTrialResultMutant mutant;
	
	public SourceFileTrialResult() {
		mutant = new SourceFileTrialResultMutant();
	}
	
	public SourceFileTrialResult(I_SourceFileTrialResult p) {
		mutant = new SourceFileTrialResultMutant(p);
	}

	public I_SourceFileCoverage getSourceFileCoverage() {
		return mutant.getSourceFileCoverage();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public String getName() {
		return mutant.getName();
	}


	public TrialTypeEnum getType() {
		return mutant.getType();
	}

	public List<I_TestResult> getResults() {
		return mutant.getResults();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}

	public boolean isIgnored() {
		return mutant.isIgnored();
	}

	public String getBeforeTestOutput() {
		return mutant.getBeforeTestOutput();
	}

	public String getAfterTestOutput() {
		return mutant.getAfterTestOutput();
	}

	public String toString() {
		return mutant.toString();
	}

	public I_TrialFailure getFailure() {
		return mutant.getFailure();
	}

	public boolean isPassed() {
		return mutant.isPassed();
	}

	public int getTestCount() {
		return mutant.getTestCount();
	}

	public int getTestFailureCount() {
		return mutant.getTestFailureCount();
	}

	public int getAssertionCount() {
		return mutant.getAssertionCount();
	}

	public int getUniqueAssertionCount() {
		return mutant.getUniqueAssertionCount();
	}

	public String getSourceFileName() {
		return mutant.getSourceFileName();
	}
}
