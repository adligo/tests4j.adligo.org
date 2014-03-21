package org.adligo.jtests.models.shared.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.jtests.models.shared.common.TrialType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public class TrialResult implements I_TrialResult {
	private TrialResultMutant mutant;
	
	public TrialResult(I_TrialResult p) {
		mutant = new TrialResultMutant(p);
	}
	
	@Override
	public String toString() {
		return mutant.toString(TrialResult.class);
	}

	public String getName() {
		return mutant.getName();
	}

	public String getTestedClassName() {
		return mutant.getTestedClassName();
	}

	public String getTestedPackageName() {
		return mutant.getTestedPackageName();
	}

	public TrialType getType() {
		return mutant.getType();
	}

	public List<I_TestResult> getResults() {
		List<I_TestResult> toRet = new ArrayList<I_TestResult>();
		for (I_TestResult mut: mutant.getResults()) {
			toRet.add(new TestResult(mut));
		}
		return Collections.unmodifiableList(toRet);
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

	public I_TrialFailure getFailure() {
		return mutant.getFailure();
	}

	public I_PackageCoverage getPackageCoverage() {
		return mutant.getPackageCoverage();
	}

	public I_ClassCoverage getClassCoverage() {
		return mutant.getClassCoverage();
	}

	public boolean isPassed() {
		return mutant.isPassed();
	}

	@Override
	public int getTestCount() {
		return mutant.getTestCount();
	}

	@Override
	public int getAssertionCount() {
		return mutant.getAssertionCount();
	}

	public int getUniqueAssertionCount() {
		return mutant.getUniqueAssertionCount();
	}
	
}
