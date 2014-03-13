package org.adligo.jtests.models.shared.run;

import org.adligo.jtests.base.shared.asserts.I_AssertCommand;
import org.adligo.jtests.models.shared.results.I_TestFailure;

public interface I_AssertListener {
	public void assertCompleted(I_AssertCommand cmd);
	public void assertFailed(I_TestFailure failure);
}
