package org.adligo.tests4j.models.shared.asserts.common;

public interface I_CompareAssertCommand extends I_AssertCommand {
	public Object getExpected();
	public Object getActual();
}
