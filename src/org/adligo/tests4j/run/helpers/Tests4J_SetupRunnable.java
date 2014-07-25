package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.run.discovery.TrialDescription;

public class Tests4J_SetupRunnable implements Runnable {


	private Tests4J_Memory memory;
	private Tests4J_ThreadManager threadManager;
	private I_Tests4J_NotificationManager notifier;
	private I_Tests4J_Logger logger;
	private TrialDescriptionProcessor trialDescriptionProcessor;
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
		
		trialDescriptionProcessor = new TrialDescriptionProcessor(memory);
	}
	@Override
	public void run() {
		Class<? extends I_AbstractTrial> trialClazz = memory.pollTrialClasses();
		while (trialClazz != null) {
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
				TrialDescription trialDescription = trialDescriptionProcessor.addTrialDescription(trialClazz);
				if (trialDescription.isRunnable()) {
					if (instrumentedClass != null) {
						memory.addTrialToRun(instrumentedClass);
					} else {
						memory.addTrialToRun(trialClazz);
					}
				}
				notifier.checkDoneDescribingTrials();
				
				
			} catch (Exception x) {
				logger.onException(x);
				notifier.onDescibeTrialError();
			} catch (Error x) {
				logger.onException(x);
				notifier.onDescibeTrialError();
			}
			trialClazz = memory.pollTrialClasses();
		}
	}

}
