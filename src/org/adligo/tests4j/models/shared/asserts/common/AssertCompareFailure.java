package org.adligo.tests4j.models.shared.asserts.common;

public class AssertCompareFailure extends TestFailure implements I_AssertCompareFailure {
	private AssertCompareFailureMutant mutant;
	
	public AssertCompareFailure() {
		mutant = new AssertCompareFailureMutant();
	}
	
	public AssertCompareFailure(I_AssertCompareFailure p) {
		super(p);
		mutant = new AssertCompareFailureMutant(p);
	}

	public String getExpectedClass() {
		return mutant.getExpectedClass();
	}

	public String getExpectedValue() {
		return mutant.getExpectedValue();
	}

	public String getActualClass() {
		return mutant.getActualClass();
	}

	public String getActualValue() {
		return mutant.getActualValue();
	}
	
	@Override
	public I_TestFailureType getType() {
		return TestFailureType.AssertCompareFailure;
	}
}
