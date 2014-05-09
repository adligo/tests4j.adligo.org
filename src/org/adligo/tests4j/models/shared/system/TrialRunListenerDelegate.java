package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

/**
 * just synchronizes all of the methods for the delegate
 * 
 * @author scott
 *
 */
public class TrialRunListenerDelegate implements I_TrialRunListener {
	private I_TrialRunListener delegate;

	public TrialRunListenerDelegate(I_TrialRunListener p) {
		delegate = p;
	}
	
	public synchronized void onMetadataCalculated(I_TrialRunMetadata metadata) {
		delegate.onMetadataCalculated(metadata);
	}

	public synchronized void onStartingTrail(String trialName) {
		delegate.onStartingTrail(trialName);
	}

	public synchronized void onStartingTest(String trialName, String testName) {
		delegate.onStartingTest(trialName, testName);
	}

	public synchronized void onTestCompleted(String trialName, String testName,
			boolean passed) {
		delegate.onTestCompleted(trialName, testName, passed);
	}

	public synchronized void onTrialCompleted(I_TrialResult result) {
		delegate.onTrialCompleted(result);
	}

	public synchronized void onRunCompleted(I_TrialRunResult result) {
		delegate.onRunCompleted(result);
	}
	
}
