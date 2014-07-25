package org.adligo.tests4j.shared.report.summary;

import java.text.DecimalFormat;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public abstract class AbstractProgressReporter {
	private I_Tests4J_Log logger;
	
	protected AbstractProgressReporter(I_Tests4J_Log pLogger) {
		logger = pLogger;
	}
	
	public void onProgress(String process, double pctComplete) {
		if (logger.isLogEnabled(this.getClass())) {
			if (pctComplete >= 100.0) {
				logger.log(process + " 100% done.");
			} else {
				DecimalFormat formatter = new DecimalFormat("###.##");
				logger.log(process + " " + formatter.format(pctComplete) + "% complete...");
			}
		}
	}

}
