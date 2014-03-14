package org.adligo.jtests.models.shared.results;

import org.adligo.jtests.models.shared.asserts.I_AssertionData;

public class TestFailureMutant implements I_TestFailure {
	public static final String TEST_FAILURE_MUTANT_REQURIES_ASSERTION_DATA = "TestFailureMutant requries assertion data.";
	public static final String FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_MESSAGES = "TestFailureMutant does NOT allow null messages.";
	public static final String FAILURE_MUTANT_REQUIRES_EITHER_A_LOCATION_FAILED_OR_A_EXCEPTION = "TestFailureMutant requires either a locationFailed or a exception.";
	private String message;
	private I_AssertionData data;
	private Throwable locationFailed;
	private Throwable exception;
	
	public TestFailureMutant() {}
	
	public TestFailureMutant(I_TestFailure p) {
		message = p.getMessage();
		if (message == null || message.trim().length() == 0) {
			throw new IllegalArgumentException(
					FAILURE_MUTANT_DOES_NOT_ALLOW_NULL_MESSAGES);
		}
		locationFailed = p.getLocationFailed();
		exception = p.getException();
		if (locationFailed == null && exception == null) {
			if (message == null) {
				throw new IllegalArgumentException(
						FAILURE_MUTANT_REQUIRES_EITHER_A_LOCATION_FAILED_OR_A_EXCEPTION);
			}
		}
		data = p.getData();
		/*
		if (data == null) {
			
			throw new IllegalArgumentException(
					TEST_FAILURE_MUTANT_REQURIES_ASSERTION_DATA, locationFailed);
		}
		*/
	}
	
 	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_Failure#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
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
	public void setLocationFailed(Throwable locationFailed) {
		this.locationFailed = locationFailed;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return toString(TestFailureMutant.class);
	}
	
	String toString(Class<?> c) {
		return  c.getSimpleName() + " [message=" + message + ", data=" + data
				+ ", locationFailed=" + locationFailed
				+ ", exception=" + exception + "]";
	}

	public I_AssertionData getData() {
		return data;
	}

	public void setData(I_AssertionData data) {
		this.data = data;
	}
}
