package org.adligo.tests4j.run.helpers;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.system.CoveragePluginWrapper;
import org.adligo.tests4j.models.shared.system.DuplicatingPrintStream;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * ok this is the main processing class which does this;
 * 1) put all the test classes in memory
 * 2) start the thread pool
 * 
 * @author scott
 *
 */
public class TrialsProcessor implements I_Tests4J_Delegate {
	/**
	 * note this is static and final, so that 
	 * when Tests4J tests itself the ThreadLocalOutputStream
	 * is shared, so that the correct output will show up 
	 * in the I_TrialResults.
	 */
	private static final ThreadLocalOutputStream OUT = new ThreadLocalOutputStream();
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
	public void run(I_TrialRunListener processor, I_Tests4J_Params pParams) {
		
		Tests4J_Params params = new Tests4J_Params(pParams);
		I_CoveragePlugin plugin = params.getCoveragePlugin();
		if (plugin != null) {
			List<Class<? extends I_AbstractTrial>> instrumentedTrials = plugin.instrumentClasses(params);
			params.setTrials(instrumentedTrials);
			plugin = new CoveragePluginWrapper(plugin);
			params.setCoveragePlugin(plugin);
		}
		
		I_Tests4J_Reporter reporter = params.getReporter();
		if (reporter == null) {
			reporter = new ConsoleReporter();
			params.setReporter(reporter);
		} 
		
		if (reporter.isRedirect()) {
			if (reporter.isSnare()) {
				System.setOut(new PrintStream(OUT));
				System.setErr(new PrintStream(OUT));
			} else {
				System.setOut(new DuplicatingPrintStream(OUT, System.out));
				System.setErr(new DuplicatingPrintStream(OUT, System.err));
			}
		}
		
		memory = new Tests4J_Memory(params,OUT);
		notifier = new Tests4J_NotificationManager(memory, params.getReporter(), processor);
		
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
			TrialInstancesProcessor tip = new TrialInstancesProcessor(memory, notifier, reporter); 
			runService.execute(tip);
			memory.addTrialInstancesProcessors(tip);
		}
	}
}
