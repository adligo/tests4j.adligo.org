package org.adligo.tests4j.shared.asserts.common;


public class TestFailure implements I_TestFailure {
	private TestFailureMutant mutant;

	public TestFailure() {
		mutant = new TestFailureMutant();
	}
	
	public TestFailure(I_TestFailure p) {
		mutant = new TestFailureMutant(p);
	}
	
	public I_AssertType getAssertType() {
		return mutant.getAssertType();
	}

	public String getFailureMessage() {
		return mutant.getFailureMessage();
	}

	public String getFailureDetail() {
		return mutant.getFailureDetail();
	}

	public String toString() {
		return mutant.toString(this);
	}

	@Override
	public I_TestFailureType getType() {
		return TestFailureType.TestFailure;
	}
	
}
