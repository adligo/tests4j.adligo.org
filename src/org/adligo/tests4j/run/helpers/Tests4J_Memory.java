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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_Tests4J_System;
import org.adligo.tests4j.models.shared.system.Tests4J_ListenerDelegator;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_Trial;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
import org.adligo.tests4j.run.discovery.TrialDescription;

/**
 * Instances of this class represent the main
 * memory of the Tests4J framework, so if are working
 * on the framework and need to share some objects
 * put them in here.
 * 
 * @author scott
 *
 */
public class Tests4J_Memory {
	private ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>> trialClasses = 
			new ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>>();
	private AtomicBoolean metaTrial = new AtomicBoolean(false);
	private AtomicBoolean hasTrialThatCanRun = new AtomicBoolean(false);
	private CopyOnWriteArraySet<String> tests;
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
	private ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>> trialsToRun = new ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>>();
	
	private ConcurrentHashMap<String,AtomicInteger> trialRuns = new ConcurrentHashMap<String,AtomicInteger>();
	
	private I_Tests4J_CoverageRecorder mainRecorder;
	
	private ConcurrentLinkedQueue<I_TrialResult> resultsBeforeMetadata = new ConcurrentLinkedQueue<I_TrialResult>();
	private int allTrialCount;
	private I_Tests4J_CoveragePlugin coveragePlugin;
	private I_Tests4J_Logger logger;
	private ThreadLocalOutputStream out;
	private CopyOnWriteArrayList<Tests4J_TrialsRunable> trialInstancesProcessors = 
			new CopyOnWriteArrayList<Tests4J_TrialsRunable>();

	
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
	private I_Tests4J_System system;
	private int trialThreadCount;
	private int setupThreadCount;
	private int remoteThreadCount;
	private AtomicLong startTime = new AtomicLong();
	private AtomicLong setupEndTime = new AtomicLong();
	private AtomicLong trialEndTime = new AtomicLong();
	private AtomicLong remoteEndTime = new AtomicLong();
	private AtomicLong trialsSetup = new AtomicLong();
	private AtomicLong trialsDone = new AtomicLong();
	private AtomicLong testsDone = new AtomicLong();
	
	private Tests4J_NotificationManager notifier;
	/**
	 * 
	 * @param params
	 * 
	 * @diagram sync on 7/21/2014 with Overview.seq 
	 */
	public Tests4J_Memory() {
		
	}
	
	protected void initialize(Tests4J_ParamsReader p) {
		trialThreadCount = p.getTrialThreadCount();
		//TODO pass remote count into the thread manager
		threadManager = new Tests4J_ThreadManager(trialThreadCount, 0, system, logger);
		
		List<Class<? extends I_AbstractTrial>> trials = p.getInstrumentedTrials();
		if (trials.size() == 0) {
			trials = p.getTrials();
		} 
		Class<? extends I_MetaTrial> meta = p.getMetaTrialClass();
		if (meta != null) {
			TrialDescriptionProcessor trialDescProcessor = new TrialDescriptionProcessor(this);
			trialDescProcessor.addTrialDescription(meta);
			trials.add(meta);
		}
		coveragePlugin =  p.getCoveragePlugin();
		
		for (Class<? extends I_AbstractTrial> clazz: trials) {
			if (I_MetaTrial.class.isAssignableFrom(clazz)) {
				metaTrial.set(true);
			}
		}
		allTrialCount = trials.size();
		trialClasses.addAll(trials);
		
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
		if (logger.isLogEnabled(Tests4J_Memory.class)) {
			logger.log("added trial description for " + name);
		}
	}
	public TrialDescription getTrialDescription(String name) {
		return trialDescriptions.get(name);
	}
	
	public int incrementTrialRun(String trialName) {
		if (logger.isLogEnabled(Tests4J_Memory.class)) {
			logger.log("incrementTrialRun for " + trialName);
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
	
	public String getThreadLocalOutput() {
		return out.get().toString();
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

	public I_Tests4J_Logger getLogger() {
		return logger;
	}

	public List<Tests4J_TrialsRunable> getTrialInstancesProcessors() {
		return new ArrayList<Tests4J_TrialsRunable>(trialInstancesProcessors);
	}

	public void setTrialInstancesProcessors(
			Collection<Tests4J_TrialsRunable> p) {
		trialInstancesProcessors.clear();
		trialInstancesProcessors.addAll(p);
	}
	
	public void addTrialInstancesProcessors(Tests4J_TrialsRunable p) {
		trialInstancesProcessors.add(p);
	}
	
	
	public boolean hasTestsFilter() {
		if (tests != null) {
			return true;
		}
		return false;
	}
	
	public boolean runTest(Class<?> trial, String method) {
		String trialMethod = trial.getName() + "." + method;
		if (tests.contains(trialMethod)) {
			return true;
		}
		return false;
	}

	public Tests4J_ListenerDelegator getListener() {
		return listener;
	}

	public Tests4J_ThreadManager getThreadManager() {
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

	public I_EvaluatorLookup getEvaluationLookup() {
		return evaluationLookup;
	}

	public I_Tests4J_System getSystem() {
		return system;
	}

	public I_Tests4J_Listener getReporter() {
		return reporter;
	}

	protected void setReporter(I_Tests4J_Listener reporter) {
		this.reporter = reporter;
	}

	protected void setListener(I_Tests4J_Listener p) {
		listener = new Tests4J_ListenerDelegator(p, logger);
	}
	/**
	 * paramTests
	 * @return
	 */
	public CopyOnWriteArraySet<String> getTests() {
		return tests;
	}

	protected void setLogger(I_Tests4J_Logger logger) {
		this.logger = logger;
	}

	protected void setThreadLocalOutput(ThreadLocalOutputStream p) {
		this.out = p;
	}

	public int getTrialThreadCount() {
		return trialThreadCount;
	}

	public Tests4J_NotificationManager getNotifier() {
		return notifier;
	}

	protected void setSystem(I_Tests4J_System system) {
		this.system = system;
	}

	
	protected void addTrialToRun(Class<? extends I_AbstractTrial> p) {
		trialsToRun.add(p);
		hasTrialThatCanRun.set(true);
	}
	
	protected Class<? extends I_AbstractTrial> pollTrialsToRun() {
		return trialsToRun.poll();
	}
	
	protected boolean hasTrialThatCanRun() {
		return hasTrialThatCanRun.get();
	}

	protected int getSetupThreadCount() {
		return setupThreadCount;
	}

	protected int getRemoteThreadCount() {
		return remoteThreadCount;
	}

	protected void setSetupThreadCount(int setupThreadCount) {
		this.setupThreadCount = setupThreadCount;
	}

	protected void setRemoteThreadCount(int remoteThreadCount) {
		this.remoteThreadCount = remoteThreadCount;
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

	protected Long getTrialsSetup() {
		return trialsSetup.get();
	}

	protected Long getTrialsDone() {
		return trialsDone.get();
	}

	protected Long getTestsDone() {
		return testsDone.get();
	}

	protected void addTrialSetup() {
		this.trialsSetup.addAndGet(1L);
	}

	protected void addTrialsDone() {
		this.trialsDone.addAndGet(1L);
	}

	protected void addTestsDone() {
		this.testsDone.addAndGet(1L);
	}
	
	public int getAllTestsCount() {
		return metadata.getAllTestsCount();
	}
}