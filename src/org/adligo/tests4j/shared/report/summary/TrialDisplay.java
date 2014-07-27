package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class TrialDisplay {
	private I_Tests4J_Log logger;
	private TrialFailedDisplay failuresReporter;
	
	public TrialDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
		failuresReporter = new TrialFailedDisplay(pLogger);
	}
	
	public void onStartingTrial(String trialName) {
		if (logger.isLogEnabled(TrialDisplay.class)) {
			I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(I_Tests4J_Constants.PREFIX + messages.getStartingTrial() + trialName);
		}
	}

	public void onTrialCompleted(I_TrialResult result) {
		if (!result.isPassed()) {
			failuresReporter.onTrailFailed(result);
		} else {
			if (logger.isLogEnabled(TrialDisplay.class)) {
				I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
				logger.log(I_Tests4J_Constants.PREFIX + messages.getTrialHeading() + result.getName() + 
						messages.getPassedEOS());
			}
		}
	}

	public List<I_TrialResult> getFailedTrials() {
		return failuresReporter.getFailedTrials();
	}

}
