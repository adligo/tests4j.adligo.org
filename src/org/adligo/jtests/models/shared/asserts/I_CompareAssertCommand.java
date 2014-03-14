package org.adligo.jtests.models.shared.asserts;

public interface I_CompareAssertCommand extends I_BasicAssertCommand {
	public Object getExpected();
	public Object getActual();
}
