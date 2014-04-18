package org.adligo.tests4j.run;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		
		if (params.getLogger() == null) {
			params.setLogger(new ConsoleLogger(true));
		} 
		memory = new Tests4J_Memory(params);
		notifier = new NotificationManager(memory, params.getLogger(), processor);
		ExecutorService runService = memory.getRunService();
		int threads = params.getThreadPoolSize();
		
		for (int i = 0; i < threads; i++) {
			runService.execute(new TrialInstanceProcessor(memory, notifier));
		}
	}
}
