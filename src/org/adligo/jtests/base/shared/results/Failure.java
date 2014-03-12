package org.adligo.jtests.base.shared.results;

import org.adligo.jtests.base.shared.common.I_Immutable;

public class Failure implements I_Failure, I_Immutable {
	private FailureMutant mutant;
	
	public Failure(I_Failure p) {
		mutant = new FailureMutant(p);
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
		return mutant.toString(Failure.class);
	}
}
