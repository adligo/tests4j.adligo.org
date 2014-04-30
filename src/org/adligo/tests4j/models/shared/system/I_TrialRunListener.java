package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

public interface I_TrialRunListener {
	/**
	 * in diagram Overview.seq
	 */
	public void onMetadataCalculated(I_TrialRunMetadata metadata);
	/**
	 * in diagram Overview.seq
	 */
	public void onTrialCompleted(I_TrialResult result);
	/**
	 * in diagram Overview.seq
	 */
	public void onRunCompleted(I_TrialRunResult result);
}
