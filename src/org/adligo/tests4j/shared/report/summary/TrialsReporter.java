package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public class TrialsReporter {
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	public static final String PASSED = "Passed!";
	private I_Tests4J_Log logger;
	private TrialsFailedReporter failuresReporter;
	private boolean hadTrialTestsWhichDidNOTRun = false;
	
	
	public TrialsReporter(I_Tests4J_Log pLogger) {
		logger = pLogger;
		failuresReporter = new TrialsFailedReporter(pLogger);
	}
	
	public void onStartingTrial(String trialName) {
		if (logger.isLogEnabled(TrialsReporter.class)) {
			logger.log("startingTrial: " + trialName);
		}
	}

	public void onTrialCompleted(I_TrialResult result) {
		if (!result.isPassed()) {
			failuresReporter.onTrialCompleted(result);
		} else {
			if (logger.isLogEnabled(TrialsReporter.class)) {
				logger.log("Trial: " + result.getName() + " passed!");
			}
		}
		if (result.isHadAfterTrialTests()) {
			if (!result.isRanAfterTrialTests()) {
				hadTrialTestsWhichDidNOTRun = true;
			}
		}
	}

	protected List<I_TrialResult> getFailedTrials() {
		return failuresReporter.getFailedTrials();
	}

	protected boolean isHadTrialTestsWhichDidNOTRun() {
		return hadTrialTestsWhichDidNOTRun;
	}
}
