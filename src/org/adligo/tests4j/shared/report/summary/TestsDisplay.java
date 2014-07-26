package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class TestsDisplay {
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	public static final String PASSED = "Passed!";
	private I_Tests4J_Log logger;
	private TestsFailedDisplay testsFailedDisplay;
	
	public TestsDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
		testsFailedDisplay = new TestsFailedDisplay(pLogger);
	}
	
	public void onStartingTest(String trialName, String testName) {
		if (logger.isLogEnabled(TestsDisplay.class)) {
			logger.log("startingTest: " + trialName + "." + testName);
		}
	}
	
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		
		if (!passed) {
			testsFailedDisplay.onTestCompleted(trialName, testName, passed);
		} else if (logger.isLogEnabled(TestsDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			logger.log(messages.getTestHeading() + trialName + "." + testName + messages.getPassedEOS());
		}
	}
}
