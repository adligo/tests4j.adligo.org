package org.adligo.tests4j.shared.asserts.common;

import java.util.Set;

/**
 * a immutable class to represent 
 * data about a thrown assertion.
 * @author scott
 *
 */
public class ThrownAssertionData implements I_ThrownAssertionData {
	private ThrownAssertionDataMutant mutant;
	
	public ThrownAssertionData() {
		mutant = new ThrownAssertionDataMutant();
	}
	
	public ThrownAssertionData(I_ThrownAssertionData p) {
		mutant = new ThrownAssertionDataMutant(p);
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}

	public String getFailureReason() {
		return mutant.getFailureReason();
	}

	public int getFailureThrowable() {
		return mutant.getFailureThrowable();
	}
	
	@Override
	public String toString() {
		return mutant.toString(this);
	}

	public I_ExpectedThrowable getExpected() {
		return mutant.getExpected();
	}

	public Throwable getActual() {
		return mutant.getActual();
	}

	public I_AssertType getType() {
		return mutant.getType();
	}
}
