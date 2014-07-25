package org.adligo.tests4j.shared.report.summary;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

public class TrialsReporter {
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	public static final String PASSED = "Passed!";
	private I_Tests4J_Logger logger;
	private List<I_TrialResult> failedTrials = new ArrayList<I_TrialResult>();
	private boolean hadTrialTestsWhichDidNOTRun = false;
	
	public TrialsReporter(I_Tests4J_Logger pLogger) {
		logger = pLogger;
	}
	
	public synchronized void onStartingTrial(String trialName) {
		if (logger.isLogEnabled(TrialsReporter.class)) {
			logger.log("startingTrial: " + trialName);
		}
	}

	public synchronized void onTrialCompleted(I_TrialResult result) {
		if (logger.isLogEnabled(TrialsReporter.class)) {
			String passedString = " passed!";
			if (!result.isPassed()) {
				passedString = " failed!";
				failedTrials.add(result);
			}
			logger.log("Trial: " + result.getName() + passedString);
		}
		if (result.isHadAfterTrialTests()) {
			if (!result.isRanAfterTrialTests()) {
				hadTrialTestsWhichDidNOTRun = true;
			}
		}
	}

	protected synchronized List<I_TrialResult> getFailedTrials() {
		return failedTrials;
	}

	protected synchronized boolean isHadTrialTestsWhichDidNOTRun() {
		return hadTrialTestsWhichDidNOTRun;
	}
}
