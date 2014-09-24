package org.adligo.tests4j.shared.asserts.common;


public class AssertThrownFailureMutant extends TestFailureMutant implements I_AssertThrownFailure {
	public I_ThrowableInfo expected;
	public I_ThrowableInfo actual;
	public String failureReason;
	public int throwable = 1;
	
	public AssertThrownFailureMutant() {}
	
	public AssertThrownFailureMutant(I_AssertThrownFailure p) {
		super(p);
		expected = p.getExpected();
		actual = p.getActual();
		failureReason = p.getFailureReason();
		throwable = p.getThrowable();
	}
	public I_ThrowableInfo getExpected() {
		return expected;
	}
	public I_ThrowableInfo getActual() {
		return actual;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public int getThrowable() {
		return throwable;
	}
	public void setExpected(I_ThrowableInfo expected) {
		this.expected = expected;
	}
	public void setActual(I_ThrowableInfo actual) {
		this.actual = actual;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public void setThrowable(int throwable) {
		this.throwable = throwable;
	}
	@Override
	public I_TestFailureType getType() {
		return TestFailureType.AssertThrownFailure;
	}
}
