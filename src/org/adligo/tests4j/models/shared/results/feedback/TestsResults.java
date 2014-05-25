package org.adligo.tests4j.models.shared.results.feedback;


public class TestsResults {
	private TestsResultsMutant mutant;

	public TestsResults() {}
	
	public TestsResults(I_TestsResults p) {
		mutant = new TestsResultsMutant(p);
	}
	public boolean hasRecordedCoverage() {
		return mutant.hasRecordedCoverage();
	}

	public long getUniqueAssertions() {
		return mutant.getUniqueAssertions();
	}

	public long getAssertions() {
		return mutant.getAssertions();
	}
	
	
}
