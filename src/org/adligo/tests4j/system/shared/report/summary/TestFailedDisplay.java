package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TestFailedDisplay {
  private static final I_Tests4J_Constants CONSTANTS = Tests4J_Constants.CONSTANTS;
	private I_Tests4J_Log logger;
	
	public TestFailedDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}
	
	public void onTestFailed(String trialName, String testName,
			boolean passed) {
		if (logger.isLogEnabled(TestFailedDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(DefaultLog.orderLine(CONSTANTS.getPrefix(), 
					messages.getTestHeading(), trialName, ".", testName, messages.getFailedEOS()));
		}
	}
}
