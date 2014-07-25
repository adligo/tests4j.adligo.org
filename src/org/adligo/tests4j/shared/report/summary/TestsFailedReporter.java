package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public class TestsFailedReporter {
	public static final String TEST = "Test: ";
	public static final String FAILED = " failed!";
	private I_Tests4J_Log logger;
	
	public TestsFailedReporter(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}
	
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		if (logger.isLogEnabled(TestsFailedReporter.class)) {
			logger.log(TEST + trialName + "." + testName + FAILED);
		}
	}
}
