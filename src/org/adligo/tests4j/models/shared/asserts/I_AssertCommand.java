package org.adligo.tests4j.models.shared.asserts;


public interface I_AssertCommand {
	public I_AssertType getType();
	public String getFailureMessage();
	public I_AssertionData getData();
}
