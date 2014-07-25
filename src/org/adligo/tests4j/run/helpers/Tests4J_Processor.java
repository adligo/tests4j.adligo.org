package org.adligo.tests4j.run.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_Tests4J_System;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
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
	/**
	 * note this is static and final, so that 
	 * when Tests4J tests itself the ThreadLocalOutputStream
	 * is shared, so that the correct output will show up 
	 * in the I_TrialResults.
	 */
	private static final ThreadLocalOutputStream OUT = new ThreadLocalOutputStream();
	private Tests4J_ParamsReader reader;
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private Tests4J_Memory memory = new Tests4J_Memory();
	private I_Tests4J_Logger logger;
	
	private Tests4J_ThreadManager threadManager;
	private Tests4J_NotificationManager notifier;
	private Tests4J_Controls controls;
	
	/**
	 * This method sets up everything for the run,
	 * assumes all other setters have been called.
	 *  
	 * @param pListener
	 * @param pParams
	 * @param pLogger
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean setup(I_Tests4J_Listener pListener, I_Tests4J_Params pParams) {
		
		memory.setThreadLocalOutput(OUT);
		I_Tests4J_Logger logger = memory.getLogger();
		memory.setListener(pListener);
		memory.setReporter(new SummaryReporter(logger));
		
		reader = new Tests4J_ParamsReader(pParams, logger);
		
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
		if (logger.isLogEnabled(Tests4J_Processor.class)) {
			
			logger.log("Start: " + dateFormat.format(new Date(time)));
		}
		threadManager = memory.getThreadManager();
		notifier = memory.getNotifier();
		submitSetupRunnables();
		
		startRecordingAllTrialsRun();
		
		//sleep this thread until it can start running trials
		
		submitTrialsRunnables();
		/*
		Collection<I_Tests4J_RemoteInfo> remotes = params.getRemoteInfo();
		for (I_Tests4J_RemoteInfo remote: remotes) {
			I_Tests4J_Params remoteParams = params.getRemoteParams(remote);
			Tests4J_RemoteRunner remoteRunner = new Tests4J_RemoteRunner(remote, remoteParams, reporter);
			Future<?> future = runService.submit(remoteRunner);
			threadManager.addRemoteFuture(future);
			threadManager.addRemoteRunner(remoteRunner);
		}
		if (runService instanceof ThreadPoolExecutor) {
			ThreadPoolExecutor tpe = (ThreadPoolExecutor) runService;
			tpe.setKeepAliveTime(1000, TimeUnit.MILLISECONDS);
			tpe.allowCoreThreadTimeOut(true);
		}
		*/
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
			logger.log("Starting setup with " + setupThreadCount + " trial threads.");
		}
		if (setupThreadCount <= 1) {
			//run single threaded on the main thread.
			Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory, notifier); 
			sr.run();
		} else {
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
			Tests4J_TrialsRunable tip = new Tests4J_TrialsRunable(memory, notifier); 
			
			tip.run();
			long now = memory.getTime();
			memory.setSetupEndTime(now);
			if (logger.isLogEnabled(Tests4J_Processor.class)) {
				double millis = now - memory.getStartTime();
				double secs = millis/1000.0;
				logger.log("Finised trials after " + secs + " seconds.");
			}
		} else {
			ExecutorService runService = threadManager.getTrialRunService();
			
			
			
			for (int i = 0; i < trialThreadCount; i++) {
				Tests4J_TrialsRunable tip = new Tests4J_TrialsRunable(memory, notifier); 
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
			int tests = memory.getAllTestsCount();
			Tests4J_ProgressMonitor  testsProgressMonitor = new Tests4J_ProgressMonitor(memory, notifier, tests, "tests");
			
			while (!notifier.isDoneRunningTrials()) {
				try {
					Thread.sleep(500);
					
					long trialsRan = memory.getTrialsDone();
					trialProgressMonitor.notifyIfProgressedTime(trialsRan);
					long testsRan = memory.getTestsDone();
					testsProgressMonitor.notifyIfProgressedTime(testsRan);
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
	public void setLogger(I_Tests4J_Logger logger) {
		memory.setLogger(logger);
	}

	@Override
	public void setSystem(I_Tests4J_System system) {
		memory.setSystem(system);
	}


}
