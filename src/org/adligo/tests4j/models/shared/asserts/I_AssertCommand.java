package org.adligo.tests4j.models.shared.asserts;


public interface I_AssertCommand extends I_AssertionData{
	public I_AssertType getType();
	public String getFailureMessage();
}
