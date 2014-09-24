package org.adligo.tests4j.models.shared.results;

import java.util.Collections;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.common.I_TestFailure;

public class TestResult implements I_TestResult {
	private TestResultMutant mutant;
	
	public TestResult(I_TestResult p) {
		mutant = new TestResultMutant(p);
	}

	public String getName() {
		return mutant.getName();
	}

	public int getAssertionCount() {
		return mutant.getAssertionCount();
	}


	public boolean isPassed() {
		return mutant.isPassed();
	}

	public boolean isIgnored() {
		return mutant.isIgnored();
	}

	public I_TestFailure getFailure() {
		return mutant.getFailure();
	}

	public String getBeforeOutput() {
		return mutant.getBeforeOutput();
	}

	public String getOutput() {
		return mutant.getOutput();
	}

	public String getAfterOutput() {
		return mutant.getAfterOutput();
	}

	public Set<Integer> getUniqueAsserts() {
		return Collections.unmodifiableSet(mutant.getUniqueAsserts());
	}

	public int getUniqueAssertionCount() {
		return mutant.getUniqueAssertionCount();
	}

	public String toString() {
		return TestResultMutant.toString(this);
	}
}
