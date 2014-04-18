package org.adligo.tests4j.run;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.console.ConsoleLogger;
import org.adligo.tests4j.models.shared.system.console.TextReporter;

import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * this class handles event notification
 * @author scott
 *
 */
public class NotificationManager {
	private AtomicBoolean doneDescribeingTrials = new AtomicBoolean(false);
	private long start = System.currentTimeMillis();
	private Tests4J_Memory memory;
	private I_Tests4J_Logger log;
	private I_TrialRunListener listener;
	private TextReporter reporter;
	private TrialRunResultMutant runResult = new TrialRunResultMutant();
	private I_CoverageRecorder allCoverageRecorder;
	private int trialClassDeffinitionFailures = 0;
	private int trialsDone = 0;
	
	public NotificationManager(Tests4J_Memory pMem, I_Tests4J_Logger pLog, I_TrialRunListener pListener) {
		memory = pMem;
		log = pLog;
		listener = pListener;
		reporter = new TextReporter(log);
	}
	
	public void startRecordingRunCoverage() {
		I_CoveragePlugin plugin = memory.getPlugin();
		if (plugin != null) {
			I_CoverageRecorder allCoverageRecorder = plugin.createRecorder(I_CoverageRecorder.TRIAL_RUN);
			memory.addRecorder(I_CoverageRecorder.TRIAL_RUN, allCoverageRecorder);
			allCoverageRecorder.startRecording();
		}
		
	}
	
	public synchronized void checkDoneDescribingTrials() {
		if (doneDescribeingTrials.get()) {
			return;
		}
		int trialDescriptions = memory.getDescriptionCount();
		int trialCount = memory.getTrialCount();
		
		if (trialCount == trialDescriptions) {
			onTrialDefinitionsDone();
		}
	}

	private void onTrialDefinitionsDone() {
		if (log.isEnabled()) {
			logPrivate("DescribingTrials is Done.");
		}
		doneDescribeingTrials.set(true);
		if (memory.getFailureResultsSize() >= 1) {
			I_TrialResult result = memory.pollFailureResults();
			while (result != null) {
				trialDone(result);
				result = memory.pollFailureResults();
			}
		}
		
	}

	public synchronized void startingTrial(String name) {
		if (log.isEnabled()) {
			logPrivate("startingTrial " + name);
		}
	}
	
	public synchronized void startingTest(String name) {
		if (log.isEnabled()) {
			logPrivate("startingTest " + name);
		}
	}
	
	public synchronized void trialDone(I_TrialResult result) {
		if (log.isEnabled()) {
			reporter.printTestCompleted(result);
		}
		if (listener != null) {
			listener.onTrialCompleted(result);
		}
		trialsDone++;
	}
	
	public synchronized void checkDoneRunningTrials() {
		int trialsWhichCanRun = memory.getRunnableTrialDescriptions();
		
		if (trialsDone == trialsWhichCanRun) {
			if (log.isEnabled()) {
				logPrivate("DoneRunningTrials.");
			}
			if (allCoverageRecorder != null) {
				List<I_PackageCoverage> packageCoverage = allCoverageRecorder.getCoverage();
				runResult.setCoverage(packageCoverage);
			}
			long end = System.currentTimeMillis();
			runResult.setRunTime(end - runResult.getStartTime());
			
			ExecutorService runService =  memory.getRunService();
			runService.shutdownNow();
			if (memory.isSystemExit()) {
				System.exit(0);
			}
		}
	}
	
	public boolean isLogEnabled() {
		return log.isEnabled();
	}
	
	public synchronized void log(String p) {
		logPrivate(p);
	}
	
	private void logPrivate(String p) {
		log.log(p);
	}
}
