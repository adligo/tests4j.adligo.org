package org.adligo.tests4j.models.shared.metadata;

public class TestRunInfo {
	private TestRunInfoMutant mutant;

	public TestRunInfo() {}
	
	public TestRunInfo(I_TestRunInfo p) {
		mutant = new TestRunInfoMutant(p);
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
