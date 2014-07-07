package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialType;

public class BaseTrialResult implements I_TrialResult {
	private BaseTrialResultMutant mutant;
	private List<I_TestResult> results;
	
	public BaseTrialResult() {}
	
	public BaseTrialResult(I_TrialResult other) {
		mutant = (BaseTrialResultMutant) other.clone(false);
		List<I_TestResult> otherResults = other.getResults();
		if (otherResults.size() >= 1) {
			results = new ArrayList<I_TestResult>();
			for (I_TestResult result: otherResults) {
				results.add(new TestResult(result));
			}
			results = Collections.unmodifiableList(results);
		} else {
			results = Collections.emptyList();
		}
	}
	public int hashCode() {
		return mutant.hashCode();
	}

	public String getName() {
		return mutant.getName();
	}

	public TrialType getType() {
		return mutant.getType();
	}

	public List<I_TestResult> getResults() {
		return results;
	}

	public boolean isIgnored() {
		return mutant.isIgnored();
	}

	public String getBeforeTrialOutput() {
		return mutant.getBeforeTrialOutput();
	}

	public String getAfterTrialOutput() {
		return mutant.getAfterTrialOutput();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}

	public I_TrialFailure getFailure() {
		return mutant.getFailure();
	}

	@Override
	public boolean isPassed() {
		if (Boolean.FALSE.equals(mutant.getPassed())) {
			return false;
		}
		if (results.size() == 0) {
			return false;
		}
		for (I_TestResult result: results) {
			if (!result.isIgnored()) {
				if (!result.isPassed()) {
					return false;
				}
			}
		}
		return true;
	}

	public int getTestCount() {
		return results.size();
	}

	public int getTestFailureCount() {
		return BaseTrialResultMutant.getTestFailureCount(results);
	}

	public int getAssertionCount() {
		return BaseTrialResultMutant.getAssertionCount(results);
	}

	public int getUniqueAssertionCount() {
		return BaseTrialResultMutant.getUniqueAssertionCount(results);
	}
	

	public BaseTrialResultMutant clone(boolean cloneRelations) {
		return new BaseTrialResultMutant(this, cloneRelations);
	}

	public boolean isHadAfterTrialTests() {
		return mutant.isHadAfterTrialTests();
	}

	public boolean isRanAfterTrialTests() {
		return mutant.isRanAfterTrialTests();
	}

	@Override
	public boolean hasRecordedCoverage() {
		return false;
	}

	public String getTrialClassName() {
		return mutant.getTrialClassName();
	}
}
