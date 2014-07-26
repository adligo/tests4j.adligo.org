package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class TestsFailedDisplay {
	private I_Tests4J_Log logger;
	
	public TestsFailedDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}
	
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		if (logger.isLogEnabled(TestsFailedDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(messages.getTestHeading() + trialName + "." + testName + messages.getFailedEOS());
		}
	}
}
