package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

public interface I_TrialRunListener {
	public void onTestCompleted(Class<? extends I_AbstractTrial> testClass,
			I_AbstractTrial test, I_TrialResult result);
	public void onRunCompleted(I_TrialRunResult result);
}
