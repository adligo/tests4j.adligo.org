package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;

public class TrialResult implements I_TrialResult {
	private TrialResultMutant mutant;
	
	public TrialResult(I_TrialResult p) {
		mutant = new TrialResultMutant(p);
	}
	
	@Override
	public String toString() {
		return mutant.toString(this);
	}

	public String getName() {
		return mutant.getName();
	}

	public TrialTypeEnum getType() {
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

	public int getTestFailureCount() {
		return mutant.getTestFailureCount();
	}
	
}
