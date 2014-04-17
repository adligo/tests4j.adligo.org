package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.asserts.I_AssertionData;
import org.adligo.tests4j.models.shared.common.I_Immutable;

public class TestFailure implements I_TestFailure, I_Immutable {
	private TestFailureMutant mutant;
	
	public TestFailure(I_TestFailure p) {
		mutant = new TestFailureMutant(p);
	}

	public String getMessage() {
		return mutant.getMessage();
	}

	public I_AssertionData getData() {
		return mutant.getData();
	}


	public Throwable getLocationFailed() {
		return mutant.getLocationFailed();
	}

	public Throwable getException() {
		return mutant.getException();
	}
	
	@Override
	public String toString() {
		return mutant.toString(TrialFailure.class);
	}
}
