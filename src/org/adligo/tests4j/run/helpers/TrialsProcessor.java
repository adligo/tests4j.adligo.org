package org.adligo.tests4j.run.helpers;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.system.ConsoleLogger;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;

/**
 * ok this is the main processing class which does this;
 * 1) put all the test classes in memory
 * 2) start the thread pool
 * 
 * @author scott
 *
 */
public class TrialsProcessor {
	private Tests4J_Memory memory;
	private Tests4J_NotificationManager notifier;
	
	/**
	 * This method kicks off the TheadPool (ExecutorService)
	 * creating as many TrialInstancesProcessors
	 * as there are params.getThreadPoolSize().
	 *  
	 * @param params
	 * @param processor
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 * 
	 */
	public TrialsProcessor(Tests4J_Params params, I_TrialRunListener processor) {
		
		I_CoveragePlugin plugin = params.getCoveragePlugin();
		if (plugin != null) {
			List<Class<? extends I_AbstractTrial>> instrumentedTrials = plugin.instrumentClasses(params);
			params.setTrials(instrumentedTrials);
		}
		
		if (params.getLog() == null) {
			params.setLog(new ConsoleLogger(true));
		} 
		memory = new Tests4J_Memory(params);
		notifier = new Tests4J_NotificationManager(memory, params.getLog(), processor);
		
		if (plugin != null) {
			String mainScope = memory.getMainRecorderScope();
			I_CoverageRecorder allCoverageRecorder = plugin.createRecorder(mainScope);
			memory.addRecorder(mainScope, allCoverageRecorder);
			//@diagram Overview.seq sync on 5/1/2014 'startRecordingTrialRun'
			allCoverageRecorder.startRecording();
		}
		
		//@diagram Overview.seq sync on 5/1/2014 'runService a ExecutorService'
		ExecutorService runService = memory.getRunService();
		int threads = params.getThreadPoolSize();
		
		//@diagram Overview.seq sync on 5/1/2014 'loop theadPoolSize'
		for (int i = 0; i < threads; i++) {
			TrialInstancesProcessor tip = new TrialInstancesProcessor(memory, notifier); 
			runService.execute(tip);
		}
	}
}
