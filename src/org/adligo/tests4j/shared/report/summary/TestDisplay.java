package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class TestDisplay {
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	public static final String PASSED = "Passed!";
	private I_Tests4J_Log logger;
	private TestFailedDisplay testsFailedDisplay;
	
	public TestDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
		testsFailedDisplay = new TestFailedDisplay(pLogger);
	}
	
	public void onStartingTest(String trialName, String testName) {
		if (logger.isLogEnabled(TestDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(I_Tests4J_Constants.PREFIX + 
					messages.getStartingTest() + trialName + "." + testName);
		}
	}
	
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		
		if (!passed) {
			testsFailedDisplay.onTestFailed(trialName, testName, passed);
		} else if (logger.isLogEnabled(TestDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(
					I_Tests4J_Constants.PREFIX + messages.getTestHeading() + 
					trialName + "." + testName + messages.getPassedEOS());
		}
	}
}
