package org.adligo.tests4j.system.shared.report.summary;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_ProcessInfo;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;
import org.adligo.tests4j.system.shared.trials.I_Progress;

/**
 * Descendants of this class are simply there for the 
 * ClassName, so that logging can be turned off for different 
 * parts of the  very basic I_Tests4J_Log.
 * @author scott
 *
 */
public abstract class AbstractProcessDisplay {
	private String pct = "";
	private int timesAtPct;
	
	
	public void onProccessStateChange(I_Tests4J_Log log, I_Tests4J_ProcessInfo processInfo) {
		if (log.isLogEnabled(this.getClass())) {
			I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
			int started = processInfo.getRunnablesStarted();
			if (started == 0) {
				String message = messages.getStartingProcessXWithYThreads();
				message = message.replace("<X/>",processInfo.getProcessName());
				message = message.replace("<Y/>", "" +processInfo.getThreadCount());
				log.log(I_Tests4J_Constants.PREFIX_HEADER + message);
			} else {
				getTimesAtThisPct(processInfo);
				String message = messages.getProcessVhasXRunnablesRunningAndYZdone();
				message = message.replace("<V/>",processInfo.getProcessName());
				String runnables = "" + (processInfo.getRunnablesStarted() -
						processInfo.getRunnablesFinished()) + 
						"/" + processInfo.getRunnablesStarted();
				message = message.replace("<X/>", runnables);
				
				String doneString = "" + processInfo.getDone() + "/" +
						processInfo.getCount();
				message = message.replace("<Y/>", doneString);
				String z = messages.getNonMetaTrials();
				if (I_Tests4J_ProcessInfo.SETUP.equals(processInfo.getProcessName())) {
					z = messages.getTrialDescriptionsInStatement();
				}
				message = message.replace("<Z/>", z);
				message = addCurrentRunningInfoToStalledProcess(log,
						processInfo, message);
				log.log(I_Tests4J_Constants.PREFIX_HEADER + message);
			}
		}
	}

	protected String addCurrentRunningInfoToStalledProcess(I_Tests4J_Log log,
			I_Tests4J_ProcessInfo processInfo, String message) {
		StringBuilder sb = new StringBuilder();
		if (timesAtPct >= 2) {
			
			sb.append(message);
			List<I_Tests4J_TrialProgress> states =  processInfo.getTrials();
			
			List<String> trials = new ArrayList<String>();
			for (I_Tests4J_TrialProgress state: states) {
				sb.append(log.getLineSeperator());
				String doneLine = state.getTrialName();
				sb.append(doneLine);
				trials.add(doneLine);
				
				I_Progress sub = state.getSubProgress();
				String subProgress = null;
				if (sub != null) {
					double pct = sub.getPctDone();
					if (pct > 0.0) {
						subProgress = "....." + sub.getName() + " " + 
								PercentFormat.format(sub.getPctDone(), 2) + "%.";
						sb.append(subProgress);
					} 
				} 
				
				if (subProgress == null){
					String line = " " + PercentFormat.format(state.getPctDone(), 2) + "%.";
					sb.append(line);
				}
			}
			sb.append(log.getLineSeperator());
			message = sb.toString();
		} 
		return message;
	}

	protected void getTimesAtThisPct(I_Tests4J_ProcessInfo processInfo) {
		double newPct = processInfo.getPercentDone();
		String npString = PercentFormat.format(newPct, 2);
		if (pct.equals(npString)) {
			timesAtPct++;
		} else {
			timesAtPct = 0;
			pct = npString;
		}
	}
	
	public void onProgress(I_Tests4J_Log log, I_Tests4J_ProcessInfo processInfo) {
		//check the child class logEnable
		if (log.isLogEnabled(this.getClass())) {
			getTimesAtThisPct(processInfo);
			I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
			String message = "";
			if (processInfo.getPercentDone() >= 100.0) {
				if (processInfo.hasFinishedAll()) {
					message =  messages.getDoneEOS() +log.getLineSeperator();
				}
			} else {
				message = PercentFormat.format(processInfo.getPercentDone(), 2) + messages.getPctComplete();
			}
			String stalled = addCurrentRunningInfoToStalledProcess(log,processInfo, message);
			if ( !StringMethods.isEmpty(stalled)) {
				message = I_Tests4J_Constants.PREFIX_HEADER +processInfo.getProcessName() + " " + stalled;
				log.log( message);
			}
		}
	}
}
