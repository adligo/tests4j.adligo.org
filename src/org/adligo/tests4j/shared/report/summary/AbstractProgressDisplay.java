package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public abstract class AbstractProgressDisplay {
	private I_Tests4J_Log log;
	
	protected AbstractProgressDisplay(I_Tests4J_Log pLogger) {
		log = pLogger;
	}
	
	public void onProgress(String process, double pctComplete) {
		if (log.isLogEnabled(this.getClass())) {
			if (pctComplete >= 100.0) {
				I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
				log.log(I_Tests4J_Constants.PREFIX_HEADER +  process + " " + messages.getDoneEOS()
						+log.getLineSeperator());
			} else {
				I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
				log.log(I_Tests4J_Constants.PREFIX_HEADER +  process + " " + 
						PercentFormat.format(pctComplete, 2) + messages.getPctComplete());
			}
		}
	}

}
