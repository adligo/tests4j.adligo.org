package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public class TestsReporter {
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	public static final String PASSED = "Passed!";
	private I_Tests4J_Log logger;
	
	public TestsReporter(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}
	
	public void onStartingTest(String trialName, String testName) {
		if (logger.isLogEnabled(TestsReporter.class)) {
			logger.log("startingTest: " + trialName + "." + testName);
		}
	}
	
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		if (logger.isLogEnabled(TestsReporter.class)) {
			String passedString = PASSED;
			if (!passed) {
				passedString = FAILED;
			}
			logger.log(TEST + trialName + "." + testName + passedString);
		}
	}
}
