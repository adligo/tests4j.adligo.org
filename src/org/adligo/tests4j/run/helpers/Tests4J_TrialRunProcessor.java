package org.adligo.tests4j.run.helpers;

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
public class Tests4J_TrialRunProcessor implements I_Tests4J_Delegate {
	public static final String REQUIRES_SETUP_IS_CALLED_BEFORE_RUN = " requires setup is called before run.";
	/**
	 * note this is static and final, so that 
	 * when Tests4J tests itself the ThreadLocalOutputStream
	 * is shared, so that the correct output will show up 
	 * in the I_TrialResults.
	 */
	private static final ThreadLocalOutputStream OUT = new ThreadLocalOutputStream();
	private Tests4J_Memory memory = new Tests4J_Memory();
	
	
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
		
		Tests4J_ParamsReader reader = new Tests4J_ParamsReader(pParams, logger);
		
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
		//@diagram sync with Overview.seq on 7/21/2014
		int trialThreadCount = memory.getTrialThreadCount();
		I_Tests4J_Logger logger = memory.getLogger();
		if (logger.isLogEnabled(Tests4J_TrialRunProcessor.class)) {
			logger.log("Starting run with " + trialThreadCount + " trial threads.");
		}
		I_Tests4J_CoveragePlugin coveragePlugin = memory.getCoveragePlugin();
		if (coveragePlugin != null) {
			startRecordingAllTrialsRun(coveragePlugin);
		}
		
		Tests4J_ThreadManager threadManager = memory.getThreadManager();
		ExecutorService runService = threadManager.getTrialRunService();
		Tests4J_NotificationManager notifier = memory.getNotifier();
		
		//@diagram Overview.seq sync on 7/21/2014 'loop trialThreadCount size()'
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
	 * @adligo.diagram_sync with Overview.seq on 7/5/2014
	 * @param plugin
	 */
	private void startRecordingAllTrialsRun(I_Tests4J_CoveragePlugin plugin) {
		//@adligo.diagram_sync with Overview.seq on 7/5/2014
		I_Tests4J_CoverageRecorder allCoverageRecorder = plugin.createRecorder();
		//@adligo.diagram_sync with Overview.seq on 7/5/2014
		allCoverageRecorder.startRecording();
		//@adligo.diagram_sync with Overview.seq on 7/5/2014
		memory.setMainRecorder(allCoverageRecorder);
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
