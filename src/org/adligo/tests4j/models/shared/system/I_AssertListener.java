package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.asserts.I_AssertCommand;
import org.adligo.tests4j.models.shared.results.I_TestFailure;

public interface I_AssertListener {
	public void assertCompleted(I_AssertCommand cmd);
	public void assertFailed(I_TestFailure failure);
}