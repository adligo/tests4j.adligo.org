package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TrialDisplay {
	private I_Tests4J_Log logger;
	private TrialFailedDisplay failuresReporter;
	private ThreadDisplay theadDisplay;
	
	public TrialDisplay(I_Tests4J_Log pLogger, ThreadDisplay pTheadDisplay) {
		logger = pLogger;
		failuresReporter = new TrialFailedDisplay(pLogger);
		theadDisplay = pTheadDisplay;
	}
	
	public void onStartingTrial(String trialName) {
		if (logger.isLogEnabled(TrialDisplay.class)) {
			I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
			String message = I_Tests4J_Constants.PREFIX + messages.getStartingTrial() + trialName; 
			if (theadDisplay.isOn()) {
				message = message + " " +theadDisplay.getThreadInfo();
			}
			logger.log(message);
		}
	}

	public void onTrialCompleted(I_TrialResult result) {
		if (!result.isPassed()) {
			failuresReporter.onTrailFailed(result);
		} else {
			if (logger.isLogEnabled(TrialDisplay.class)) {
				I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
				
				String message =I_Tests4J_Constants.PREFIX + messages.getTrialHeading() + result.getName() + 
						messages.getPassedEOS();
				logger.log(message);
			}
		}
	}

	public List<I_TrialResult> getFailedTrials() {
		return failuresReporter.getFailedTrials();
	}

}
