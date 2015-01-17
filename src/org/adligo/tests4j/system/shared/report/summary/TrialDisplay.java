package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

import java.util.List;

public class TrialDisplay {
  private final I_Tests4J_Constants constants_;
	private final I_Tests4J_Log logger_;
	private TrialFailedDisplay failuresReporter_;
	private ThreadDisplay theadDisplay_;
	
	public TrialDisplay(I_Tests4J_Constants constants, I_Tests4J_Log log, ThreadDisplay theadDisplay) {
	  constants_ = constants;
		logger_ = log;
		failuresReporter_ = new TrialFailedDisplay(constants, log);
		theadDisplay_ = theadDisplay;
	}
	
	public void onStartingTrial(String trialName) {
		if (logger_.isLogEnabled(TrialDisplay.class)) {
			I_Tests4J_ReportMessages messages =  constants_.getReportMessages();
			String message = StringMethods.orderLine(constants_.isLeftToRight(),
			    constants_.getPrefix(),  messages.getStartingTrial(), trialName); 
			if (theadDisplay_.isOn()) {
				message = StringMethods.orderLine(constants_.isLeftToRight(),
				    message, " ", theadDisplay_.getThreadInfo());
			}
			logger_.log(message);
		}
	}

	public void onTrialCompleted(I_TrialResult result) {
		if (!result.isPassed()) {
			failuresReporter_.onTrailFailed(result);
		} else {
			if (logger_.isLogEnabled(TrialDisplay.class)) {
				I_Tests4J_ReportMessages messages =  constants_.getReportMessages();
				
				String message = StringMethods.orderLine(constants_.isLeftToRight(),
				    constants_.getPrefix(),  messages.getTrialHeading(), result.getName(),
						messages.getPassedEOS());
				logger_.log(message);
			}
		}
	}

	public List<I_TrialResult> getFailedTrials() {
		return failuresReporter_.getFailedTrials();
	}

}
