package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TestDisplay {
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	public static final String PASSED = "Passed!";
	private I_Tests4J_Log logger;
	private TestFailedDisplay testsFailedDisplay;
	private ThreadDisplay threadDisplay;
	
	public TestDisplay(I_Tests4J_Log pLogger, ThreadDisplay td) {
		logger = pLogger;
		testsFailedDisplay = new TestFailedDisplay(pLogger);
		threadDisplay = td;
	}
	
	public void onStartingTest(String trialName, String testName) {
		if (logger.isLogEnabled(TestDisplay.class)) {
			I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
			
			String thread = "";
			if (threadDisplay.isOn()) {
				thread = logger.getLineSeperator() +  threadDisplay.getThreadInfo();
			}
			logger.log(I_Tests4J_Constants.PREFIX + 
					messages.getStartingTest() + trialName + "." + testName + thread);
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
