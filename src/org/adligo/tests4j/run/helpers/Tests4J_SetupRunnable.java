package org.adligo.tests4j.run.helpers;

import java.io.IOException;

import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class Tests4J_SetupRunnable implements Runnable {


	private Tests4J_Memory memory;
	private I_Tests4J_NotificationManager notifier;
	private I_Tests4J_Log logger;
	private TrialDescriptionProcessor trialDescriptionProcessor;
	private Tests4J_ProgressMonitor progressMonitor;
	/**
	 * 
	 * @param p
	 * @param pNotificationManager
	 */
	public Tests4J_SetupRunnable(Tests4J_Memory p, 
			I_Tests4J_NotificationManager pNotificationManager) {
		memory = p;
		notifier = pNotificationManager;
		logger = p.getLogger();
		
		int allTrials = memory.getAllTrialCount();
		progressMonitor = new Tests4J_ProgressMonitor(memory, notifier, allTrials, "setup");
		trialDescriptionProcessor = new TrialDescriptionProcessor(memory);
	}
	
	@Override
	public void run() {
		Class<? extends I_AbstractTrial> trialClazz = memory.pollTrialClasses();
		
		while (trialClazz != null) {
			try {
				
				Class<? extends I_AbstractTrial> instrumentedClass = null;
				progressMonitor.notifyIfProgressedTime();
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
				memory.addTrialSetup();
				notifier.checkDoneDescribingTrials();
				
				
			} catch (IOException x) {
				logger.onThrowable(x);
				notifier.onDescibeTrialError();
			}
			trialClazz = memory.pollTrialClasses();
		}
		progressMonitor.notifyDone();
	}
	protected void setNotifyProgress(boolean notifyProgress) {
		progressMonitor.setNotifyProgress(notifyProgress);
	}

}
