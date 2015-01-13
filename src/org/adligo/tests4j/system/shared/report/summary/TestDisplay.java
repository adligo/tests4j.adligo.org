package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TestDisplay {
  //TODO i18n
	public static final String TEST = "Test: ";
	public static final String FAILED = "Failed!";
	public static final String PASSED = "Passed!";
	private final I_Tests4J_Constants constants_;
	private final I_Tests4J_ReportMessages messages_;
	private I_Tests4J_Log logger;
	private TestFailedDisplay testsFailedDisplay;
	private ThreadDisplay threadDisplay;
	
	public TestDisplay(I_Tests4J_Constants constants, I_Tests4J_Log pLogger, ThreadDisplay td) {
	  constants_ = constants;
	  messages_ = constants_.getReportMessages();
		logger = pLogger;
		testsFailedDisplay = new TestFailedDisplay(constants, pLogger);
		threadDisplay = td;
	}
	
	public void onStartingTest(String trialName, String testName) {
		if (logger.isLogEnabled(TestDisplay.class)) {
			
			String thread = "";
			if (threadDisplay.isOn()) {
				thread = logger.getLineSeperator() +  threadDisplay.getThreadInfo();
			}
			logger.log(DefaultLog.orderLine(constants_.isLeftToRight(),
			    constants_.getPrefix(), messages_.getStartingTest(),
			    trialName, ".", testName) + thread);
		}
	}
	
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		
		if (!passed) {
			testsFailedDisplay.onTestFailed(trialName, testName, passed);
		} else if (logger.isLogEnabled(TestDisplay.class)) {
			logger.log(
			    DefaultLog.orderLine(constants_.isLeftToRight(),
			        constants_.getPrefix(), messages_.getTestHeading(),  
			        trialName, ".", testName, messages_.getPassedEOS()));
		}
	}
}
