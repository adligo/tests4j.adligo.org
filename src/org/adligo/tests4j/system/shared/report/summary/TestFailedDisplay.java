package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TestFailedDisplay {
  private final I_Tests4J_Constants constants_;
	private final I_Tests4J_Log log_;
	
	public TestFailedDisplay(I_Tests4J_Constants constants, I_Tests4J_Log log) {
	  constants_ = constants;
		log_ = log;
	}
	
	public void onTestFailed(String trialName, String testName,
			boolean passed) {
		if (log_.isLogEnabled(TestFailedDisplay.class)) {
			I_Tests4J_ReportMessages messages = constants_.getReportMessages();
			log_.log(StringMethods.orderLine(constants_.isLeftToRight(),
			    constants_.getPrefix(), 
					messages.getTestHeading(), trialName, ".", testName, messages.getFailedEOS()));
		}
	}
}
