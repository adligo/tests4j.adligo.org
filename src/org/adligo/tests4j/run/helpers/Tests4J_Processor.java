package org.adligo.tests4j.run.helpers;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.adligo.tests4j.models.dependency_groups.gwt.GWT_Classes;
import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.common.JavaAPIVersion;
import org.adligo.tests4j.models.shared.common.LegacyAPI_Issues;
import org.adligo.tests4j.models.shared.common.SystemWithPrintStreamDelegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_Tests4J_ProgressMonitor;
import org.adligo.tests4j.models.shared.system.Tests4J_DelegateProgressMonitor;
import org.adligo.tests4j.run.Tests4J_UncaughtExceptionHandler;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
import org.adligo.tests4j.run.discovery.TrialQueueDecisionTree;
import org.adligo.tests4j.run.output.ConcurrentOutputDelegateor;
import org.adligo.tests4j.run.output.JsePrintOutputStream;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.DelegatingLog;
import org.adligo.tests4j.shared.output.I_ConcurrentOutputDelegator;
import org.adligo.tests4j.shared.output.I_OutputBuffer;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.shared.output.ListDelegateOutputBuffer;
import org.adligo.tests4j.shared.output.PrintStreamOutputBuffer;
import org.adligo.tests4j.shared.output.SafeOutputStremBuffer;
import org.adligo.tests4j.shared.report.summary.SummaryReporter;

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
	private Tests4J_Memory memory;
	private I_Tests4J_Log log;
	private Tests4J_DelegateProgressMonitor progressMonitor;
	private long nextSetupProgresss;
	private long nextTrialsProgresss;
	private long nextRemotesProgresss;
	
	private final I_System system;
	private Tests4J_ParamsReader reader;
	private I_Tests4J_ThreadManager threadManager;
	private I_Tests4J_NotificationManager notifier;
	
	private Tests4J_Controls controls;
	private ConcurrentOutputDelegateor cod;
	private TrialQueueDecisionTree trialQueueDecisionTree;
	
	public Tests4J_Processor(I_System systemIn) {
		system = systemIn;
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
		

		
		reader = new Tests4J_ParamsReader(system,  pParams);
		/**
		 * note this code is a bit confusing,
		 * the log is single threaded for a short time,
		 * and then is 
		 */
		log = reader.getLogger();
		displayJavaVerionErrors();
		/**
		 * now the log is concurrent
		 */
		Map<Class<?>, Boolean> logStates = reader.getLogStates();
		cod = setupLogging(logStates);
		//pass the concurrent log back to the reader
		//to set up the CoveragePluginFactory
		reader.read(log);
		
		memory = new Tests4J_Memory(log);
		memory.setSystem(system);
		progressMonitor = new Tests4J_DelegateProgressMonitor(log, pParams.getProgressMonitor());
		
		List<OutputStream> outs =  pParams.getAdditionalReportOutputStreams();
		if (outs.size() >= 1) {
			List<I_OutputBuffer> outputBuffers = new ArrayList<I_OutputBuffer>();
			for (OutputStream ob: outs) {
				outputBuffers.add(new SafeOutputStremBuffer(log, ob));
			}
			outputBuffers.add(new PrintStreamOutputBuffer(system.getOut()));
			PrintStream out = new  JsePrintOutputStream(new ListDelegateOutputBuffer(outputBuffers));
			
			SystemWithPrintStreamDelegate sysPs = new SystemWithPrintStreamDelegate(system, out);
			log = new DefaultLog(sysPs, pParams.getLogStates());
		}
		
		//use the dynamic log
		memory.setReporter(new SummaryReporter(log));
		
		memory.setListener(pListener);
		
		if (reader.isRunnable()) {
			memory.initialize(reader);
			threadManager = memory.getThreadManager();
		}
		controls = new Tests4J_Controls(memory);
		return reader.isRunnable();
	}	
	

	private void displayJavaVerionErrors() {
		LegacyAPI_Issues issues = new LegacyAPI_Issues();
		issues.addIssues(CachedClassBytesClassLoader.ISSUES);
		issues.addIssues(GWT_Classes.ISSUES);
		String version = system.getJseVersion();
		JavaAPIVersion v = new JavaAPIVersion(version);
		if (issues.hasIssues()) {
			List<Throwable> thrown = issues.getIssues(v);
			if (thrown != null) {
				log.onThrowable(new IllegalStateException("tests4j detects java " + version +
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
		ExecutorService service = threadManager.getTests4jService();
		service.submit(this);
	}
	
	public void run() {
		Thread.setDefaultUncaughtExceptionHandler(new Tests4J_UncaughtExceptionHandler());
		memory.setSystem(system);
		long time = system.getTime();
		memory.setStartTime(time);
		
		
		notifier = memory.getNotifier();
		
		
		Tests4J_ProcessInfo setupProcessInfo = memory.getSetupProcessInfo();
		Tests4J_ProcessInfo trialProcessInfo = memory.getTrialProcessInfo();
		//Tests4J_ProcessInfo remoteProcessInfo = memory.getTrialProcessInfo();
		try {
			startRecordingAllTrialsRun();
			
			submitSetupRunnables(setupProcessInfo);
			notifier.onSetupDone();
			
			submitTrialsRunnables(trialProcessInfo);
			//@diagram_sync with Overview.seq on 8/20/2014
			if (isTrialsOrRemotesRunning()) {
				monitorTrials();
				monitorRemotes();
			}
			
			notifier.onDoneRunningNonMetaTrials();
			
			String next = cod.poll();
			while (next != null) {
				system.println(next);
				next = cod.poll();
			}
		} catch (Throwable t) {
			log.onThrowable(t);
			//print the throwable
			String next = cod.poll();
			while (next != null) {
				system.println(next);
				next = cod.poll();
			}
		}
		
		controls.notifyFinished();
		//should call system.exit in main runs
		threadManager.shutdown();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException x) {
			log.onThrowable(x);
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
		log = new DelegatingLog(system, logStates, cod);
		return cod;
	}
	
	/**
	 * @adligo.diagram_sync with Overview.seq on 7/5/2014
	 * @param plugin
	 */
	private void startRecordingAllTrialsRun() {
		I_Tests4J_CoveragePlugin coveragePlugin = memory.getCoveragePlugin();
		if (coveragePlugin != null) {
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			I_Tests4J_CoverageRecorder allCoverageRecorder = coveragePlugin.createRecorder();
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			allCoverageRecorder.startRecording();
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			memory.setMainRecorder(allCoverageRecorder);
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
	private void submitSetupRunnables(Tests4J_ProcessInfo info) {
		notifier.onProcessStateChange(info);
		int setupThreadCount = info.getThreadCount();
		trialQueueDecisionTree = memory.getTrialQueueDecisionTree();
		
		if (log.isLogEnabled(Tests4J_Processor.class)) {
			log.log(this.toString() + " Starting setup with " + setupThreadCount + " setup threads.");
		}
		ExecutorService runService = threadManager.getSetupService();
		
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
	protected void runSetupRunnable(Tests4J_ProcessInfo info,
			ExecutorService runService) {
		Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory, notifier); 
		info.addRunnable(sr);
		
		try {
			Future<?> future = runService.submit(sr);
			threadManager.addSetupFuture(future);
		} catch (RejectedExecutionException x) {
			//do nothing, not sure why this exception is happening for me
			// it must have to do with shutdown, but it happens intermittently.
		}
	}
	
	private void monitorSetup() {
		Tests4J_ProcessInfo setupProcessInfo = memory.getSetupProcessInfo();
		
		boolean sentSetup100 = false;
		boolean sentSetup0 = false;
		nextSetupProgresss = progressMonitor.getNextNotifyTime(I_Tests4J_ProgressMonitor.SETUP);
		while (!trialQueueDecisionTree.isFull()) {
			try {
				
				Thread.sleep(progressMonitor.getSleepTime());
				if (!sentSetup100) {
					if (system.getTime() > nextSetupProgresss) {
					
						if (setupProcessInfo.getPercentDone() >= 100.0) {
							sentSetup100 = true;
						} else if (setupProcessInfo.getPercentDone() == 0.0) {
							if (!sentSetup0) {
								sentSetup0 = true;
								notifier.onProgress(setupProcessInfo);
								nextSetupProgresss = progressMonitor.getNextNotifyTime(I_Tests4J_ProgressMonitor.SETUP);
							}
						} else {
							notifier.onProgress(setupProcessInfo);
							nextSetupProgresss = progressMonitor.getNextNotifyTime(I_Tests4J_ProgressMonitor.SETUP);
						}
					}
				}
				String next = cod.poll();
				while (next != null) {
					system.println(next);
					next = cod.poll();
				}
			} catch (InterruptedException x) {
				throw new RuntimeException(x);
			}
		}
		notifier.onProgress(setupProcessInfo);

		long now = memory.getTime();
		memory.setSetupEndTime(now);
		if (log.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory.getStartTime();
			double secs = millis/1000.0;
			log.log(this.toString() + " Competing setup with after " + secs + " seconds.");
		}
		String next = cod.poll();
		while (next != null) {
			system.println(next);
			next = cod.poll();
		}
	}
	
	private void submitTrialsRunnables(Tests4J_ProcessInfo info) {
		
		
		notifier.onProcessStateChange(info);
		int trialThreadCount = info.getThreadCount();
		
		if (log.isLogEnabled(Tests4J_Processor.class)) {
			log.log("Starting submitTrialRunnables with " + trialThreadCount + " trial threads.");
		}
		ExecutorService runService = threadManager.getTrialRunService();
		
		if (trialThreadCount > 1) {
			for (int i = 0; i < trialThreadCount; i++) {
				runTrialRunnable(info, cod, runService);
			}
		} else {
			runTrialRunnable(info, cod, runService);
		}
	}
	protected void runTrialRunnable(Tests4J_ProcessInfo info,
			I_ConcurrentOutputDelegator od, ExecutorService runService) {
		Tests4J_TrialsRunnable tip = new Tests4J_TrialsRunnable(memory, notifier); 
		info.addRunnable(tip);
		tip.setOutputDelegator(od);
		try {
			Future<?> future = runService.submit(tip);
			threadManager.addTrialFuture(future);
		} catch (RejectedExecutionException x) {
			//do nothing, not sure why this exception is happening for me
			// it must have to do with shutdown, but it happens intermittently.
		}
	}
	
	private void monitorTrials() {
		Tests4J_ProcessInfo trialProcessInfo = memory.getTrialProcessInfo();
		
		nextTrialsProgresss = progressMonitor.getNextNotifyTime(I_Tests4J_ProgressMonitor.TRIALS);
		while (!trialQueueDecisionTree.areAllTrialsFinished()) {
			try {
				Thread.sleep(progressMonitor.getSleepTime());
				if (system.getTime() > nextTrialsProgresss) {
					notifier.onProgress(trialProcessInfo);
					nextTrialsProgresss = progressMonitor.getNextNotifyTime(I_Tests4J_ProgressMonitor.TRIALS);
				}
				
				String next = cod.poll();
				while (next != null) {
					system.println(next);
					next = cod.poll();
				}
			} catch (InterruptedException x) {
				throw new RuntimeException(x);
			}
		}
		notifier.onProgress(trialProcessInfo);
		long now = system.getTime();
		memory.setTrialEndTime(now);
		if (log.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory.getSetupEndTime();
			double secs = millis/1000.0;
			log.log(this.toString() + " Competing trials with after " + secs + " seconds.");
		}
		String next = cod.poll();
		while (next != null) {
			system.println(next);
			next = cod.poll();
		}
	}
	
	private boolean isTrialsOrRemotesRunning() {
		if (!notifier.isDoneRunningTrials()) {
			return true;
		}
		//todo add remotes
		return false;
	}
	/**
	 * TODO
	 * @param cod
	 */
	private void monitorRemotes() {
		
	}
	

	@Override
	public I_Tests4J_Controls getControls() {
		return controls;
	}

}
