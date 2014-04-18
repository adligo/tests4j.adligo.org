package org.adligo.tests4j.run;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;

public class Tests4J_Memory {
	public static PrintStream INITAL_OUT = System.out;
	private ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>> trialClasses = new ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>>();
	private ConcurrentLinkedQueue<TrialDescription> descriptionsToRun = new ConcurrentLinkedQueue<TrialDescription>();
	private List<TrialDescription> allDescriptions = new CopyOnWriteArrayList<TrialDescription>();
	private ConcurrentLinkedQueue<I_TrialResult> resultsBeforeMetadata = new ConcurrentLinkedQueue<I_TrialResult>();
	private int trialCount;
	private boolean systemExit;
	private I_CoveragePlugin plugin;
	private ExecutorService runService;
	private Map<String, I_CoverageRecorder> recorders = new ConcurrentHashMap<String, I_CoverageRecorder>();
	private ThreadLocalOutputStream out = new ThreadLocalOutputStream();
	private I_Tests4J_Logger log;
	
	public Tests4J_Memory(Tests4J_Params params) {
		trialClasses.addAll(params.getTrials());
		trialCount = trialClasses.size();
		systemExit = params.isExitAfterLastNotification();
		plugin = params.getCoveragePlugin();
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(out));
		int threads = params.getThreadPoolSize();
		runService = Executors.newFixedThreadPool(threads);
		log = params.getLog();
	}
	
	public Class<? extends I_AbstractTrial> pollTrial() {
		return trialClasses.poll();
	}
	
	public void add(TrialDescription p) {
		allDescriptions.add(p);
		if (p.isTrialCanRun()) {
			descriptionsToRun.add(p);
		}
	}
	
	
	public TrialDescription pollDescriptions() {
		return descriptionsToRun.poll();
	}
	
	public void add(I_TrialResult p) {
		resultsBeforeMetadata.add(p);
	}
	
	public I_TrialResult pollFailureResults() {
		return resultsBeforeMetadata.poll();
	}
	
	public int getFailureResultsSize() {
		return resultsBeforeMetadata.size();
	}

	public ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>> getTrialClasses() {
		return trialClasses;
	}

	public int getTrialCount() {
		return trialCount;
	}

	public int getDescriptionCount() {
		return allDescriptions.size();
	}

	public boolean isSystemExit() {
		return systemExit;
	}

	public I_CoveragePlugin getPlugin() {
		return plugin;
	}
	
	public void addRecorder(String p, I_CoverageRecorder recorder) {
		recorders.put(p, recorder);
	}
	
	public I_CoverageRecorder getRecorder(String p) {
		return recorders.get(p);
	}
	
	public String getThreadLocalOutput() {
		return out.get().toString();
	}

	public ExecutorService getRunService() {
		return runService;
	}

	public List<TrialDescription> getAllDescriptions() {
		return allDescriptions;
	}
	
	public int getRunnableTrialDescriptions() {
		int toRet = 0;
		for (TrialDescription desc: allDescriptions) {
			if (desc.isTrialCanRun()) {
				if (!desc.isIgnored()) {
					toRet++;
				}
			}
		}
		
		return toRet;
	}

	public I_Tests4J_Logger getLog() {
		return log;
	}
}