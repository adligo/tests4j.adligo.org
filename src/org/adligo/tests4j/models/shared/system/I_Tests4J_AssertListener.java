package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;
import org.adligo.tests4j.models.shared.results.I_TestFailure;

public interface I_Tests4J_AssertListener {
	public void assertCompleted(I_AssertCommand cmd);
	public void assertFailed(I_TestFailure failure);
}
