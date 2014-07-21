package org.adligo.tests4j.run.helpers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.adligo.tests4j.models.shared.system.CoveragePluginDelegator;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_System;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.TrialRunListenerDelegator;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
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
public class TrialsProcessor implements I_Tests4J_Delegate {
	public static final String REQUIRES_SETUP_IS_CALLED_BEFORE_RUN = " requires setup is called before run.";
	/**
	 * note this is static and final, so that 
	 * when Tests4J tests itself the ThreadLocalOutputStream
	 * is shared, so that the correct output will show up 
	 * in the I_TrialResults.
	 */
	private static final ThreadLocalOutputStream OUT = new ThreadLocalOutputStream();
	private I_Tests4J_Logger logger;
	private I_System system;
	
	private int trialThreadCount;
	private Tests4J_Manager threadManager;
	private Tests4J_Memory memory;
	private I_Tests4J_NotificationManager notifier;
	private Tests4J_Controls controls;
	
	private I_CoveragePlugin coveragePlugin;
	private I_Tests4J_Params paramsForRun;
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
	public boolean setup(I_TrialRunListener pListener, I_Tests4J_Params pParams) {
		
		memory = new Tests4J_Memory();
		memory.setThreadLocalOutput(OUT);
		memory.setLogger(logger);
		memory.setReporter(new SummaryReporter(logger));
		memory.setListener(new TrialRunListenerDelegator(pListener, logger));
		
		
		Tests4J_ParamsReader reader = new Tests4J_ParamsReader(pParams, logger);
		
		if (reader.isRunnable()) {
			trialThreadCount = reader.getTrialThreadCount();
			threadManager = new Tests4J_Manager(trialThreadCount, system, logger);
			memory.setThreadManager(threadManager);
			
			List<Class<? extends I_AbstractTrial>> trials = reader.getInstrumentedTrials();
			if (trials.size() == 0) {
				trials = reader.getTrials();
			} 
			Class<? extends I_MetaTrial> meta = reader.getMetaTrialClass();
			if (meta != null) {
				TrialDescriptionProcessor trialDescProcessor = new TrialDescriptionProcessor(memory);
				trialDescProcessor.addTrialDescription(meta);
				trials.add(meta);
			}
			coveragePlugin =  reader.getCoveragePlugin();
			memory.setCoveragePlugin(coveragePlugin);
			memory.setTrialClasses(trials);
			memory.setEvaluationLookup(reader.getEvaluatorLookup());
			
			
			
			notifier = new Tests4J_NotificationManager(memory);
			
			controls = new Tests4J_Controls(threadManager, notifier);
			return true;
		}
		controls = new Tests4J_Controls();
		return false;
		/*
		 
		//TODO
		/*
		if (reporter.isRedirect()) {
			if (reporter.isSnare()) {
				System.setOut(new PrintStream(OUT));
				System.setErr(new PrintStream(OUT));
			} else {
				System.setOut(new DuplicatingPrintStream(OUT, System.out));
				System.setErr(new DuplicatingPrintStream(OUT, System.err));
			}
		}
		
		SecurityManager securityManager = System.getSecurityManager();
		if ( !(securityManager instanceof Tests4J_SecurityManager)) {
			//TODO add back in the security manager
			//System.setSecurityManager(new Tests4J_SecurityManager(reporter));
		}
		
		
		*/
	}	
	
	/**
	 * This method kicks off the TheadPool (ExecutorService)
	 * creating as many TrialInstancesProcessors
	 * as there are params.getThreadPoolSize().
	 *  
	 * @param params
	 * @param pListener
	 * 
	 * @diagram Overview.seq sync on 5/19/2014
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		if (notifier == null) {
			throw new IllegalStateException(this.getClass() + REQUIRES_SETUP_IS_CALLED_BEFORE_RUN);
		}
		if (logger.isLogEnabled(TrialsProcessor.class)) {
			logger.log("Starting run with " + trialThreadCount + " trial threads.");
		}
		if (coveragePlugin != null) {
			startRecordingAllTrialsRun(coveragePlugin);
		}
		
		ExecutorService runService = threadManager.getTrialRunService();
		
		//@diagram Overview.seq sync on 5/1/2014 'loop theadPoolSize'
		for (int i = 0; i < trialThreadCount; i++) {
			TrialInstancesProcessor tip = new TrialInstancesProcessor(memory, notifier); 
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
	private void startRecordingAllTrialsRun(I_CoveragePlugin plugin) {
		//@adligo.diagram_sync with Overview.seq on 7/5/2014
		I_CoverageRecorder allCoverageRecorder = plugin.createRecorder();
		//@adligo.diagram_sync with Overview.seq on 7/5/2014
		allCoverageRecorder.startRecording();
		//@adligo.diagram_sync with Overview.seq on 7/5/2014
		memory.setMainRecorder(allCoverageRecorder);
	}

	@Override
	public I_Tests4J_Controls getControls() {
		return controls;
	}

	public I_Tests4J_Logger getLogger() {
		return logger;
	}

	public I_System getSystem() {
		return system;
	}

	public void setLogger(I_Tests4J_Logger logger) {
		this.logger = logger;
	}

	public void setSystem(I_System system) {
		this.system = system;
	}

}
