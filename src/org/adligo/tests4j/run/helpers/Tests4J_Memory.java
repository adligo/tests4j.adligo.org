package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_ProcessInfo;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Selection;
import org.adligo.tests4j.models.shared.system.Tests4J_ListenerDelegator;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.shared.output.CurrentLog;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.shared.output.I_Tests4J_LogObtainer;

/**
 * Instances of this class represent the main
 * memory of the Tests4J framework, so if are working
 * on the framework and need to share some objects
 * put them in here.
 * 
 * @author scott
 *
 */
public class Tests4J_Memory implements I_Tests4J_Memory {
	private ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>> trialClasses = 
			new ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>>();
	private AtomicBoolean metaTrial = new AtomicBoolean(false);
	private AtomicBoolean hasTrialThatCanRun = new AtomicBoolean(false);
	private CopyOnWriteArraySet<I_Tests4J_Selection> tests;
	/**
	 * The key is the class name of the trial
	 * 
	 * keeps track of the trials between threads,
	 * so when a trial is sent twice, synchronization occurs
	 * so that it the runs of it do NOT overlap
	 */
	private ConcurrentHashMap<String,TrialDescription> trialDescriptions = new ConcurrentHashMap<String,TrialDescription>();
	/**
	 * note the trial description may occur twice,
	 * if the user of the api is running trials more than once
	 */
	private CopyOnWriteArrayList<TrialDescription> allTrialDescriptions = new CopyOnWriteArrayList<TrialDescription>();
	/**
	 * the queue of trials to run,
	 * they should be instrumented if there is a coverage plugin
	 */
	private ArrayBlockingQueue<Class<? extends I_AbstractTrial>> trialsToRun;
	
	private ConcurrentHashMap<String,AtomicInteger> trialRuns = new ConcurrentHashMap<String,AtomicInteger>();
	
	private I_Tests4J_CoverageRecorder mainRecorder;
	
	private ConcurrentLinkedQueue<I_TrialResult> resultsBeforeMetadata = new ConcurrentLinkedQueue<I_TrialResult>();
	private int allTrialCount;
	private I_Tests4J_CoveragePlugin coveragePlugin;
	private final CurrentLog log;
	private volatile I_Tests4J_Log currentLogDelegate = new DefaultLog();
	private Tests4J_ProcessInfo setupProcessInfo;
	private Tests4J_ProcessInfo trialProcessInfo;
	private Tests4J_ProcessInfo remoteProcessInfo;
	/**
	 * a safe wrapper around the I_TrialRunListener passed to Test4J.run
	 * this should never be null during the trial run
	 */
	private Tests4J_ListenerDelegator listener;
	
	/**
	 * this is the reporter that tests4j uses to report it's own
	 * progress, and should never be null during the trial run
	 */
	private I_Tests4J_Listener reporter;
	
	private Tests4J_ThreadManager threadManager;
	private ArrayBlockingQueue<I_TrialRunMetadata> metaTrialDataBlock = new ArrayBlockingQueue<I_TrialRunMetadata>(1);
	private I_TrialRunMetadata metadata;
	private TrialDescription metaTrialDescription;
	private AtomicBoolean ranMetaTrial = new AtomicBoolean(false);
	private boolean hasRemoteDelegation = false;
	private I_EvaluatorLookup evaluationLookup;
	private I_System system;
	private AtomicLong startTime = new AtomicLong();
	private AtomicLong setupEndTime = new AtomicLong();
	private AtomicLong trialEndTime = new AtomicLong();
	private AtomicLong remoteEndTime = new AtomicLong();
	private AtomicInteger trialsDone = new AtomicInteger();
	private AtomicLong testsDone = new AtomicLong();
	private Tests4J_NotificationManager notifier;
	/**
	 * 
	 * @param params
	 * 
	 * @diagram sync on 7/21/2014 with Overview.seq 
	 */
	public Tests4J_Memory() {
		log = new CurrentLog(new I_Tests4J_LogObtainer() {
			
			@Override
			public I_Tests4J_Log getLog() {
				return currentLogDelegate;
			}
		});
	}
	
	protected synchronized void initialize(Tests4J_ParamsReader p) {
		
		
		List<Class<? extends I_AbstractTrial>> trials = p.getTrials();
		
		
		threadManager = new Tests4J_ThreadManager(system, log);
		
		Class<? extends I_MetaTrial> meta = p.getMetaTrialClass();
		if (meta != null) {
			TrialDescriptionProcessor trialDescProcessor = new TrialDescriptionProcessor(this);
			trialDescProcessor.instrumentAndAddTrialDescription(meta);
			trials.add(meta);
			metaTrial.set(true);
		}
		coveragePlugin =  p.getCoveragePlugin();
		
		
		allTrialCount = trials.size();
		trialsToRun = 
				new ArrayBlockingQueue<Class<? extends I_AbstractTrial>>(allTrialCount);
		setupProcessInfo = new Tests4J_ProcessInfo(I_Tests4J_ProcessInfo.SETUP, 
				p.getSetupThreadCount(), allTrialCount);
		/**
		 * note this includes the ignored trials,
		 * so that the trial process can overlap with the setup
		 * process, which allows for much faster execution of the entire process
		 */
		int trialCount = allTrialCount;
		if (meta != null) {
			trialCount--;
		}
		trialProcessInfo = new Tests4J_ProcessInfo(I_Tests4J_ProcessInfo.TRIALS, 
				p.getTrialThreadCount(), trialCount);
		//todo when remote functionality is added
		remoteProcessInfo = new Tests4J_ProcessInfo(I_Tests4J_ProcessInfo.REMOTES, 
				0, 0);
		threadManager.setupSetupProcess(setupProcessInfo);
		threadManager.setupTrialsProcess(trialProcessInfo);
		//threadManager.setupRemoteProcess(remoteProcessInfo);
		
		trialClasses.addAll(trials);
		tests = new CopyOnWriteArraySet<I_Tests4J_Selection>(p.getTests());
		
		evaluationLookup = p.getEvaluatorLookup();
		notifier = new Tests4J_NotificationManager(this);
		
	}
	
	/**
	 * 
	 * @return the trial classes which have been request to be run,
	 * stored in a ConcurrentLinkedQueue for thread collaboration.
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public Class<? extends I_AbstractTrial> pollTrialClasses() {
		return trialClasses.poll();
	}
	/**
	 * @param name
	 * @param p
	 */
	public void addTrialDescription(String name, TrialDescription p) {
		allTrialDescriptions.add(p);
		if (p.isRunnable() && p.getType() == TrialType.MetaTrial) {
			metaTrialDescription = p;
		}
		trialDescriptions.put(name, p);
		if (!trialRuns.containsKey(name)) {
			trialRuns.put(name, new AtomicInteger(0));
		}
		if (log.isLogEnabled(Tests4J_Memory.class)) {
			log.log("added trial description for " + name);
		}
	}
	public TrialDescription getTrialDescription(String name) {
		return trialDescriptions.get(name);
	}
	
	public int incrementTrialRun(String trialName) {
		if (log.isLogEnabled(Tests4J_Memory.class)) {
			log.log("incrementTrialRun for " + trialName);
		}
		AtomicInteger next = trialRuns.get(trialName);
		return next.getAndAdd(1);
	}

	
	public synchronized void addResultBeforeMetadata(I_TrialResult p) {
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

	public int getAllTrialCount() {
		return allTrialCount;
	}

	public int getDescriptionCount() {
		return allTrialDescriptions.size();
	}

	/**
	 * 
	 * @return the coverage plugin, may be null.
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public I_Tests4J_CoveragePlugin getCoveragePlugin() {
		return coveragePlugin;
	}
	
	/**
	 * 
	 * @param p
	 * @param recorder
	 * 
	 * @diagram Overview.seq sync on 7/5/2014
	 */
	public synchronized void setMainRecorder(I_Tests4J_CoverageRecorder recorder) {
		mainRecorder = recorder;
	}
	
	/**
	 * 
	 * @return the recorder that is recording all of the coverage
	 * for the various trials/tests.
	 * 
	 * @diagram Overview.seq sync on 5/26/2014
	 */
	public I_Tests4J_CoverageRecorder getMainRecorder() {
		return mainRecorder;
	}
	

	public Iterator<TrialDescription> getAllTrialDescriptions() {
		return allTrialDescriptions.iterator();
	}
	
	public int getRunnableTrialDescriptions() {
		int toRet = 0;
		Iterator<TrialDescription> it = allTrialDescriptions.iterator();
		while (it.hasNext()) {
			TrialDescription desc = it.next();
			if (desc.isRunnable()) {
				if (!desc.isIgnored()) {
					toRet++;
				}
			}
		}
		
		return toRet;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_Memory#getLogger()
	 */
	@Override
	public I_Tests4J_Log getLogger() {
		return log;
	}

	public boolean hasTestsFilter() {
		if (tests != null) {
			return true;
		}
		return false;
	}
	
	public boolean runTest(Class<?> trial, String method) {
		if (tests.isEmpty()) {
			return true;
		}
		for (I_Tests4J_Selection sel: tests) {
			Class<?> selTrial = sel.getTrial();
			if (trial.getName().equals(selTrial.getName())) {
				String selMethod = sel.getTestMethodName();
				if (method.equals(selMethod)) {
					return true;
				}
			}
		}
		return false;
	}

	public Tests4J_ListenerDelegator getListener() {
		return listener;
	}

	public I_Tests4J_ThreadManager getThreadManager() {
		return threadManager;
	}

	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 * @param md
	 */
	public synchronized void setMetaTrialData(I_TrialRunMetadata md) {
		metadata = md;
		metaTrialDataBlock.add(md);
		
	}
	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 * @param md
	 */
	public I_TrialRunMetadata takeMetaTrialData() throws InterruptedException {
		return metaTrialDataBlock.take();
	}
	
	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 */
	public boolean hasMetaTrial() {
		return metaTrial.get();
	}
	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 * @return
	 */
	public TrialDescription getMetaTrialDescription() {
		return metaTrialDescription;
	}
	
	public int getIgnoredTrialDescriptions() {
		int toRet = 0;
		Iterator<TrialDescription> it = allTrialDescriptions.iterator();
		while (it.hasNext()) {
			TrialDescription td = it.next();
			if (td.isIgnored()) {
				toRet++;
			}
		}
		return toRet;
	}
	
	public synchronized  void setRanMetaTrial() {
		ranMetaTrial.set(true);
	}
	
	public synchronized boolean hasRanMetaTrial() {
		return ranMetaTrial.get();
	}

	public boolean hasRemoteDelegation() {
		return hasRemoteDelegation;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_Memory#getEvaluationLookup()
	 */
	@Override
	public I_EvaluatorLookup getEvaluationLookup() {
		return evaluationLookup;
	}

	public I_System getSystem() {
		return system;
	}

	public I_Tests4J_Listener getReporter() {
		return reporter;
	}

	protected void setReporter(I_Tests4J_Listener reporter) {
		this.reporter = reporter;
	}

	protected void setListener(I_Tests4J_Listener p) {
		listener = new Tests4J_ListenerDelegator(p, log);
	}
	/**
	 * paramTests
	 * @return
	 */
	public CopyOnWriteArraySet<I_Tests4J_Selection> getTests() {
		return tests;
	}

	protected synchronized void setLog(I_Tests4J_Log logger) {
		this.currentLogDelegate = logger;
	}

	public Tests4J_NotificationManager getNotifier() {
		return notifier;
	}

	protected void setSystem(I_System system) {
		this.system = system;
	}

	
	protected void addTrialToRun(Class<? extends I_AbstractTrial> p) {
		trialsToRun.add(p);
		hasTrialThatCanRun.set(true);
	}
	
	protected Class<? extends I_AbstractTrial> pollTrialsToRun() {
		return trialsToRun.poll();
	}
	
	protected Class<? extends I_AbstractTrial> takeTrialsToRun() throws InterruptedException {
		return trialsToRun.take();
	}
	
	public int getTrialsToRun() {
		return trialsToRun.size();
	}
	
	protected boolean hasTrialThatCanRun() {
		return hasTrialThatCanRun.get();
	}
	
	public long getTime() {
		return system.getTime();
	}

	protected Long getStartTime() {
		return startTime.get();
	}

	protected Long getSetupEndTime() {
		return setupEndTime.get();
	}

	protected Long getTrialEndTime() {
		return trialEndTime.get();
	}

	protected Long getRemoteEndTime() {
		return remoteEndTime.get();
	}

	protected void setStartTime(long p) {
		startTime.set(p);
	}

	protected void setSetupEndTime(long p) {
		setupEndTime.set(p);
	}

	protected void setTrialEndTime(long p) {
		trialEndTime.set(p);
	}

	protected void setRemoteEndTime(long p) {
		remoteEndTime.set(p);
	}

	protected int getTrialsToSetup() {
		return trialClasses.size();
	}
	
	protected int getTrialsDone() {
		return trialsDone.get();
	}

	protected Long getTestsDone() {
		return testsDone.get();
	}

	

	protected void addTrialsDone() {
		this.trialsDone.addAndGet(1);
	}

	protected void addTestsDone() {
		this.testsDone.addAndGet(1L);
	}
	
	public int getAllTestsCount() {
		return metadata.getAllTestsCount();
	}

	protected I_Tests4J_Log getLog() {
		return log;
	}

	protected Tests4J_ProcessInfo getSetupProcessInfo() {
		return setupProcessInfo;
	}

	protected Tests4J_ProcessInfo getTrialProcessInfo() {
		return trialProcessInfo;
	}

	protected Tests4J_ProcessInfo getRemoteProcessInfo() {
		return remoteProcessInfo;
	}


}