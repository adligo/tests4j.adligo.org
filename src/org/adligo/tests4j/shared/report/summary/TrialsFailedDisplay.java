package org.adligo.tests4j.shared.report.summary;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class TrialsFailedDisplay {
	private I_Tests4J_Log logger;
	private List<I_TrialResult> failedTrials = new ArrayList<I_TrialResult>();
	private boolean hadTrialTestsWhichDidNOTRun = false;
	
	public TrialsFailedDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}

	public void onTrialCompleted(I_TrialResult result) {
		failedTrials.add(result);
		if (logger.isLogEnabled(TrialsFailedDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(I_Tests4J_Constants.HEADER + messages.getTrialsHeading()  + 
					result.getName() + messages.getFailedEOS());
		}
	}

	protected List<I_TrialResult> getFailedTrials() {
		return failedTrials;
	}

	protected boolean isHadTrialTestsWhichDidNOTRun() {
		return hadTrialTestsWhichDidNOTRun;
	}
}
