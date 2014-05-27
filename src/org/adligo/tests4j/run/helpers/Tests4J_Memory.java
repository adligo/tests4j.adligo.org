package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.AfterTrial;
import org.adligo.tests4j.models.shared.BeforeTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.I_MetaTrial;
import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.UseCaseScope;
import org.adligo.tests4j.models.shared.common.I_Immutable;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

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
	private Set<String> tests;
	
	private ConcurrentLinkedQueue<TrialDescription> trialDescriptionsToRun = new ConcurrentLinkedQueue<TrialDescription>();
	private List<TrialDescription> allTrialDescriptions = new CopyOnWriteArrayList<TrialDescription>();
	private ConcurrentLinkedQueue<I_TrialResult> resultsBeforeMetadata = new ConcurrentLinkedQueue<I_TrialResult>();
	private int allTrialCount;
	private I_CoveragePlugin plugin;
	private Map<String, I_CoverageRecorder> recorders = new ConcurrentHashMap<String, I_CoverageRecorder>();
	private I_Tests4J_Reporter reporter;
	private final String mainRecorderScope;
	private ThreadLocalOutputStream out;
	private CopyOnWriteArrayList<TrialInstancesProcessor> trialInstancesProcessors = 
			new CopyOnWriteArrayList<TrialInstancesProcessor>();
	private I_TrialRunListener listener;
	private Tests4J_ThreadManager threadManager;
	private ArrayBlockingQueue<I_TrialRunMetadata> metaTrialDataBlock = new ArrayBlockingQueue<I_TrialRunMetadata>(1);
	private TrialDescription metaTrialDescription;
	/**
	 * 
	 * @param params
	 * 
	 * @diagram sync on 5/21/2014 with Overview.seq 
	 */
	public Tests4J_Memory(Tests4J_Params params, ThreadLocalOutputStream pOut, 
			I_TrialRunListener pListener,I_CoveragePlugin pPlugin) {
		out = pOut;
		listener = pListener;
		trialClasses.addAll(params.getTrials());
		Class<? extends I_MetaTrial> metaTrialClass = params.getMetaTrialClass();
		if (metaTrialClass != null) {
			trialClasses.add(metaTrialClass);
			metaTrial.set(true);
		}
		reporter = params.getReporter();
		
		if (reporter.isLogEnabled(Tests4J_Memory.class)) {
			reporter.log("Starting thread manager with " + params.getTrialThreadCount());
		}
		/**
		 * @diagram sync //TODO
		 */
		threadManager = new Tests4J_ThreadManager(
				params.isExitAfterLastNotification(), 
				params.getTrialThreadCount(),
				params.getExitor());
		
		Set<String> pTests = params.getTests();
		if (pTests.size() >= 1) {
			tests = new CopyOnWriteArraySet<String>();
			tests.addAll(pTests);
			tests = Collections.unmodifiableSet(tests);
		}
		allTrialCount = trialClasses.size();
		plugin = pPlugin;
		if (!LOADED_COMMON_CLASSES.get()) {
			synchronized (LOADED_COMMON_CLASSES) {
				if (!LOADED_COMMON_CLASSES.get()) {
					COMMON_CLASSES = getCommonClasses(reporter);
				}
				LOADED_COMMON_CLASSES.set(true);
			}
		}
		

		long now = System.currentTimeMillis();
		mainRecorderScope = I_CoverageRecorder.TESTS4J_ + now + I_CoverageRecorder.RECORDER;
	}
	
	/**
	 * this is the set of common classes that 
	 * @return
	 */
	private List<Class<?>> getCommonClasses(I_Tests4J_Reporter log) {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		//start with common
		toRet.add(TrialTypeEnum.class);
		
		toRet.add(I_Immutable.class);
		
		//end with shared/
		toRet.add(AfterTrial.class);
		toRet.add(BeforeTrial.class);
		toRet.add(IgnoreTest.class);
		toRet.add(IgnoreTrial.class);
		toRet.add(PackageScope.class);
		toRet.add(SourceFileScope.class);
		toRet.add(TrialType.class);
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
	 * Add the TrialDescription to allTrialDescriptions
	 * as well as trialDescriptionsToRun (if the 
	 * TrialDescription.isTrialCanRun is true).
	 * 
	 * @param p the description of the Trial
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void add(TrialDescription p) {
		allTrialDescriptions.add(p);
		if (reporter.isLogEnabled(Tests4J_Memory.class)) {
			reporter.log("TrialDescription " + p.getTrialName() +
					" has " + p.getTestMethodsSize());
		}
		if (p.isTrialCanRun()) {
			if (p.getType() != TrialTypeEnum.MetaTrial) {
				trialDescriptionsToRun.add(p);
			} else {
				metaTrialDescription = p;
			}
		}
		if (reporter.isLogEnabled(Tests4J_Memory.class)) {
			reporter.log("TrialDescriptions counts " + trialDescriptionsToRun.size() +
					"/" + allTrialDescriptions.size());
		}
	}
	
	/**
	 * Polling is used for thread collaboration.
	 * 
	 * @return the next TrialDescription in the queue.
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public TrialDescription pollDescriptions() {
		return trialDescriptionsToRun.poll();
	}
	
	public synchronized void add(I_TrialResult p) {
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
	public I_CoveragePlugin getPlugin() {
		return plugin;
	}
	
	/**
	 * 
	 * @param p
	 * @param recorder
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public void addRecorder(String p, I_CoverageRecorder recorder) {
		recorders.put(p, recorder);
	}
	
	/**
	 * 
	 * @param p
	 * @return a recorder that may be specific to a 
	 * particular trial.
	 * 
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public I_CoverageRecorder getRecorder(String p) {
		return recorders.get(p);
	}
	
	/**
	 * 
	 * @return the recorder that is recording all of the coverage
	 * for the various trials/tests.
	 * 
	 * @diagram Overview.seq sync on 5/26/2014
	 */
	public I_CoverageRecorder getMainRecorder() {
		return recorders.get(mainRecorderScope);
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

	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}

	public String getMainRecorderScope() {
		return mainRecorderScope;
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

	public I_TrialRunListener getListener() {
		return listener;
	}

	public Tests4J_ThreadManager getThreadManager() {
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
		for (TrialDescription td: allTrialDescriptions) {
			if (td.isIgnored()) {
				toRet++;
			}
		}
		return toRet;
	}
}