package org.adligo.tests4j.shared.report.summary;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.asserts.ContainsAssertCommand;
import org.adligo.tests4j.models.shared.asserts.StringCompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

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
