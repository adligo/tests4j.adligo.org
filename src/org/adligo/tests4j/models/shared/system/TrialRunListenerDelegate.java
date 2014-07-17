package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

/**
 * just catches any sort of error/exception and loggs it to the I_Tests4J_Reporter
 * so that tests4j isn't blamed for other code.
 * 
 * @author scott
 *
 */
public class TrialRunListenerDelegate implements I_TrialRunListener {
	public static final String TRIAL_RUN_LISTENER_DELEGATE_REQUIRES_A_I_TESTS4J_REPORTER = "TrialRunListenerDelegate requires a I_Tests4J_Reporter.";
	public static final String TRIAL_RUN_LISTENER_DELEGATE_REQUIRES_A_I_TRIAL_RUN_LISTENER = "TrialRunListenerDelegate requires a I_TrialRunListener.";
	private I_TrialRunListener delegate;
	private I_Tests4J_Reporter reporter;
	
	public TrialRunListenerDelegate(I_TrialRunListener p, I_Tests4J_Reporter pReporter) {
		if (p == null) {
			throw new IllegalArgumentException(TRIAL_RUN_LISTENER_DELEGATE_REQUIRES_A_I_TRIAL_RUN_LISTENER);
		}
		delegate = p;
		
		if (pReporter == null) {
			throw new IllegalArgumentException(TRIAL_RUN_LISTENER_DELEGATE_REQUIRES_A_I_TESTS4J_REPORTER);
		}
		reporter = pReporter;
		
	}
	
	public void onMetadataCalculated(I_TrialRunMetadata metadata) {
		try {
			delegate.onMetadataCalculated(metadata);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public void onStartingTrial(String trialName) {
		try {
			delegate.onStartingTrial(trialName);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public void onStartingTest(String trialName, String testName) {
		try {
			delegate.onStartingTest(trialName, testName);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		try {
			delegate.onTestCompleted(trialName, testName, passed);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public void onTrialCompleted(I_TrialResult result) {
		try {
			delegate.onTrialCompleted(result);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}

	public void onRunCompleted(I_TrialRunResult result) {
		try {
			delegate.onRunCompleted(result);
		} catch (Throwable t) {
			reporter.onError(t);
		}
	}
	
}
