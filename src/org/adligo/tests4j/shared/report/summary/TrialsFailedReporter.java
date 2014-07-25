package org.adligo.tests4j.shared.report.summary;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public class TrialsFailedReporter {
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	private I_Tests4J_Log logger;
	private List<I_TrialResult> failedTrials = new ArrayList<I_TrialResult>();
	private boolean hadTrialTestsWhichDidNOTRun = false;
	
	public TrialsFailedReporter(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}

	public void onTrialCompleted(I_TrialResult result) {
		failedTrials.add(result);
		if (logger.isLogEnabled(TrialsFailedReporter.class)) {
			logger.log("Trial: " + result.getName() + " failed!");
		}
	}

	protected List<I_TrialResult> getFailedTrials() {
		return failedTrials;
	}

	protected boolean isHadTrialTestsWhichDidNOTRun() {
		return hadTrialTestsWhichDidNOTRun;
	}
}
