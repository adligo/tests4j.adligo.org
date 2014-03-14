package org.adligo.jtests.models.shared.system;

import org.adligo.jtests.models.shared.I_AbstractTest;
import org.adligo.jtests.models.shared.results.I_TestResult;
import org.adligo.jtests.models.shared.results.I_TestRunResult;

public interface I_TestRunListener {
	public void onTestCompleted(Class<? extends I_AbstractTest> testClass,
			I_AbstractTest test, I_TestResult result);
	public void onRunCompleted(I_TestRunResult result);
}
