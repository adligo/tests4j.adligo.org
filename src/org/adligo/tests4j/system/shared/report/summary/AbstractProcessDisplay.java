package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;
import org.adligo.tests4j.system.shared.trials.I_Progress;

import java.util.ArrayList;
import java.util.List;

/**
 * Descendants of this class are simply there for the 
 * ClassName, so that logging can be turned off for different 
 * parts of the  very basic I_Tests4J_Log.
 * @author scott
 *
 */
public abstract class AbstractProcessDisplay {
  private final I_Tests4J_Constants constants_;
	private String pct = "";
	private int timesAtPct;
	
	protected AbstractProcessDisplay(I_Tests4J_Constants constants) {
	  constants_ = constants;
	}
	
	public void onProccessStateChange(I_Tests4J_Log log, I_PhaseState processInfo) {
		if (log.isLogEnabled(this.getClass())) {
			I_Tests4J_ReportMessages messages =  constants_.getReportMessages();
			int started = processInfo.getRunnablesStarted();
			if (started == 0) {
				String message = messages.getStartingProcessXWithYThreads();
				message = message.replace("<X/>",processInfo.getProcessName());
				message = message.replace("<Y/>", "" +processInfo.getThreadCount());
				log.logLine(constants_.getHeader(), message);
			} else {
				getTimesAtThisPct(processInfo);
				String message = messages.getProcessVhasXRunnablesRunningAndYZdone();
				message = message.replace("<V/>",processInfo.getProcessName());
				String runnables = "" + (processInfo.getRunnablesStarted() -
						processInfo.getRunnablesFinished()) + 
						"/" + processInfo.getRunnablesStarted();
				message = message.replace("<X/>", runnables);
				
				String doneString = "" + processInfo.getDoneCount() + "/" +
						processInfo.getCount();
				message = message.replace("<Y/>", doneString);
				String z = messages.getNonMetaTrials();
				if (I_PhaseState.SETUP.equals(processInfo.getProcessName())) {
					z = messages.getTrialDescriptionsInStatement();
				}
				message = message.replace("<Z/>", z);
				message = addCurrentRunningInfoToStalledProcess(log,
						processInfo, message);
				log.logLine(constants_.getHeader(), message);
			}
		}
	}

	protected String addCurrentRunningInfoToStalledProcess(I_Tests4J_Log log,
			I_PhaseState processInfo, String message) {
	  
	  StringBuilder sb = new StringBuilder();
	  sb.append(message);
		if (timesAtPct >= 2) {
		  I_Tests4J_ReportMessages messages =  constants_.getReportMessages();
			List<String> lines = new ArrayList<String>();
			lines.add(message);
			List<I_Tests4J_TrialProgress> states =  processInfo.getTrials();
			
			List<String> trials = new ArrayList<String>();
			for (I_Tests4J_TrialProgress state: states) {
			  List<String> line = new ArrayList<String>();
				String doneLine = state.getTrialName();
				line.add(doneLine);
				line.add(" ");
				trials.add(doneLine);
				
				I_Progress sub = state.getSubProgress();
				boolean subProgress = false;
				if (sub != null) {
					double pct = sub.getPctDone();
					if (pct > 0.0) {
					  line.add(".....");
					  line.add(sub.getName());
					  line.add(" ");
					  line.add(PercentFormat.format(sub.getPctDone(), 2));
					  line.add(messages.getPctComplete());
					  subProgress = true;
					} 
				} 
				
				if (!subProgress){
				  line.add(" ");
          line.add(PercentFormat.format(state.getPctDone(), 2));
          line.add(messages.getPctComplete());
				}
				String [] lineParts = line.toArray(new String[line.size()]);
				String lineMessage = StringMethods.orderLine(constants_.isLeftToRight(), lineParts);
				sb.append(System.lineSeparator());
				sb.append(lineMessage);
			}
		} 
		return sb.toString();
	}

	protected void getTimesAtThisPct(I_PhaseState processInfo) {
		double newPct = processInfo.getPercentDone();
		String npString = PercentFormat.format(newPct, 2);
		if (pct.equals(npString)) {
			timesAtPct++;
		} else {
			timesAtPct = 0;
			pct = npString;
		}
	}
	
	public void onProgress(I_Tests4J_Log log, I_PhaseState processInfo) {
		//check the child class logEnable
		if (log.isLogEnabled(this.getClass())) {
			getTimesAtThisPct(processInfo);
			I_Tests4J_ReportMessages messages =  constants_.getReportMessages();
			String message = "";
			if (processInfo.getPercentDone() >= 100.0) {
				if (processInfo.hasFinishedAll()) {
					message =  messages.getDoneEOS();
				}
			} else {
				message = StringMethods.orderLine(constants_.isLeftToRight(),
				    PercentFormat.format(processInfo.getPercentDone(), 2), messages.getPctComplete());
			}
			String stalled = addCurrentRunningInfoToStalledProcess(log,processInfo, message);
			if ( !StringMethods.isEmpty(stalled)) {
				log.logLine(constants_.getHeader(), processInfo.getProcessName(), " ", stalled);
			}
		}
	}
}
