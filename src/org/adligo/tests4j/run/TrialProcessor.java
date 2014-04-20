package org.adligo.tests4j.run;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
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
		if (plugin != null) {
			List<Class<? extends I_AbstractTrial>> instrumentedTrials = plugin.instrumentClasses(params);
			params.setTrials(instrumentedTrials);
		}
		
		if (params.getLog() == null) {
			params.setLog(new ConsoleLogger(true));
		} 
		memory = new Tests4J_Memory(params);
		notifier = new NotificationManager(memory, params.getLog(), processor);
		notifier.startRecordingRunCoverage();
		
		ExecutorService runService = memory.getRunService();
		int threads = params.getThreadPoolSize();
		
		for (int i = 0; i < threads; i++) {
			runService.execute(new TrialInstanceProcessor(memory, notifier));
		}
	}
}
