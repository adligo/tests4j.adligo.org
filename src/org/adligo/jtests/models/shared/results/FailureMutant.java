package org.adligo.jtests.models.shared.results;

public class FailureMutant implements I_Failure {
	public static final String FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_MESSAGES = "FailureMutant does NOT allow null messages.";
	public static final String FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_EXPECTED = "FailureMutant does NOT allow null expected.";
	public static final String FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_ACTUAL = "FailureMutant does NOT allow null actual.";
	public static final String FAILURE_MUTANT_REQUIRES_EITHER_A_LOCATION_FAILED_OR_A_EXCEPTION = "FailureMutant requires either a locationFailed or a exception.";
	private String message;
	private Object expected;
	private Object actual;
	private Throwable locationFailed;
	private Throwable exception;
	
	public FailureMutant() {}
	
	public FailureMutant(I_Failure p) {
		message = p.getMessage();
		if (message == null || message.trim().length() == 0) {
			throw new IllegalArgumentException(
					FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_MESSAGES);
		}
		expected = p.getExpected();
		if (expected == null) {
			throw new IllegalArgumentException(
					FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_EXPECTED);
		}
		actual = p.getActual();
		if (actual == null) {
			throw new IllegalArgumentException(
					FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_ACTUAL);
		}
		locationFailed = p.getLocationFailed();
		exception = p.getException();
		if (locationFailed == null && exception == null) {
			if (message == null) {
				throw new IllegalArgumentException(
						FAILURE_MUTANT_REQUIRES_EITHER_A_LOCATION_FAILED_OR_A_EXCEPTION);
			}
		}
	}
	
 	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_Failure#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_Failure#getExpected()
	 */
	@Override
	public Object getExpected() {
		return expected;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_Failure#getActual()
	 */
	@Override
	public Object getActual() {
		return actual;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_Failure#getLocationFailed()
	 */
	@Override
	public Throwable getLocationFailed() {
		return locationFailed;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_Failure#getException()
	 */
	@Override
	public Throwable getException() {
		return exception;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setExpected(Object expected) {
		this.expected = expected;
	}
	public void setActual(Object actual) {
		this.actual = actual;
	}
	public void setLocationFailed(Throwable locationFailed) {
		this.locationFailed = locationFailed;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return toString(FailureMutant.class);
	}
	
	String toString(Class<?> c) {
		return  c.getSimpleName() + " [message=" + message + ", expected=" + expected
				+ ", actual=" + actual + ", locationFailed=" + locationFailed
				+ ", exception=" + exception + "]";
	}
}
