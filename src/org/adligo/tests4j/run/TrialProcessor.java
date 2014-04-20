package org.adligo.tests4j.run;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.adligo.tests4j.models.shared.AbstractTrial;
import org.adligo.tests4j.models.shared.TrialClassPair;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;
import org.adligo.tests4j.models.shared.system.console.ConsoleLogger;

/**
 * ok this is the main processing class which does this;
 * 1) put all the test classes in memory
 * 2) start the thread pool
 * 
 * @author scott
 *
 */
public class TrialProcessor {
	private Tests4J_Memory memory;
	private NotificationManager notifier;
	
	public TrialProcessor(Tests4J_Params params, I_TrialRunListener processor) {
		
		I_CoveragePlugin plugin = params.getCoveragePlugin();
		List<TrialClassPair> trialClassPairs = new ArrayList<TrialClassPair>();
		if (plugin == null) {
			List<Class<? extends AbstractTrial>> trials = params.getTrials();
			for (Class<? extends AbstractTrial> trialClazz: trials) {
				trialClassPairs.add(new TrialClassPair(trialClazz, null));
			}
		} else {
			List<Class<? extends AbstractTrial>> trials = params.getTrials();
			List<Class<? extends AbstractTrial>> instrumentedTrials = plugin.instrumentClasses(params);
			for (int i = 0; i < trials.size(); i++) {
				Class<? extends AbstractTrial> trialClazz = trials.get(i);
				Class<? extends AbstractTrial> instrumentedTrialClazz = instrumentedTrials.get(i);
				
				trialClassPairs.add(new TrialClassPair(trialClazz, instrumentedTrialClazz));
			}
		}
		
		if (params.getLog() == null) {
			params.setLog(new ConsoleLogger(true));
		} 
		memory = new Tests4J_Memory(params, trialClassPairs);
		notifier = new NotificationManager(memory, params.getLog(), processor);
		notifier.startRecordingRunCoverage();
		
		ExecutorService runService = memory.getRunService();
		int threads = params.getThreadPoolSize();
		
		for (int i = 0; i < threads; i++) {
			runService.execute(new TrialInstanceProcessor(memory, notifier));
		}
	}
}
