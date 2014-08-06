package org.adligo.tests4j.models.shared.asserts.common;


public class AssertThrownFailure extends TestFailure implements I_AssertThrownFailure {
	private AssertThrownFailureMutant mutant;
	
	public AssertThrownFailure() {
		mutant = new AssertThrownFailureMutant();
	}
	
	public AssertThrownFailure(I_AssertThrownFailure p) {
		super(p);
		mutant = new AssertThrownFailureMutant(p);
	}

	public I_ThrowableInfo getExpected() {
		return mutant.getExpected();
	}

	public I_ThrowableInfo getActual() {
		return mutant.getActual();
	}

	public String getFailureReason() {
		return mutant.getFailureReason();
	}

	public int getThrowable() {
		return mutant.getThrowable();
	}
	
	@Override
	public I_TestFailureType getType() {
		return TestFailureType.AssertThrownFailure;
	}
}
