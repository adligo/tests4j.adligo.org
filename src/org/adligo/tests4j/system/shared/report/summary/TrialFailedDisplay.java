package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

import java.util.ArrayList;
import java.util.List;

public class TrialFailedDisplay {
  private final I_Tests4J_Constants constants_;
	private final I_Tests4J_Log logger;
	private List<I_TrialResult> failedTrials = new ArrayList<I_TrialResult>();
	
	public TrialFailedDisplay(I_Tests4J_Constants constants, I_Tests4J_Log pLogger) {
	  constants_ = constants;
		logger = pLogger;
	}

	public void onTrailFailed(I_TrialResult result) {
		failedTrials.add(result);
		if (logger.isLogEnabled(TrialFailedDisplay.class)) {
			I_Tests4J_ReportMessages messages = constants_.getReportMessages();
			logger.logLine(constants_.getPrefix(), messages.getTrialHeading(),
					result.getName(), messages.getFailedEOS());
		}
	}

	public List<I_TrialResult> getFailedTrials() {
		return failedTrials;
	}

}
