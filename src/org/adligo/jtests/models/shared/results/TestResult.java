package org.adligo.jtests.models.shared.results;

public class TestResult implements I_TestResult {
	private TestResultMutant mutant;
	
	public TestResult(I_TestResult p) {
		mutant = new TestResultMutant(p);
	}

	public String getExhibitName() {
		return mutant.getExhibitName();
	}

	public int getAssertionCount() {
		return mutant.getAssertionCount();
	}

	public int getUniqueAsserts() {
		return mutant.getUniqueAsserts();
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
}
