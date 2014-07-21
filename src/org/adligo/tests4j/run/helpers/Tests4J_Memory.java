package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.common.I_Immutable;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_System;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.TrialRunListenerDelegator;
import org.adligo.tests4j.models.shared.trials.AfterTrial;
import org.adligo.tests4j.models.shared.trials.BeforeTrial;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.IgnoreTest;
import org.adligo.tests4j.models.shared.trials.IgnoreTrial;
import org.adligo.tests4j.models.shared.trials.PackageScope;
import org.adligo.tests4j.models.shared.trials.SourceFileScope;
import org.adligo.tests4j.models.shared.trials.TrialTypeAnnotation;
import org.adligo.tests4j.models.shared.trials.UseCaseScope;
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
	private static AtomicBoolean LOADED_COMMON_CLASSES = new AtomicBoolean(false);
	
	/**
	 * these are enums, interfaces and other classes
	 * that have NO methods or runtime code 
	 * which are loaded by this class's
	 * parent classloader so that wierd .
	 * These are stored here for testing of this class.
	 */
	public static List<Class<?>> COMMON_CLASSES;
	
	private ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>> trialClasses = 
			new ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>>();
	private AtomicBoolean metaTrial = new AtomicBoolean(false);
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
	
	private ConcurrentHashMap<String,AtomicInteger> trialRuns = new ConcurrentHashMap<String,AtomicInteger>();
	
	private I_CoverageRecorder mainRecorder;
	
	private ConcurrentLinkedQueue<I_TrialResult> resultsBeforeMetadata = new ConcurrentLinkedQueue<I_TrialResult>();
	private int allTrialCount;
	private I_CoveragePlugin coveragePlugin;
	private I_Tests4J_Logger logger;
	private ThreadLocalOutputStream out;
	private CopyOnWriteArrayList<TrialInstancesProcessor> trialInstancesProcessors = 
			new CopyOnWriteArrayList<TrialInstancesProcessor>();

	
	/**
	 * a safe wrapper around the I_TrialRunListener passed to Test4J.run
	 * this should never be null during the trial run
	 */
	private TrialRunListenerDelegator listener;
	
	/**
	 * this is the reporter that tests4j uses to report it's own
	 * progress, and should never be null during the trial run
	 */
	private I_TrialRunListener reporter;
	
	private Tests4J_Manager threadManager;
	private ArrayBlockingQueue<I_TrialRunMetadata> metaTrialDataBlock = new ArrayBlockingQueue<I_TrialRunMetadata>(1);
	private TrialDescription metaTrialDescription;
	private AtomicBoolean ranMetaTrial = new AtomicBoolean(false);
	private boolean hasRemoteDelegation = false;
	private I_EvaluatorLookup evaluationLookup;
	private I_System system;
	
	/**
	 * 
	 * @param params
	 * 
	 * @diagram sync on 5/21/2014 with Overview.seq 
	 */
	public Tests4J_Memory() {
		
	}
	
	/**
	 * this is the set of common classes that 
	 * @return
	 */
	private List<Class<?>> getCommonClasses(I_Tests4J_Logger log) {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		//start with common
		toRet.add(TrialType.class);
		
		toRet.add(I_Immutable.class);
		
		//end with shared/
		toRet.add(AfterTrial.class);
		toRet.add(BeforeTrial.class);
		toRet.add(IgnoreTest.class);
		toRet.add(IgnoreTrial.class);
		toRet.add(PackageScope.class);
		toRet.add(SourceFileScope.class);
		toRet.add(TrialTypeAnnotation.class);
		toRet.add(UseCaseScope.class);
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		for (Class<?> c: toRet) {
			try {
				cl.loadClass(c.getName());
				if (log.isLogEnabled(Tests4J_Memory.class)) {
					log.log("Loaded Class " + c.getName());
				}
			} catch (ClassNotFoundException x) {
				throw new RuntimeException(x);
			}
		}
		return Collections.unmodifiableList(toRet);
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
	 * @diagram_sync with Overview.seq on 7/5/2014
	 * @param name
	 * @param p
	 */
	public synchronized void addTrialDescription(String name, TrialDescription p) {
		allTrialDescriptions.add(p);
		if (p.isTrialCanRun() && p.getType() == TrialType.MetaTrial) {
			metaTrialDescription = p;
		}
		trialDescriptions.put(name, p);
		if (!trialRuns.containsKey(name)) {
			trialRuns.put(name, new AtomicInteger(0));
		}
	}
	public synchronized TrialDescription getTrialDescription(String name) {
		return trialDescriptions.get(name);
	}
	
	public synchronized int incrementTrialRun(String trialName) {
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
	public I_CoveragePlugin getCoveragePlugin() {
		return coveragePlugin;
	}
	
	/**
	 * 
	 * @param p
	 * @param recorder
	 * 
	 * @diagram Overview.seq sync on 7/5/2014
	 */
	public synchronized void setMainRecorder(I_CoverageRecorder recorder) {
		mainRecorder = recorder;
	}
	
	/**
	 * 
	 * @return the recorder that is recording all of the coverage
	 * for the various trials/tests.
	 * 
	 * @diagram Overview.seq sync on 5/26/2014
	 */
	public I_CoverageRecorder getMainRecorder() {
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
			if (desc.isTrialCanRun()) {
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

	public List<TrialInstancesProcessor> getTrialInstancesProcessors() {
		return new ArrayList<TrialInstancesProcessor>(trialInstancesProcessors);
	}

	public void setTrialInstancesProcessors(
			Collection<TrialInstancesProcessor> p) {
		trialInstancesProcessors.clear();
		trialInstancesProcessors.addAll(p);
	}
	
	public void addTrialInstancesProcessors(TrialInstancesProcessor p) {
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

	public TrialRunListenerDelegator getListener() {
		return listener;
	}

	public Tests4J_Manager getThreadManager() {
		return threadManager;
	}

	/**
	 * @diagram_sync on 5/26/2014 with Overview.seq
	 * @param md
	 */
	public void setMetaTrialData(I_TrialRunMetadata md) {
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

	public I_System getSystem() {
		return system;
	}

	public I_TrialRunListener getReporter() {
		return reporter;
	}

	protected void setReporter(I_TrialRunListener reporter) {
		this.reporter = reporter;
	}

	protected void setListener(TrialRunListenerDelegator listener) {
		this.listener = listener;
	}

	protected void setThreadManager(Tests4J_Manager threadManager) {
		this.threadManager = threadManager;
	}

	protected void setTrialClasses(
			Collection<Class<? extends I_AbstractTrial>> p) {
		
		for (Class<? extends I_AbstractTrial> clazz: p) {
			if (I_MetaTrial.class.isAssignableFrom(clazz)) {
				metaTrial.set(true);
			}
		}
		allTrialCount = p.size();
		trialClasses.addAll(p);
	}

	protected void setEvaluationLookup(I_EvaluatorLookup p) {
		evaluationLookup = p;
	}

	/**
	 * paramTests
	 * @return
	 */
	public CopyOnWriteArraySet<String> getTests() {
		return tests;
	}

	protected void setTests(Collection<String> p) {
		tests.clear();
		tests.addAll(p);
	}

	protected void setLogger(I_Tests4J_Logger logger) {
		this.logger = logger;
	}

	protected void setCoveragePlugin(I_CoveragePlugin coveragePlugin) {
		this.coveragePlugin = coveragePlugin;
	}

	protected void setThreadLocalOutput(ThreadLocalOutputStream p) {
		this.out = p;
	}

}