package org.adligo.tests4j.run.helpers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class manages all child threads 
 * created by Tests4J or custom Trials.
 * 
 * @author scott
 *
 */
public class Tests4J_ThreadManager {
	private Tests4J_ThreadFactory trialFactory;
	private Tests4J_ThreadFactory testFactory;
	private ExecutorService trialRunService;
	private List<Future<?>> trialFutures = new CopyOnWriteArrayList<Future<?>>();
	private Map<ExecutorService, Future<?>> testRuns = new ConcurrentHashMap<ExecutorService, Future<?>>();
	
	
	private boolean jvmExit = false;
	public Tests4J_ThreadManager(boolean pJvmExit, int trialThreads) {
		jvmExit = pJvmExit;
		trialFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TRIAL_THREAD_NAME);
		testFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TEST_THREAD_NAME, trialFactory.getGroup());
		trialRunService = Executors.newFixedThreadPool(trialThreads, trialFactory);
	}
	
	/**
	 * shutdown all of the threads
	 */
	public void shutdown() {
		Set<Entry<ExecutorService, Future<?>>> testRunEntries = testRuns.entrySet();
		for (Entry<ExecutorService, Future<?>> e: testRunEntries) {
			ExecutorService es =  e.getKey();
			es.shutdownNow();
			Future<?> future = e.getValue();
			if (!future.isDone()) {
				future.cancel(true);
			}
		}
		for (Future<?> future: trialFutures) {
			if (!future.isDone()) {
				future.cancel(true);
			}
		}
		trialRunService.shutdownNow();
		if (jvmExit) {
			System.exit(0);
		}
	}
	
	public ExecutorService getTrialRunService() {
		return trialRunService;
	}
	
	public ExecutorService createNewTestRunService() {
		ExecutorService toRet = Executors.newFixedThreadPool(1, testFactory);
		return toRet;
	}
	
	public void setTestRunFuture(ExecutorService exe, Future<?> future) {
		testRuns.put(exe,  future);
	}
	
	public void addTrialFuture(Future<?> future) {
		trialFutures.add(future);
	}
}
