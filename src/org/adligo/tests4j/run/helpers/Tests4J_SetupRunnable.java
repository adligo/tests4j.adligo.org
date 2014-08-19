package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Runnable;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class Tests4J_SetupRunnable implements Runnable, I_Tests4J_Runnable {


	private Tests4J_Memory memory;
	private I_Tests4J_NotificationManager notifier;
	private I_Tests4J_Log logger;
	private TrialDescriptionProcessor trialDescriptionProcessor;
	/**
	 * only used for single threaded operation may be null
	 */
	private Tests4J_ProgressMonitor progressMonitor;
	private Tests4J_ProcessInfo processInfo;
	private String trialName;
	
	/**
	 * 
	 * @param p
	 * @param pNotificationManager
	 */
	public Tests4J_SetupRunnable(Tests4J_Memory p, 
			I_Tests4J_NotificationManager pNotificationManager, Tests4J_ProgressMonitor pProgressMonitor) {
		memory = p;
		notifier = pNotificationManager;
		logger = p.getLogger();
		processInfo = memory.getSetupProcessInfo();
		
		progressMonitor = pProgressMonitor;
		trialDescriptionProcessor = new TrialDescriptionProcessor(memory);
	}
	
	@Override
	public void run() {
		if (logger.isLogEnabled(Tests4J_SetupRunnable.class)) {
			logger.log("" + this + " on " + Thread.currentThread().getName() + " starting.");
		}
		processInfo.addRunnableStarted();
		Class<? extends I_AbstractTrial> trialClazz = memory.pollTrialClasses();
		
		while (trialClazz != null && !notifier.hasDescribeTrialError()) {
			trialName = trialClazz.getName();
			try {
				
				Class<? extends I_AbstractTrial> instrumentedClass = null;
				
				//no need to instrument the meta trial
				if ( !I_MetaTrial.class.isAssignableFrom(trialClazz)) {
					//instrument the classes first, so that 
					// they are loaded before the TrialDescrpion processing code
					I_Tests4J_CoveragePlugin plugin = memory.getCoveragePlugin();
					
					if (plugin != null) {
						instrumentedClass = plugin.instrument(trialClazz);
					}
				}	
				TrialDescription trialDescription = trialDescriptionProcessor.instrumentAndAddTrialDescription(trialClazz);
				if (trialDescription.isRunnable()) {
					if (instrumentedClass != null) {
						memory.addTrialToRun(instrumentedClass);
					} else {
						memory.addTrialToRun(trialClazz);
					}
				} 
			} catch (Throwable x) {
				logger.onThrowable(x);
				notifier.onDescibeTrialError();
			}
			if (progressMonitor != null) {
				progressMonitor.incrementAndNotify();
			} else {
				processInfo.addDone();
			}
			trialClazz = memory.pollTrialClasses();
		}
		if (logger.isLogEnabled(Tests4J_SetupRunnable.class)) {
			logger.log("" + this + " on " + Thread.currentThread().getName() + " finished.");
		}
		trialName = null;
		processInfo.addRunnableFinished();
		notifier.checkDoneDescribingTrials();
	}

	@Override
	public synchronized String getTrial() {
		return trialName;
	}

}
