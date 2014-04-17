package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

public interface I_TrialResultsProcessor {
	public void process(I_TrialResult result);
	public void process(I_TrialRunResult result);
}
