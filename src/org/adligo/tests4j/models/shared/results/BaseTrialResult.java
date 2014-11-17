package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.shared.common.I_TrialType;

public class BaseTrialResult implements I_TrialResult {
	private BaseTrialResultMutant mutant_;
	private List<I_TestResult> results_;
	
	public BaseTrialResult() {}
	
	public BaseTrialResult(I_TrialResult other) {
		mutant_ = (BaseTrialResultMutant) other.clone(false);
		List<I_TestResult> otherResults = other.getResults();
		if (otherResults.size() >= 1) {
			results_ = new ArrayList<I_TestResult>();
			for (I_TestResult result: otherResults) {
				results_.add(new TestResult(result));
			}
			results_ = Collections.unmodifiableList(results_);
		} else {
			results_ = Collections.emptyList();
		}
	}
	public int hashCode() {
		return mutant_.hashCode();
	}

	public String getName() {
		return mutant_.getName();
	}

	public I_TrialType getType() {
		return mutant_.getType();
	}

	public List<I_TestResult> getResults() {
		return results_;
	}

	public boolean isIgnored() {
		return mutant_.isIgnored();
	}

	public String getBeforeTrialOutput() {
		return mutant_.getBeforeTrialOutput();
	}

	public String getAfterTrialOutput() {
		return mutant_.getAfterTrialOutput();
	}

	public boolean equals(Object obj) {
		return mutant_.equals(obj);
	}

	public List<I_TrialFailure> getFailures() {
		return mutant_.getFailures();
	}

	@Override
	public boolean isPassed() {
		if (Boolean.FALSE.equals(mutant_.getPassed())) {
			return false;
		}
		/**
		 * trials with no results actually pass,
		 * so that interface trials can run successfully
		 * during a run with out the code coverage plug-in.
		 */
		for (I_TestResult result: results_) {
			if (!result.isIgnored()) {
				if (!result.isPassed()) {
					return false;
				}
			}
		}
		return true;
	}

	public int getTestCount() {
		return results_.size();
	}

	public int getTestFailureCount() {
		return BaseTrialResultMutant.getTestFailureCount(results_);
	}

	public int getAssertionCount() {
		return BaseTrialResultMutant.getAssertionCount(results_);
	}

	public int getUniqueAssertionCount() {
		return BaseTrialResultMutant.getUniqueAssertionCount(results_);
	}
	

	public BaseTrialResultMutant clone(boolean cloneRelations) {
		return new BaseTrialResultMutant(this, cloneRelations);
	}

	public boolean isHadAfterTrialTests() {
		return mutant_.isHadAfterTrialTests();
	}

	public boolean isRanAfterTrialTests() {
		return mutant_.isRanAfterTrialTests();
	}

	@Override
	public boolean hasRecordedCoverage() {
		return false;
	}

	public String getTrialClassName() {
		return mutant_.getTrialClassName();
	}

  @Override
  public int getRunNumber() {
    return mutant_.getRunNumber();
  }

  @Override
  public int getTestIgnoredCount() {
    return BaseTrialResultMutant.getTestIgnoredCount(results_);
  }
}
