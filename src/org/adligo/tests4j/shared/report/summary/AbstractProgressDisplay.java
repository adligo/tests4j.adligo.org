package org.adligo.tests4j.shared.report.summary;

import java.text.DecimalFormat;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

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
				DecimalFormat formatter = new DecimalFormat("###.##");
				I_Tests4J_ReportMessages messages = Tests4J_Constants.CONSTANTS.getReportMessages();
				log.log(I_Tests4J_Constants.PREFIX_HEADER +  process + " " + 
						formatter.format(pctComplete) + messages.getPctComplete());
			}
		}
	}

}
