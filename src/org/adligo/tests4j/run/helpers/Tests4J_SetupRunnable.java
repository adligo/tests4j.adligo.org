package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.association.I_ClassAssociationsLocal;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.run.discovery.TrialDescriptionProcessor;
import org.adligo.tests4j.run.discovery.TrialQueueDecisionTree;
import org.adligo.tests4j.run.discovery.TrialState;
import org.adligo.tests4j.run.memory.Tests4J_Memory;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageTrialInstrumentation;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Runnable;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;
import org.adligo.tests4j.system.shared.api.Tests4J_TrialProgress;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.SubProgress;

/**
 * 
 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
 * @author scott
 *
 */
public class Tests4J_SetupRunnable implements Runnable, I_Tests4J_Runnable {


	private Tests4J_Memory memory;
	private I_Tests4J_NotificationManager notifier;
	private I_Tests4J_Log logger;
	private TrialDescriptionProcessor trialDescriptionProcessor;
	private TrialQueueDecisionTree trialQueueDecisionTree;
	private Tests4J_PhaseOverseer phaseOverseer_;
	private String trialName;
	private Class<? extends I_AbstractTrial> currentTrial;
	private TrialState state;
	
	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 * @param p
	 * @param pNotificationManager
	 */
	public Tests4J_SetupRunnable(Tests4J_Memory p, 
			I_Tests4J_NotificationManager pNotificationManager) {
		memory = p;
		trialQueueDecisionTree = p.getTrialQueueDecisionTree();
		notifier = pNotificationManager;
		logger = p.getLog();
		phaseOverseer_ = memory.getSetupPhaseOverseer();
		
		trialDescriptionProcessor = new TrialDescriptionProcessor(memory);
	}
	
	@Override
	public void run() {
		if (logger.isLogEnabled(Tests4J_SetupRunnable.class)) {
			logger.log("" + this + " on " + Thread.currentThread().getName() + " starting.");
		}
		phaseOverseer_.addRunnableStarted();
		//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
		Class<? extends I_AbstractTrial> trialClazz = memory.pollTrialClasses();
		
		while (trialClazz != null && !notifier.hasDescribeTrialError()) {
			trialName = trialClazz.getName();
			state = new TrialState(trialName, trialClazz);
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			checkAndApprove(trialClazz, state);
			
			//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			trialQueueDecisionTree.addTrial(state);
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			phaseOverseer_.addDone();
			//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			trialClazz = memory.pollTrialClasses();
		}
		if (logger.isLogEnabled(Tests4J_SetupRunnable.class)) {
			logger.log("" + this + " on " + Thread.currentThread().getName() + " finished.");
		}
		trialName = null;
		phaseOverseer_.addRunnableFinished();
		//its not on the main thread so join
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			logger.onThrowable(e);
		}
	}

	
	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 * @param trialClazz
	 * @param states
	 */
	protected void checkAndApprove(Class<? extends I_AbstractTrial> trialClazz, TrialState states) {
		try {
			
			
			TrialDescription trialDescription =  trialDescriptionProcessor.createAndRemberNotRunnableTrials(trialClazz);
			states.setDescApprovedForInstrumentation(trialDescription);
			if (trialDescription.isRunnable()) {
				TrialType type = TrialType.get(trialDescription.getType());
				if (TrialType.MetaTrial == type) {
					//don't instrument the metatrial, as it doesn't need to do  do any code coverage
					states.setDescApprovedForRun(trialDescription);
				} else {
					I_Tests4J_CoverageTrialInstrumentation instrumention = null;
					//no need to instrument the meta trial
					I_Tests4J_CoveragePlugin plugin = memory.getCoveragePlugin();
					
					if (plugin != null) {
						if ( !I_MetaTrial.class.isAssignableFrom(trialClazz)) {
							//instrument the classes first, so that 
							// they are loaded before the TrialDescrpion processing code
							currentTrial = trialClazz;
							//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
							instrumention = plugin.instrumentTrial(trialClazz);
							
							trialDescription = trialDescriptionProcessor.createAndRemberNotRunnableTrials(instrumention);
							states.setDescApprovedForRun(trialDescription);
						}
					} else {
						states.setDescApprovedForRun(trialDescription);
					} 
				}
			} 
		} catch (Throwable x) {
			logger.onThrowable(x);
			notifier.onDescibeTrialError();
		}
	}

	@Override
	public I_Tests4J_TrialProgress getTrial() {
		if (state == null) {
			return null; 
		}
		if (currentTrial == null) {
			//no plugin
			return new Tests4J_TrialProgress(state.getTrialName(), state.getPctDone(), null);
		} else {
			I_Tests4J_CoveragePlugin plugin = memory.getCoveragePlugin();
			
			return new Tests4J_TrialProgress(state.getTrialName(), state.getPctDone(),
					new SubProgress("setup", plugin.getInstrumentProgress(currentTrial), null));
		}
	}
}
