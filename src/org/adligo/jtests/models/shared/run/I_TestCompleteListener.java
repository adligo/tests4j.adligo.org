package org.adligo.jtests.models.shared.run;

import org.adligo.jtests.base.shared.asserts.I_AbstractTest;
import org.adligo.jtests.models.shared.results.I_TestResult;

public interface I_TestCompleteListener {
	public void onTestCompleted(Class<? extends I_AbstractTest> testClass,
			I_AbstractTest test, I_TestResult result);
}
