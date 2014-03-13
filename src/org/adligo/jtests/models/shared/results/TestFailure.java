package org.adligo.jtests.models.shared.results;

import org.adligo.jtests.models.shared.common.I_Immutable;

public class TestFailure implements I_TestFailure, I_Immutable {
	private TestFailureMutant mutant;
	
	public TestFailure(I_TestFailure p) {
		mutant = new TestFailureMutant(p);
	}

	public String getMessage() {
		return mutant.getMessage();
	}

	public Object getExpected() {
		return mutant.getExpected();
	}

	public Object getActual() {
		return mutant.getActual();
	}

	public Throwable getLocationFailed() {
		return mutant.getLocationFailed();
	}

	public Throwable getException() {
		return mutant.getException();
	}
	
	@Override
	public String toString() {
		return mutant.toString(TestFailure.class);
	}
}
