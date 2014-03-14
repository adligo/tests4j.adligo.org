package org.adligo.jtests.models.shared.asserts;


public interface I_AssertCommand extends I_AssertionData{
	public I_AssertType getType();
	public boolean evaluate();
	public String getFailureMessage();
}
