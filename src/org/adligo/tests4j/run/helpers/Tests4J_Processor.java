package org.adligo.tests4j.run.helpers;

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
import org.adligo.tests4j.run.output.ConcurrentOutputDelegateor;
import org.adligo.tests4j.run.output.JsePrintOutputStream;
import org.adligo.tests4j.run.output.OutputDelegateor;
import org.adligo.tests4j.shared.output.DelegatingLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.shared.report.summary.SummaryReporter;

/**
 * ok this is the main processing class which does this;
 * 1) put all the test classes in memory
 * 2) start the thread pool
 * 
 * @author scott
 *
 */
public class Tests4J_Processor implements I_Tests4J_Delegate {
	public static final String REQUIRES_SETUP_IS_CALLED_BEFORE_RUN = " requires setup is called before run.";
	private final PrintStream out;
	private Tests4J_Memory memory = new Tests4J_Memory();
	private I_Tests4J_Log logger;
	
	private Tests4J_ParamsReader reader;
	private I_Tests4J_ThreadManager threadManager;
	private I_Tests4J_NotificationManager notifier;
	private Tests4J_Controls controls;
	
	public Tests4J_Processor(PrintStream pOut) {
		out = pOut;
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
		
		I_System system = memory.getSystem();
		reader = new Tests4J_ParamsReader(system,  pParams);
		
		I_Tests4J_Log logger = reader.getLogger();
		memory.setLogger(logger);
		
		memory.setListener(pListener);
		memory.setReporter(new SummaryReporter(logger));
		
		if (reader.isRunnable()) {
			memory.initialize(reader);
		}
		controls = new Tests4J_Controls(memory);
		return reader.isRunnable();
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
	@SuppressWarnings("unchecked")
	public void run() {
		
		logger = memory.getLogger();
		
		long time = memory.getTime();
		memory.setStartTime(time);
		
		threadManager = memory.getThreadManager();
		notifier = memory.getNotifier();
		submitSetupRunnables();
		
		
		
		//sleep this thread until it can start running trials
		if (memory.getTrialsToRun() >= 1) {
			startRecordingAllTrialsRun();
			
			submitTrialsRunnables();
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
	private void submitSetupRunnables() {
		//memory.getS
		int setupThreadCount = memory.getSetupThreadCount();
		
		if (logger.isLogEnabled(Tests4J_Processor.class)) {
			logger.log("Starting setup with " + setupThreadCount + " setup threads.");
		}
		if (setupThreadCount <= 1) {
			//run single threaded on the main thread.
			OutputDelegateor od = new OutputDelegateor(out);
			JsePrintOutputStream jpos = new JsePrintOutputStream(od);
			System.setOut(jpos);
			System.setErr(jpos);
			
			Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory, notifier); 
			sr.run();
		} else {
			ConcurrentOutputDelegateor cod = new ConcurrentOutputDelegateor();
			JsePrintOutputStream jpos = new JsePrintOutputStream(cod);
			System.setOut(jpos);
			System.setErr(jpos);
			logger = new DelegatingLog(memory.getSystem(), reader.getLogStates(), cod);
			memory.setLogger(logger);
			
			ExecutorService runService = threadManager.getTrialRunService();
			
			int allTrials = memory.getAllTrialCount();
			Tests4J_ProgressMonitor progressMonitor = new Tests4J_ProgressMonitor(memory, notifier, allTrials, "setup");
			progressMonitor.setNotifyProgress(true);
			progressMonitor.setTimeBetweenLongs(501);
			
			for (int i = 0; i < setupThreadCount; i++) {
				Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory, notifier); 
				sr.setNotifyProgress(false);
				
				try {
					Future<?> future = runService.submit(sr);
					threadManager.addSetupFuture(future);
				} catch (RejectedExecutionException x) {
					//do nothing, not sure why this exception is happening for me
					// it must have to do with shutdown, but it happens intermittently.
				}
			}
			
			while (!notifier.isDoneDescribeingTrials()) {
				try {
					Thread.sleep(500);
					long trialsSetup = memory.getTrialsSetup();
					progressMonitor.notifyIfProgressedTime(trialsSetup);
					
					String next = cod.poll();
					while (next != null) {
						out.println(next);
						next = cod.poll();
					}
				} catch (InterruptedException x) {
					throw new RuntimeException(x);
				}
			}
			progressMonitor.notifyDone();
		}
		long now = memory.getTime();
		memory.setSetupEndTime(now);
		if (logger.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory.getStartTime();
			double secs = millis/1000.0;
			logger.log("Finised setup after " + secs + " seconds.");
		}
	}
	
	private void submitTrialsRunnables() {
		int trialThreadCount = memory.getTrialThreadCount();
		
		if (logger.isLogEnabled(Tests4J_Processor.class)) {
			logger.log("Starting submitTrialRunnables with " + trialThreadCount + " trial threads.");
		}
		if (trialThreadCount <= 1) {
			OutputDelegateor od = new OutputDelegateor(out);
			JsePrintOutputStream jpos = new JsePrintOutputStream(od);
			System.setOut(jpos);
			System.setErr(jpos);
			I_Tests4J_Log logger = reader.getLogger();
			memory.setLogger(logger);
			
			Tests4J_TrialsRunable tip = new Tests4J_TrialsRunable(memory, notifier); 
			tip.setOutputDelegator(od);
			
			tip.run();
			long now = memory.getTime();
			memory.setSetupEndTime(now);
			if (logger.isLogEnabled(Tests4J_Processor.class)) {
				double millis = now - memory.getStartTime();
				double secs = millis/1000.0;
				logger.log("Finised trials after " + secs + " seconds.");
			}
		} else {
			ConcurrentOutputDelegateor cod = new ConcurrentOutputDelegateor();
			JsePrintOutputStream jpos = new JsePrintOutputStream(cod);
			System.setOut(jpos);
			System.setErr(jpos);
			logger = new DelegatingLog(memory.getSystem(), reader.getLogStates(), cod);
			memory.setLogger(logger);
			
			ExecutorService runService = threadManager.getTrialRunService();
			
			for (int i = 0; i < trialThreadCount; i++) {
				Tests4J_TrialsRunable tip = new Tests4J_TrialsRunable(memory, notifier); 
				tip.setOutputDelegator(cod);
				try {
					Future<?> future = runService.submit(tip);
					threadManager.addTrialFuture(future);
					memory.addTrialInstancesProcessors(tip);
				} catch (RejectedExecutionException x) {
					//do nothing, not sure why this exception is happening for me
					// it must have to do with shutdown, but it happens intermittently.
				}
			}
			
			int trials = memory.getAllTrialCount();
			Tests4J_ProgressMonitor  trialProgressMonitor = new Tests4J_ProgressMonitor(memory, notifier, trials, "trials");
			
			while (!notifier.isDoneRunningTrials()) {
				try {
					Thread.sleep(500);
					
					long trialsRan = memory.getTrialsDone();
					trialProgressMonitor.notifyIfProgressedTime(trialsRan);
					
					String next = cod.poll();
					while (next != null) {
						out.println(next);
						next = cod.poll();
					}
				} catch (InterruptedException x) {
					throw new RuntimeException(x);
				}
			}
		}
		
		
		
	}

	/**
	 * @adligo.diagram_sync with Overview.seq on 7/5/2014
	 * @param plugin
	 */
	private void startRecordingAllTrialsRun() {
		I_Tests4J_CoveragePlugin coveragePlugin = memory.getCoveragePlugin();
		if (coveragePlugin != null) {
			coveragePlugin.instrumentationComplete();
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			I_Tests4J_CoverageRecorder allCoverageRecorder = coveragePlugin.createRecorder();
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			allCoverageRecorder.startRecording();
			//@adligo.diagram_sync with Overview.seq on 7/5/2014
			memory.setMainRecorder(allCoverageRecorder);
		}
		
	}

	@Override
	public I_Tests4J_Controls getControls() {
		return controls;
	}

	@Override
	public void setSystem(I_System system) {
		memory.setSystem(system);
	}


}
