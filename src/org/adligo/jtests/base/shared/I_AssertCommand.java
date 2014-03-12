package org.adligo.jtests.base.shared;

public interface I_AssertCommand {
	public AssertCommandType getType();
	public boolean evaluate();
	public String getFailureMessage();
	public Object getExpected();
	public Object getActual();
}
