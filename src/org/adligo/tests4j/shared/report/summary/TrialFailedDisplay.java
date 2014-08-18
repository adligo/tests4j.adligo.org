package org.adligo.tests4j.shared.report.summary;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TrialFailedDisplay {
	private I_Tests4J_Log logger;
	private List<I_TrialResult> failedTrials = new ArrayList<I_TrialResult>();
	
	public TrialFailedDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}

	public void onTrailFailed(I_TrialResult result) {
		failedTrials.add(result);
		if (logger.isLogEnabled(TrialFailedDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(I_Tests4J_Constants.PREFIX + messages.getTrialHeading()  + 
					result.getName() + messages.getFailedEOS());
		}
	}

	public List<I_TrialResult> getFailedTrials() {
		return failedTrials;
	}

}
