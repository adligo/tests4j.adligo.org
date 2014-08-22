package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_ProcessInfo;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

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
	
	private boolean loggedThisPercent = false;
	private List<String> lastTrials;
	private boolean loggedTheseTrials = false;
	
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
				if (!loggedThisPercent) {
					if (!loggedTheseTrials) {
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
						loggedTheseTrials = true;
					}
				}
			}
		}
	}

	protected String addCurrentRunningInfoToStalledProcess(I_Tests4J_Log log,
			I_Tests4J_ProcessInfo processInfo, String message) {
		StringBuilder sb = new StringBuilder();
		if (timesAtPct >= 5) {
			loggedThisPercent = true;
			
			sb.append(message);
			List<String> trials =  processInfo.getTrials();
			for (String trial: trials) {
				sb.append(log.getLineSeperator());
				sb.append(trial);
			}
			if (lastTrials == null) {
				lastTrials = trials;
			} else {
				if (lastTrials.containsAll(trials)) {
					loggedTheseTrials = true;
				}
				lastTrials = trials;
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
			loggedThisPercent = false;
		}
	}
	
	public void onProgress(I_Tests4J_Log log, I_Tests4J_ProcessInfo processInfo) {
		//check the child class logEnable
		if (log.isLogEnabled(this.getClass())) {
			getTimesAtThisPct(processInfo);
			if (!loggedThisPercent) {
				I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
				String message = "";
				if (processInfo.getPercentDone() >= 100.0) {
					message =  messages.getDoneEOS() +log.getLineSeperator();
				} else {
					message = PercentFormat.format(processInfo.getPercentDone(), 2) + messages.getPctComplete();
				}
				message = I_Tests4J_Constants.PREFIX_HEADER +processInfo.getProcessName() + " " +
						addCurrentRunningInfoToStalledProcess(log,processInfo, message);
				log.log( message);
			}
		}
	}
}
