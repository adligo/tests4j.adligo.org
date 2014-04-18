package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

public interface I_TrialRunListener {
	public void onMetadataCalculated(I_TrialRunMetadata metadata);
	public void onTrialCompleted(I_TrialResult result);
	public void onRunCompleted(I_TrialRunResult result);
}
