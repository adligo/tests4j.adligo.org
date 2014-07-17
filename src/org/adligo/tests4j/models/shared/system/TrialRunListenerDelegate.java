package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

/**
 * just synchronizes all of the methods for the delegate
 * it also catches any sort of error/exception and loggs it to the I_Tests4J_Reporter
 * so that tests4j isn't blamed for other code.
 * 
 * @author scott
 *
 */
public class TrialRunListenerDelegate implements I_TrialRunListener {
	private I_TrialRunListener delegate;
	private I_Tests4J_Reporter reporter;
	
	public TrialRunListenerDelegate(I_TrialRunListener p, I_Tests4J_Reporter pReporter) {
		delegate = p;
		reporter = pReporter;
	}
	
	public synchronized void onMetadataCalculated(I_TrialRunMetadata metadata) {
		try {
			delegate.onMetadataCalculated(metadata);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public synchronized void onStartingTrail(String trialName) {
		try {
			delegate.onStartingTrail(trialName);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public synchronized void onStartingTest(String trialName, String testName) {
		try {
			delegate.onStartingTest(trialName, testName);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public synchronized void onTestCompleted(String trialName, String testName,
			boolean passed) {
		try {
			delegate.onTestCompleted(trialName, testName, passed);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public synchronized void onTrialCompleted(I_TrialResult result) {
		try {
			delegate.onTrialCompleted(result);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public synchronized void onRunCompleted(I_TrialRunResult result) {
		try {
			delegate.onRunCompleted(result);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}
	
}
