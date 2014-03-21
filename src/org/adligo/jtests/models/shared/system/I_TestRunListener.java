package org.adligo.jtests.models.shared.system;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.results.I_TrialResult;
import org.adligo.jtests.models.shared.results.I_TrialRunResult;

public interface I_TestRunListener {
	public void onTestCompleted(Class<? extends I_AbstractTrial> testClass,
			I_AbstractTrial test, I_TrialResult result);
	public void onRunCompleted(I_TrialRunResult result);
}
