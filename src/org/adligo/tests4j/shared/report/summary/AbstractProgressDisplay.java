package org.adligo.tests4j.shared.report.summary;

import java.text.DecimalFormat;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public abstract class AbstractProgressDisplay {
	private I_Tests4J_Log logger;
	
	protected AbstractProgressDisplay(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}
	
	public void onProgress(String process, double pctComplete) {
		if (logger.isLogEnabled(this.getClass())) {
			if (pctComplete >= 100.0) {
				I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
				logger.log(I_Tests4J_Constants.PREFIX_HEADER +  process + messages.getDoneEOS());
			} else {
				DecimalFormat formatter = new DecimalFormat("###.##");
				I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
				logger.log(I_Tests4J_Constants.PREFIX_HEADER +  process + " " + 
						formatter.format(pctComplete) + messages.getPctComplete());
			}
		}
	}

}
