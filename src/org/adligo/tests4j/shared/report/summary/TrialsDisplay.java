package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class TrialsDisplay {
	private I_Tests4J_Log logger;
	private TrialsFailedDisplay failuresReporter;
	private boolean hadTrialTestsWhichDidNOTRun = false;
	
	
	public TrialsDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
		failuresReporter = new TrialsFailedDisplay(pLogger);
	}
	
	public void onStartingTrial(String trialName) {
		if (logger.isLogEnabled(TrialsDisplay.class)) {
			I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(I_Tests4J_Constants.PREFIX + messages.getStartingTrial() + trialName);
		}
	}

	public void onTrialCompleted(I_TrialResult result) {
		if (!result.isPassed()) {
			failuresReporter.onTrialCompleted(result);
		} else {
			if (logger.isLogEnabled(TrialsDisplay.class)) {
				I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
				logger.log(I_Tests4J_Constants.PREFIX + messages.getTrialHeading() + result.getName() + 
						messages.getPassedEOS());
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
