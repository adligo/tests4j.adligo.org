package org.adligo.tests4j.shared.asserts.common;


/**
 * a mutable class to represent 
 * data for a thrown comparison.
 *  
 * @author scott
 *
 */
public class ThrownAssertionDataMutant implements I_ThrownAssertionData {
	
	private I_ExpectedThrownData expected;
	private Throwable actual;
	private String failureReason;
	/**
	 * a one based (one is the first one not zero)
	 * indicator of which exception in the causation chain
	 * was not a match.
	 */
	private int failureThrowable;
	private I_AssertType type;
	
	public ThrownAssertionDataMutant() {}
	
	public ThrownAssertionDataMutant(I_ThrownAssertionData p) {
		expected = p.getExpected();
		actual = p.getActual();
		failureReason = p.getFailureReason();
		failureThrowable = p.getFailureThrowable();
		type = p.getType();
	}

	public I_ExpectedThrownData getExpected() {
		return expected;
	}

	public Throwable getActual() {
		return actual;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public int getFailureThrowable() {
		return failureThrowable;
	}

	public void setExpected(I_ExpectedThrownData expected) {
		this.expected = expected;
	}

	public void setActual(Throwable actual) {
		this.actual = actual;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public void setFailureThrowable(int failureThrowable) {
		this.failureThrowable = failureThrowable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result
				+ ((expected == null) ? 0 : expected.hashCode());
		result = prime * result
				+ ((failureReason == null) ? 0 : failureReason.hashCode());
		result = prime * result + failureThrowable;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_ThrownAssertionData other = (I_ThrownAssertionData) obj;
			if (actual == null) {
				if (other.getActual() != null)
					return false;
			} else if (!actual.equals(other.getActual()))
				return false;
			if (expected == null) {
				if (other.getExpected() != null)
					return false;
			} else if (!expected.equals(other.getExpected()))
				return false;
			if (failureReason == null) {
				if (other.getFailureReason() != null)
					return false;
			} else if (!failureReason.equals(other.getFailureReason()))
				return false;
			if (failureThrowable != other.getFailureThrowable()) 
				return false;
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}

	public String toString(I_ThrownAssertionData data) {
		return data.getClass().getSimpleName() + " [failureReason=" + data.getFailureReason()
				+ ", failureThrowable=" + data.getFailureThrowable() + "]";
	}
	
	@Override
	public String toString() {
		return toString(this);
	}

	public I_AssertType getType() {
		return type;
	}

	public void setType(I_AssertType type) {
		this.type = type;
	}
}
