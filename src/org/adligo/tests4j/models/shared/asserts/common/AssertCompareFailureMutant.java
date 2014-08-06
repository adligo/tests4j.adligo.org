package org.adligo.tests4j.models.shared.asserts.common;

public class AssertCompareFailureMutant extends TestFailureMutant implements I_AssertCompareFailure {
	private String expectedClass;
	private String expectedValue;
	private String actualClass;
	private String actualValue;
	
	public AssertCompareFailureMutant() {}
	
	public AssertCompareFailureMutant(I_AssertCompareFailure p) {
		super(p);
		expectedClass = p.getExpectedClass();
		expectedValue = p.getExpectedValue();
		actualClass = p.getActualClass();
		actualValue = p.getActualValue();
	}
	
	public String getExpectedClass() {
		return expectedClass;
	}
	public String getExpectedValue() {
		return expectedValue;
	}
	public String getActualClass() {
		return actualClass;
	}
	public String getActualValue() {
		return actualValue;
	}
	public void setExpectedClass(String expectedClass) {
		this.expectedClass = expectedClass;
	}
	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}
	public void setActualClass(String actualClass) {
		this.actualClass = actualClass;
	}
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	
	@Override
	public I_TestFailureType getType() {
		return TestFailureType.AssertCompareFailure;
	}
}
