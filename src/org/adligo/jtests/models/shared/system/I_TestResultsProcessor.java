package org.adligo.jtests.models.shared.system;

import org.adligo.jtests.models.shared.results.I_TrialResult;
import org.adligo.jtests.models.shared.results.I_TrialRunResult;

public interface I_TestResultsProcessor {
	public void process(I_TrialResult result);
	public void process(I_TrialRunResult result);
}
