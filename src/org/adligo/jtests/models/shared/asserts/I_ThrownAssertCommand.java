package org.adligo.jtests.models.shared.asserts;

public interface I_ThrownAssertCommand extends I_AssertCommand {
	public boolean evaluate(I_Thrower p);
}
