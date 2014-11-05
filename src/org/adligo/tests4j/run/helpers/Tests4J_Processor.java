package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.run.api.Tests4J_UncaughtExceptionHandler;
import org.adligo.tests4j.run.common.I_ThreadManager;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
import org.adligo.tests4j.run.memory.Tests4J_Memory;
import org.adligo.tests4j.run.output.ConcurrentOutputDelegateor;
import org.adligo.tests4j.run.output.JsePrintOutputStream;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.JavaAPIVersion;
import org.adligo.tests4j.shared.common.LegacyAPI_Issues;
import org.adligo.tests4j.shared.common.SystemWithPrintStreamDelegate;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.DelegatingLog;
import org.adligo.tests4j.shared.output.I_ConcurrentOutputDelegator;
import org.adligo.tests4j.shared.output.I_OutputBuffer;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.shared.output.ListDelegateOutputBuffer;
import org.adligo.tests4j.shared.output.PrintStreamOutputBuffer;
import org.adligo.tests4j.shared.output.SafeOutputStreamBuffer;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Controls;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Delegate;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Listener;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Params;
import org.adligo.tests4j.system.shared.report.summary.SummaryReporter;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * ok this is the main processing class which does this;
 * 1) put all the test classes in memory
 * 2) start the thread pool
 * 
 * @author scott
 *
 */
public class Tests4J_Processor implements I_Tests4J_Delegate, Runnable {
	public static final String REQUIRES_SETUP_IS_CALLED_BEFORE_RUN = " requires setup is called before run.";
	private Tests4J_Memory memory_;
	private I_Tests4J_Log log_;
	
	private final I_System system_;
	private Tests4J_ParamsReader reader_;
	private I_ThreadManager threadManager_;
	private I_Tests4J_NotificationManager notifier_;
	
	private Tests4J_Controls controls_;
	private ConcurrentOutputDelegateor cod_;
	
	public Tests4J_Processor(I_System systemIn) {
		system_ = systemIn;
	}
	/**
	 * This method sets up everything for the run,
	 * assumes all other setters have been called.
	 *  
	 * @param pListener
	 * @param pParams
	 * 
	 */
	public boolean setup(I_Tests4J_Listener pListener, I_Tests4J_Params pParams) {
		

		
		reader_ = new Tests4J_ParamsReader(system_,  pParams);
		/**
		 * note this code is a bit confusing,
		 * the log is single threaded for a short time,
		 * and then is 
		 */
		log_ = reader_.getLogger();
		displayJavaVerionErrors();
		/**
		 * now the log is concurrent
		 */
		Map<Class<?>, Boolean> logStates = reader_.getLogStates();
		cod_ = setupLogging(logStates);
		//pass the concurrent log back to the reader
		//to set up the CoveragePluginFactory
		reader_.read(log_);
		
		memory_ = new Tests4J_Memory(log_);
		memory_.setSystem(system_);
		
		List<OutputStream> outs =  pParams.getAdditionalReportOutputStreams();
		if (outs.size() >= 1) {
			List<I_OutputBuffer> outputBuffers = new ArrayList<I_OutputBuffer>();
			for (OutputStream ob: outs) {
				outputBuffers.add(new SafeOutputStreamBuffer(log_, ob));
			}
			outputBuffers.add(new PrintStreamOutputBuffer(system_.getOut()));
			PrintStream out = new  JsePrintOutputStream(new ListDelegateOutputBuffer(outputBuffers));
			
			SystemWithPrintStreamDelegate sysPs = new SystemWithPrintStreamDelegate(system_, out);
			log_ = new DefaultLog(sysPs, pParams.getLogStates());
		}
		
		//use the dynamic log
		memory_.setReporter(new SummaryReporter(log_));
		
		memory_.setListener(pListener);
		
		if (reader_.isRunnable()) {
			memory_.initialize(reader_);
			threadManager_ = memory_.getThreadManager();
		}
		controls_ = new Tests4J_Controls(memory_);
		return reader_.isRunnable();
	}	
	

	private void displayJavaVerionErrors() {
		LegacyAPI_Issues issues = new LegacyAPI_Issues();
		issues.addIssues(CachedClassBytesClassLoader.ISSUES);
		issues.addIssues(GWT_Classes.ISSUES);
		String version = system_.getJseVersion();
		JavaAPIVersion v = new JavaAPIVersion(version);
		if (issues.hasIssues()) {
			List<Throwable> thrown = issues.getIssues(v);
			if (thrown != null) {
				log_.onThrowable(new IllegalStateException("tests4j detects java " + version +
						" which has " + thrown.size() + " potential issues."));
			}
		}
	}
	/**
	 * This method kicks off the TheadPool (ExecutorService)
	 * creating as many TrialInstancesProcessors
	 * as there are params.getThreadPoolSize().
	 *  
	 * @param params
	 * @param pListener
	 * 
	 * @diagram Overview.seq sync on 7/21/2014
	 * 
	 */
	public void runOnAnotherThreadIfAble() {
		ExecutorService service = threadManager_.getTests4jService();
		service.submit(this);
	}
	
	public void run() {
	  memory_.initalizeExecutors();
		Thread.setDefaultUncaughtExceptionHandler(new Tests4J_UncaughtExceptionHandler());
		memory_.setSystem(system_);
		long time = system_.getTime();
		memory_.setStartTime(time);
		
		
		notifier_ = memory_.getNotifier();
		
		
		Tests4J_PhaseOverseer setupProcessInfo = memory_.getSetupPhaseOverseer();
		Tests4J_PhaseOverseer trialProcessInfo = memory_.getTrialPhaseOverseer();
		//Tests4J_ProcessInfo remoteProcessInfo = memory.getTrialProcessInfo();
		try {
			startRecordingAllTrialsRun();
			
			submitSetupRunnables(setupProcessInfo);
			notifier_.onSetupDone();
			
			submitTrialsRunnables(trialProcessInfo);
			//@diagram_sync with Overview.seq on 8/20/2014
			if (isTrialsOrRemotesRunning()) {
				monitorTrials();
				monitorRemotes();
			}
			
			notifier_.onDoneRunningNonMetaTrials();
			
			printLogs();
		} catch (Throwable t) {
			log_.onThrowable(t);
			t.printStackTrace();
			printLogs();
		}
		
		controls_.notifyFinished();
		//should call system.exit in main runs
		threadManager_.shutdown();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException x) {
			log_.onThrowable(x);
		}
	}

	/**
	 * @param setupProcessInfo
	 * @param trialProcessInfo
	 * @param remoteProcessInfo
	 * @return null if single threaded
	 */
	private ConcurrentOutputDelegateor setupLogging(Map<Class<?>, Boolean> logStates) {
		ConcurrentOutputDelegateor cod = new ConcurrentOutputDelegateor();
		JsePrintOutputStream jpos = new JsePrintOutputStream(cod);
		System.setOut(jpos);
		System.setErr(jpos);
		log_ = new DelegatingLog(system_, logStates, cod);
		return cod;
	}
	
	/**
	 * @adligo.diagram_sync with Overview.seq on 7/5/2014
	 * @param plugin
	 */
	private void startRecordingAllTrialsRun() {
		I_Tests4J_CoveragePlugin coveragePlugin = memory_.getCoveragePlugin();
		if (coveragePlugin != null) {
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			I_Tests4J_CoverageRecorder allCoverageRecorder = coveragePlugin.createRecorder();
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			allCoverageRecorder.startRecording();
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			memory_.setMainRecorder(allCoverageRecorder);
		}
		
	}
	
	/**
	 * 
	 * @diagram_sync with Overview.seq on 7/24/2014
	 * 
	 * please keep these stats
	 *    7/24/2014  
	 *    		AllTrialsRunn with 75 trials 522 tests
	 *    
	 *    			12.5 seconds when one thread runs Tests4J_SetupRunnable
	 *                9.2 seconds with 8 threads
	 *                8.7 seconds with 16 threads
	 */
	private void submitSetupRunnables(Tests4J_PhaseOverseer info) {
	  I_PhaseState initial = info.getIntialPhaseState();
		notifier_.onProcessStateChange(info.getIntialPhaseState());
		int setupThreadCount = initial.getThreadCount();
		
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			log_.log(this.toString() + " Starting setup with " + setupThreadCount + " setup threads.");
		}
		ExecutorService runService = threadManager_.getSetupService();
		
		if (setupThreadCount > 1) {
			for (int i = 0; i < setupThreadCount; i++) {
				runSetupRunnable(info, runService);
			}
		} else {
			runSetupRunnable(info, runService);
		}
		//@diagram_sync with Overview.seq on 8/20/2014
		monitorSetup();

	}
	protected void runSetupRunnable(Tests4J_PhaseOverseer info,
			ExecutorService runService) {
		Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory_, notifier_); 
		info.addRunnable(sr);
		
		try {
			Future<?> future = runService.submit(sr);
			threadManager_.addSetupFuture(future);
		} catch (RejectedExecutionException x) {
			//do nothing, not sure why this exception is happening for me
			// it must have to do with shutdown, but it happens intermittently.
		}
	}
	
	@SuppressWarnings("boxing")
  private void monitorSetup() {
		Tests4J_PhaseOverseer setupPhaseOverseer = memory_.getSetupPhaseOverseer();
		
		I_PhaseState state = null;
		try {
		  state = setupPhaseOverseer.pollPhaseState();
  		while (!setupPhaseOverseer.isCountDoneCountMatch()) {
  		  notifier_.onProgress(state);
  		  printLogs();
  		  state = setupPhaseOverseer.pollPhaseState();
  		}
		} catch (InterruptedException x) {
		  throw new RuntimeException(x);
		}
		notifier_.onProgress(state);

		long now = memory_.getTime();
		memory_.setSetupEndTime(now);
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory_.getStartTime();
			double secs = millis/1000.0;
			log_.log(this.toString() + " Completed setup with after " + secs + " seconds.");
		}
		printLogs();
	}
	
	private void submitTrialsRunnables(Tests4J_PhaseOverseer info) {
		I_PhaseState initial = info.getIntialPhaseState();
		notifier_.onProcessStateChange(initial);
		int trialThreadCount = initial.getThreadCount();
		
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			log_.log("Starting submitTrialRunnables with " + trialThreadCount + " trial threads.");
		}
		ExecutorService runService = threadManager_.getTrialRunService();
		
		if (trialThreadCount > 1) {
			for (int i = 0; i < trialThreadCount; i++) {
				runTrialRunnable(info, cod_, runService);
			}
		} else {
			runTrialRunnable(info, cod_, runService);
		}
	}
	protected void runTrialRunnable(Tests4J_PhaseOverseer info,
			I_ConcurrentOutputDelegator od, ExecutorService runService) {
		Tests4J_TrialsRunnable tip = new Tests4J_TrialsRunnable(memory_, notifier_); 
		info.addRunnable(tip);
		tip.setOutputDelegator(od);
		try {
			Future<?> future = runService.submit(tip);
			threadManager_.addTrialFuture(future);
		} catch (RejectedExecutionException x) {
			//do nothing, not sure why this exception is happening for me
			// it must have to do with shutdown, but it happens intermittently.
		}
	}
	
	@SuppressWarnings("boxing")
  private void monitorTrials() {
		Tests4J_PhaseOverseer trialPhaseOverseer = memory_.getTrialPhaseOverseer();
		
		I_PhaseState state = null;
    try {
      state = trialPhaseOverseer.pollPhaseState();
      while (!trialPhaseOverseer.isCountDoneCountMatch()) {
        notifier_.onProgress(state);
        printLogs();
        state = trialPhaseOverseer.pollPhaseState();
      }
    } catch (InterruptedException x) {
      throw new RuntimeException(x);
    }
		notifier_.onProgress(state);
		long now = system_.getTime();
		memory_.setTrialEndTime(now);
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory_.getSetupEndTime();
			double secs = millis/1000.0;
			log_.log(this.toString() + " Competing trials with after " + secs + " seconds.");
		}
		String next = cod_.poll();
		while (next != null) {
			system_.println(next);
			next = cod_.poll();
		}
	}
	
	private boolean isTrialsOrRemotesRunning() {
		if (!notifier_.isDoneRunningTrials()) {
			return true;
		}
		//todo add remotes
		return false;
	}
	/**
	 * TODO
	 * @param cod_
	 */
	private void monitorRemotes() {
		
	}
	

	@Override
	public I_Tests4J_Controls getControls() {
		return controls_;
	}

	
	private void printLogs() {
	  String next = cod_.poll();
    while (next != null) {
      system_.println(next);
      next = cod_.poll();
    }
	}
}
