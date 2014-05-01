package org.adligo.tests4j.run.helpers;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.AfterTrial;
import org.adligo.tests4j.models.shared.BeforeTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.UseCaseScope;
import org.adligo.tests4j.models.shared.common.I_Immutable;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.DuplicatingPrintStream;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;

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
	/**
	 * @see Tests4J_Params#getRecordSeperateTrialCoverage()
	 */
	private Boolean recordSeperateTrialCoverage = null;
	/**
	 * @see Tests4J_Params#getRecordSeperateTestCoverage()
	 */
	private Boolean recordSeperateTestCoverage = null;
	private final String mainRecorderScope;
	
	
	public Tests4J_Memory(Tests4J_Params params) {
		
		trialClasses.addAll(params.getTrials());
		trialCount = trialClasses.size();
		systemExit = params.isExitAfterLastNotification();
		plugin = params.getCoveragePlugin();
		log = params.getLog();
		if (!LOADED_COMMON_CLASSES.get()) {
			synchronized (LOADED_COMMON_CLASSES) {
				if (!LOADED_COMMON_CLASSES.get()) {
					COMMON_CLASSES = getCommonClasses(log);
				}
				LOADED_COMMON_CLASSES.set(true);
			}
		}
		if (log.isEnabled()) {
			System.setOut(new DuplicatingPrintStream(out, System.out));
			System.setErr(new DuplicatingPrintStream(out, System.err));
		} else {
			System.setOut(new PrintStream(out));
			System.setErr(new PrintStream(out));
		}
		int threads = params.getThreadPoolSize();
		runService = Executors.newFixedThreadPool(threads);
		
		recordSeperateTrialCoverage = params.getRecordSeperateTrialCoverage();
		recordSeperateTestCoverage = params.getRecordSeperateTestCoverage();

		long now = System.currentTimeMillis();
		mainRecorderScope = I_CoverageRecorder.TESTS4J_ + now + I_CoverageRecorder.RECORDER;
	}
	
	/**
	 * this is the set of common classes that 
	 * @return
	 */
	private List<Class<?>> getCommonClasses(I_Tests4J_Logger log) {
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
				if (log.isEnabled()) {
					log.log("Loaded Class " + c.getName());
				}
			} catch (ClassNotFoundException x) {
				throw new RuntimeException(x);
			}
		}
		return Collections.unmodifiableList(toRet);
	}

	public Class<? extends I_AbstractTrial> pollTrialClasses() {
		return trialClasses.poll();
	}
	
	public synchronized void add(TrialDescription p) {
		allDescriptions.add(p);
		if (log.isEnabled()) {
			log.log("TrialDescription " + p.getTrialName() +
					" has " + p.getTestMethodsSize());
		}
		if (p.isTrialCanRun()) {
			descriptionsToRun.add(p);
		}
	}
	
	
	public TrialDescription pollDescriptions() {
		return descriptionsToRun.poll();
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
	
	public synchronized void addRecorder(String p, I_CoverageRecorder recorder) {
		recorders.put(p, recorder);
	}
	
	public synchronized I_CoverageRecorder getRecorder(String p) {
		return recorders.get(p);
	}
	
	public synchronized I_CoverageRecorder getMainRecorder() {
		return recorders.get(mainRecorderScope);
	}
	
	public String getThreadLocalOutput() {
		return out.get().toString();
	}

	public ExecutorService getRunService() {
		return runService;
	}

	public Iterator<TrialDescription> getAllDescriptions() {
		return allDescriptions.iterator();
	}
	
	public int getRunnableTrialDescriptions() {
		int toRet = 0;
		Iterator<TrialDescription> it = allDescriptions.iterator();
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

	public I_Tests4J_Logger getLog() {
		return log;
	}
	
	public Boolean getRecordSeperateTrialCoverage() {
		return recordSeperateTrialCoverage;
	}
	public Boolean getRecordSeperateTestCoverage() {
		return recordSeperateTestCoverage;
	}
	public synchronized void setRecordTrialCoverage(boolean recordTrialCoverage) {
		this.recordSeperateTrialCoverage = recordTrialCoverage;
	}
	public synchronized void setRecordTestCoverage(boolean recordTestCoverage) {
		this.recordSeperateTestCoverage = recordTestCoverage;
	}

	public String getMainRecorderScope() {
		return mainRecorderScope;
	}
}