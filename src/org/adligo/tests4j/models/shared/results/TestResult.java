package org.adligo.tests4j.models.shared.results;

import java.util.Collections;
import java.util.Set;

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
		I_TestFailure toRetOrNull = mutant.getFailure();
		if (toRetOrNull == null) {
			return null;
		}
		return new TestFailure(toRetOrNull);
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
