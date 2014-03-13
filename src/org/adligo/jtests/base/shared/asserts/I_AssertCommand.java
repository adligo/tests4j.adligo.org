package org.adligo.jtests.base.shared.asserts;


public interface I_AssertCommand {
	public AssertCommandType getType();
	public boolean evaluate();
	public String getFailureMessage();
	public I_AssertData getData();
}
