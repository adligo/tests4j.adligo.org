package org.adligo.tests4j.models.shared.metadata;

public class TestRunInfo {
	private TestRunInfoMutant mutant;

	public TestRunInfo() {}
	
	public TestRunInfo(I_AbstractTestRunInfo p) {
		mutant = new TestRunInfoMutant(p);
	}
	public boolean hasRecordedCoverage() {
		return mutant.hasRecordedCoverage();
	}

	public I_TrialMetadata getTrialMetadata() {
		return new TrialMetadata(mutant.getTrialMetadata());
	}

	public long getUniqueAssertions() {
		return mutant.getUniqueAssertions();
	}

	public long getAssertions() {
		return mutant.getAssertions();
	}
	
	
}
