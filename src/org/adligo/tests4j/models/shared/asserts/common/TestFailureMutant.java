package org.adligo.tests4j.models.shared.asserts.common;


public class TestFailureMutant implements I_TestFailure {
	private I_AssertType assertType;
	private String failureMessage;
	private String failureDetail;
	
	public TestFailureMutant() {}
	
	public TestFailureMutant(I_TestFailure p) {
		assertType = p.getAssertType();
		failureMessage = p.getFailureMessage();
		failureDetail = p.getFailureDetail();
	}
	
	public I_AssertType getAssertType() {
		return assertType;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public String getFailureDetail() {
		return failureDetail;
	}
	public void setAssertType(I_AssertType type) {
		this.assertType = type;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	public void setFailureDetail(String failureDetail) {
		this.failureDetail = failureDetail;
	}

	@Override
	public String toString() {
		return toString(this);
	}
	
	public String toString(I_TestFailure p) {
		return p.getClass().getSimpleName() + " [type=" + p.getAssertType() + ", failureMessage="
				+ p.getFailureMessage() + "]";
	}
	
	@Override
	public I_TestFailureType getType() {
		return TestFailureType.TestFailure;
	}
}
