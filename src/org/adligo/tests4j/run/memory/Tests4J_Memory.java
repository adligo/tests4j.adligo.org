package org.adligo.tests4j.run.memory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.run.common.I_Tests4J_Memory;
import org.adligo.tests4j.run.common.I_Tests4J_ThreadManager;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.run.discovery.TrialDescriptionProcessor;
import org.adligo.tests4j.run.discovery.TrialQueueDecisionTree;
import org.adligo.tests4j.run.helpers.Tests4J_NotificationManager;
import org.adligo.tests4j.run.helpers.Tests4J_PhaseInfoParamsMutant;
import org.adligo.tests4j.run.helpers.Tests4J_PhaseOverseer;
import org.adligo.tests4j.shared.asserts.reference.I_Dependency;
import org.adligo.tests4j.shared.asserts.reference.I_ReferenceGroup;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Listener;
import org.adligo.tests4j.system.shared.api.I_Tests4J_ProgressParams;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Selection;
import org.adligo.tests4j.system.shared.api.I_Tests4J_SourceInfoParams;
import org.adligo.tests4j.system.shared.api.Tests4J_DelegateProgressMonitor;
import org.adligo.tests4j.system.shared.api.Tests4J_ListenerDelegator;
import org.adligo.tests4j.system.shared.api.Tests4J_SourceInfoParamsDelegate;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrialParams;

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

	private I_MetaTrialParams metaTrialParams_;
	private I_Tests4J_CoverageRecorder mainRecorder;
	
	private ConcurrentLinkedQueue<I_TrialResult> resultsBeforeMetadata = new ConcurrentLinkedQueue<I_TrialResult>();
	private int allTrialCount;
	private I_Tests4J_CoveragePlugin coveragePlugin;
	private final I_Tests4J_Log log;
	private Tests4J_PhaseOverseer setupProcessInfo;
	private Tests4J_PhaseOverseer trialProcessInfo;
	private Tests4J_PhaseOverseer remoteProcessInfo;
	private Tests4J_SourceInfoParamsDelegate sourceInfoParamsDelegate_;
	
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
	private TrialQueueDecisionTree trialQueueDecisionTree;
	private I_TrialRunMetadata metadata;
	private AtomicBoolean ranMetaTrial = new AtomicBoolean(false);
	private boolean hasRemoteDelegation = false;
	private I_EvaluatorLookup evaluationLookup;
	private I_System system;
	private AtomicLong startTime = new AtomicLong();
	private AtomicLong setupEndTime = new AtomicLong();
	private AtomicLong trialEndTime = new AtomicLong();
	private AtomicLong remoteEndTime = new AtomicLong();
	private Tests4J_NotificationManager notifier;
	private ConcurrentHashMap<String,I_ReferenceGroup> dependencyGroupInstances_ =
			new ConcurrentHashMap<String,I_ReferenceGroup>();
	/**
	 * 
	 * @param params
	 * 
	 * @diagram sync on 7/21/2014 with Overview.seq 
	 */
	public Tests4J_Memory(I_Tests4J_Log logIn) {
		log = logIn;
	}
	
	public synchronized void initialize(Tests4J_ParamsReader p) {
		
		
		List<Class<? extends I_AbstractTrial>> trials = p.getTrials();
		
		allTrialCount = trials.size();
		I_Tests4J_ProgressParams progressMonitor = p.getProgressMonitor();
		long notificationInterval = progressMonitor.getNotificationInterval();
		
		Tests4J_PhaseInfoParamsMutant trialPhaseInfo = new Tests4J_PhaseInfoParamsMutant();
		trialPhaseInfo.setProcessName(I_PhaseState.TRIALS);
		int trialThreads = p.getTrialThreadCount();
		trialPhaseInfo.setThreadCount(trialThreads);
		trialPhaseInfo.setCount(allTrialCount);
		trialPhaseInfo.setNotificationInterval(notificationInterval);
		trialPhaseInfo.setTime(getSystem());
		trialProcessInfo = new Tests4J_PhaseOverseer(trialPhaseInfo);
		
		threadManager = new Tests4J_ThreadManager(system, log);
		
		Class<? extends I_MetaTrial> meta = p.getMetaTrialClass();
		if (meta != null) {
			TrialDescriptionProcessor trialDescProcessor = new TrialDescriptionProcessor(this);
			TrialDescription td = trialDescProcessor.createAndRemberNotRunnableTrials(meta);
			if (td.isRunnable()) {
				trials.add(meta);
				metaTrial.set(true);
				//allTrialCount now includes the meta trial
				allTrialCount = trials.size();
			}
		}
		coveragePlugin =  p.getCoveragePlugin();
		boolean hasCoveragePlugin = (coveragePlugin != null);
		trialQueueDecisionTree = new TrialQueueDecisionTree(allTrialCount, log, hasCoveragePlugin);
		
		Tests4J_PhaseInfoParamsMutant setupPhaseInfo = new Tests4J_PhaseInfoParamsMutant();
		setupPhaseInfo.setProcessName(I_PhaseState.SETUP);
		int setupThreads = p.getSetupThreadCount();
		setupPhaseInfo.setThreadCount(setupThreads);
		setupPhaseInfo.setCount(allTrialCount);
		setupPhaseInfo.setNotificationInterval(notificationInterval);
		setupPhaseInfo.setTime(getSystem());
		setupProcessInfo = new Tests4J_PhaseOverseer(setupPhaseInfo);

		//todo when remote functionality is added
		Tests4J_PhaseInfoParamsMutant remotePhaseInfo = new Tests4J_PhaseInfoParamsMutant();
		remotePhaseInfo.setProcessName(I_PhaseState.REMOTES);
		remotePhaseInfo.setThreadCount(0);
		remotePhaseInfo.setCount(0);
		remotePhaseInfo.setNotificationInterval(notificationInterval);
		remotePhaseInfo.setTime(getSystem());
		remoteProcessInfo = new Tests4J_PhaseOverseer(remotePhaseInfo);
		
		threadManager.setupSetupProcess(setupThreads);
		threadManager.setupTrialsProcess(trialThreads);
		//threadManager.setupRemoteProcess(remoteProcessInfo);
		
		trialClasses.addAll(trials);
		tests = new CopyOnWriteArraySet<I_Tests4J_Selection>(p.getTests());
		
		evaluationLookup = p.getEvaluatorLookup();
		notifier = new Tests4J_NotificationManager(this);
		metaTrialParams_ = p.getMetaTrialParams();
		
		I_Tests4J_SourceInfoParams siParams = p.getSourceInfoParams();
		if (siParams != null) {
			sourceInfoParamsDelegate_ = new Tests4J_SourceInfoParamsDelegate(siParams, log);
		}
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

	public TrialDescription getTrialDescription(String name) {
		return trialDescriptions.get(name);
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
	

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_Memory#getLogger()
	 */
	@Override
	public I_Tests4J_Log getLog() {
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

	public void setReporter(I_Tests4J_Listener reporter) {
		this.reporter = reporter;
	}

	public void setListener(I_Tests4J_Listener p) {
		listener = new Tests4J_ListenerDelegator(p, log);
	}
	/**
	 * paramTests
	 * @return
	 */
	public CopyOnWriteArraySet<I_Tests4J_Selection> getTests() {
		return tests;
	}

	public Tests4J_NotificationManager getNotifier() {
		return notifier;
	}

	public void setSystem(I_System system) {
		this.system = system;
	}
	
	public boolean hasTrialThatCanRun() {
		return hasTrialThatCanRun.get();
	}
	
	public long getTime() {
		return system.getTime();
	}

	public Long getStartTime() {
		return startTime.get();
	}

	public Long getSetupEndTime() {
		return setupEndTime.get();
	}

	public Long getTrialEndTime() {
		return trialEndTime.get();
	}

	public Long getRemoteEndTime() {
		return remoteEndTime.get();
	}

	public void setStartTime(long p) {
		startTime.set(p);
	}

	public void setSetupEndTime(long p) {
		setupEndTime.set(p);
	}

	public void setTrialEndTime(long p) {
		trialEndTime.set(p);
	}

	public void setRemoteEndTime(long p) {
		remoteEndTime.set(p);
	}

	public int getTrialsToSetup() {
		return trialClasses.size();
	}

	public int getAllTestsCount() {
		return metadata.getAllTestsCount();
	}

	public Tests4J_PhaseOverseer getSetupPhaseOverseer() {
		return setupProcessInfo;
	}

	public Tests4J_PhaseOverseer getTrialPhaseOverseer() {
		return trialProcessInfo;
	}

	public Tests4J_PhaseOverseer getRemotePhaseOverseer() {
		return remoteProcessInfo;
	}

	@Override
	public boolean hasCoveragePlugin() {
		if (coveragePlugin != null) {
			return true;
		}
		return false;
	}

	public TrialQueueDecisionTree getTrialQueueDecisionTree() {
		return trialQueueDecisionTree;
	}

	public I_MetaTrialParams getMetaTrialParams() {
		return metaTrialParams_;
	}

	public I_ReferenceGroup getDependencyGroup(Class<?> c) {
		return dependencyGroupInstances_.get(c.getName());
	}
	
	public void putIfAbsent(Class<?> c, I_ReferenceGroup group) {
		dependencyGroupInstances_.putIfAbsent(c.getName(), group);
	}

	public I_Tests4J_SourceInfoParams getSourceInfoParams() {
		return sourceInfoParamsDelegate_;
	}


}