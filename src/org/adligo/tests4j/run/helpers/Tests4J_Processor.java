package org.adligo.tests4j.run.helpers;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.common.SystemWithPrintStreamDelegate;
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
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.DelegatingLog;
import org.adligo.tests4j.shared.output.I_OutputBuffer;
import org.adligo.tests4j.shared.output.I_OutputDelegateor;
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
public class Tests4J_Processor implements I_Tests4J_Delegate {
	public static final String REQUIRES_SETUP_IS_CALLED_BEFORE_RUN = " requires setup is called before run.";
	private PrintStream out;
	private Tests4J_Memory memory = new Tests4J_Memory();
	private I_Tests4J_Log log;
	private long logSleepTime = 1000;
	
	private Tests4J_ParamsReader reader;
	private I_Tests4J_ThreadManager threadManager;
	private I_Tests4J_NotificationManager notifier;
	private Tests4J_Controls controls;
	Tests4J_ProgressMonitor setupProgressMonitor;
	Tests4J_ProgressMonitor trialsProgressMonitor;
	
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
		log = reader.getLogger();
		
		List<OutputStream> outs =  pParams.getAdditionalReportOutputStreams();
		if (outs.size() >= 1) {
			List<I_OutputBuffer> outputBuffers = new ArrayList<I_OutputBuffer>();
			for (OutputStream ob: outs) {
				outputBuffers.add(new SafeOutputStremBuffer(log, ob));
			}
			outputBuffers.add(new PrintStreamOutputBuffer(out));
			out = new  JsePrintOutputStream(new ListDelegateOutputBuffer(outputBuffers));
			
			SystemWithPrintStreamDelegate sysPs = new SystemWithPrintStreamDelegate(system, out);
			log = new DefaultLog(sysPs, pParams.getLogStates());
		}
		
		memory.setLog(log);
		//use the dynamic log
		memory.setReporter(new SummaryReporter(memory.getLog()));
		
		memory.setListener(pListener);
		
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
		
		log = memory.getLogger();
		
		long time = memory.getTime();
		memory.setStartTime(time);
		
		threadManager = memory.getThreadManager();
		notifier = memory.getNotifier();
		int trials = memory.getAllTrialCount();
		
		
		Tests4J_ProcessInfo setupProcessInfo = memory.getSetupProcessInfo();
		Tests4J_ProcessInfo trialProcessInfo = memory.getTrialProcessInfo();
		Tests4J_ProcessInfo remoteProcessInfo = memory.getTrialProcessInfo();
		setupProgressMonitor = new Tests4J_ProgressMonitor(
				memory.getSystem(), notifier, setupProcessInfo);
		trialsProgressMonitor = new Tests4J_ProgressMonitor(
				memory.getSystem(), notifier, trialProcessInfo);
		I_OutputDelegateor od = setupLogging(setupProcessInfo, trialProcessInfo, remoteProcessInfo);
		try {
			startRecordingAllTrialsRun();
			submitSetupRunnables(setupProcessInfo);
			submitTrialsRunnables(trialProcessInfo, od);
		} catch (Throwable t) {
			log.onThrowable(t);
		}
		if (ConcurrentOutputDelegateor.class.getName().equals(od.getClass().getName())) {
			monitorThenShutdown((ConcurrentOutputDelegateor) od);
		} else {
			threadManager.shutdown();
		}
		
	}

	/**
	 * @param setupProcessInfo
	 * @param trialProcessInfo
	 * @param remoteProcessInfo
	 * @return null if single threaded
	 */
	private ConcurrentOutputDelegateor setupLogging(Tests4J_ProcessInfo setupProcessInfo, Tests4J_ProcessInfo trialProcessInfo ,
			Tests4J_ProcessInfo remoteProcessInfo) {
		if (setupProcessInfo.getThreadCount() <= 1 &&
				trialProcessInfo.getThreadCount() <= 1 &&
				remoteProcessInfo.getThreadCount() <= 1) {
			
			//run single threaded on the main thread.
			I_OutputDelegateor od = new OutputDelegateor(out);
			
			JsePrintOutputStream jpos = new JsePrintOutputStream(od);
			System.setOut(jpos);
			System.setErr(jpos);
			return null;
		} else {
			ConcurrentOutputDelegateor cod = new ConcurrentOutputDelegateor();
			JsePrintOutputStream jpos = new JsePrintOutputStream(cod);
			System.setOut(jpos);
			System.setErr(jpos);
			log = new DelegatingLog(memory.getSystem(), reader.getLogStates(), cod);
			memory.setLog(log);
			return cod;
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
		
		int allTrials = memory.getAllTrialCount();
		if (log.isLogEnabled(Tests4J_Processor.class)) {
			log.log("Starting setup with " + setupThreadCount + " setup threads.");
		}
		
		if (setupThreadCount <= 1) {
			Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory, notifier, setupProgressMonitor); 
			info.addRunnable(sr);
			
			sr.run();
		} else {
			
			ExecutorService runService = threadManager.getSetupService();
			
			for (int i = 0; i < setupThreadCount; i++) {
				Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory, notifier, null); 
				info.addRunnable(sr);
				
				try {
					Future<?> future = runService.submit(sr);
					threadManager.addSetupFuture(future);
				} catch (RejectedExecutionException x) {
					//do nothing, not sure why this exception is happening for me
					// it must have to do with shutdown, but it happens intermittently.
				}
			}

		}
		
		long now = memory.getTime();
		memory.setSetupEndTime(now);
		if (log.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory.getStartTime();
			double secs = millis/1000.0;
			log.log("Finised setup after " + secs + " seconds.");
		}
	}
	
	private void submitTrialsRunnables(Tests4J_ProcessInfo info, I_OutputDelegateor od) {
		notifier.onProcessStateChange(info);
		int trialThreadCount = info.getThreadCount();
		
		if (log.isLogEnabled(Tests4J_Processor.class)) {
			log.log("Starting submitTrialRunnables with " + trialThreadCount + " trial threads.");
		}
		if (trialThreadCount <= 1) {
			Tests4J_TrialsRunable tip = new Tests4J_TrialsRunable(memory, notifier, null); 
			info.addRunnable(tip);
			tip.setOutputDelegator(od);
			
			tip.run();
			long now = memory.getTime();
			memory.setSetupEndTime(now);
			if (log.isLogEnabled(Tests4J_Processor.class)) {
				double millis = now - memory.getStartTime();
				double secs = millis/1000.0;
				log.log("Finised trials after " + secs + " seconds.");
			}
		} else {
			ExecutorService runService = threadManager.getTrialRunService();
			
			for (int i = 0; i < trialThreadCount; i++) {
				Tests4J_TrialsRunable tip = new Tests4J_TrialsRunable(memory, notifier, null); 
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
			
		}
	}

	private boolean allDone() {
		if (notifier.isDoneDescribeingTrials()) {
			if (notifier.isDoneRunningTrials()) {
				return true;
			}
		}
		return false;
	}
	private void monitorThenShutdown(ConcurrentOutputDelegateor cod) {
		Tests4J_ProcessInfo setupProcessInfo = memory.getSetupProcessInfo();
		Tests4J_ProcessInfo trialProcessInfo = memory.getTrialProcessInfo();
		
		boolean sentSetup100 = false;
		boolean sentTrial100 = false;
		while (!allDone()) {
			try {
				Thread.sleep(logSleepTime);
				if (!sentSetup100) {
					if (setupProcessInfo.getPercentDone() >= 100.0) {
						sentSetup100 = true;
					}
					notifier.onProgress(setupProcessInfo);
					
				}
				notifier.onProgress(trialProcessInfo);
				
				String next = cod.poll();
				while (next != null) {
					out.println(next);
					next = cod.poll();
				}
			} catch (InterruptedException x) {
				throw new RuntimeException(x);
			}
		}
		String next = cod.poll();
		while (next != null) {
			out.println(next);
			next = cod.poll();
		}

		threadManager.shutdown();
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
