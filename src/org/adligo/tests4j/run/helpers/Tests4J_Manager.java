package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.adligo.tests4j.models.shared.system.I_System;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.run.remote.RemoteRunnerStateEnum;
import org.adligo.tests4j.run.remote.Tests4J_RemoteRunner;

/**
 * This class manages all child threads 
 * created by Tests4J or custom Trials.
 * 
 * @author scott
 *
 */
public class Tests4J_Manager {
	private Tests4J_ThreadFactory trialFactory;
	private Tests4J_ThreadFactory testFactory;
	private ExecutorService trialRunService;
	private List<ExecutorService> testExecutorServices = new CopyOnWriteArrayList<ExecutorService>();
	private List<Future<?>> trialFutures = new CopyOnWriteArrayList<Future<?>>();
	private List<Future<?>> remoteFutures = new CopyOnWriteArrayList<Future<?>>();
	private CopyOnWriteArrayList<Tests4J_RemoteRunner> remoteRunners = 
			new CopyOnWriteArrayList<Tests4J_RemoteRunner>();
			
	private Map<ExecutorService, Future<?>> testRuns = new ConcurrentHashMap<ExecutorService, Future<?>>();
	private I_System system;
	private I_Tests4J_Logger reporter;
	
	
	public Tests4J_Manager(int trialThreads, I_System pSystem, I_Tests4J_Logger pReporter) {
		system = pSystem;
		reporter = pReporter;
		trialFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TRIAL_THREAD_NAME,reporter);
		testFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TEST_THREAD_NAME,reporter);
		trialRunService = Executors.newFixedThreadPool(trialThreads, trialFactory);
	}
	
	
	public void shutdown() {
		if (reporter.isLogEnabled(Tests4J_Manager.class)) {
			reporter.log("Tests4J_Manager.shutdown on " + ThreadLogMessageBuilder.getThreadWithGroupNameForLog());
		}
		for (Tests4J_RemoteRunner remote: remoteRunners) {
			remote.shutdown();
		}
		shutdownTestThreads();
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
		boolean remotesShutdown = false;
		if (remoteRunners.size() == 0) {
			remotesShutdown = true;
		} else {
			while (!remotesShutdown) {
				for (Tests4J_RemoteRunner remote: remoteRunners) {
					if (remote.getState() != RemoteRunnerStateEnum.SHUTDOWN) {
						remotesShutdown = false;
						try {
							Thread.sleep(500);
						} catch (InterruptedException x) {
							x.printStackTrace();
						}
						break;
					}
					remotesShutdown = true;
				}
			}
		}
		trialRunService.shutdownNow();
		system.doSystemExit(0);	
	}


	public void shutdownTestThreads() {
		for (ExecutorService testService: testExecutorServices) {
			testService.shutdownNow();
		}
	}
	
	public ExecutorService getTrialRunService() {
		return trialRunService;
	}
	
	public ExecutorService createNewTestRunService() {
		ExecutorService toRet = Executors.newSingleThreadExecutor(testFactory);
		testExecutorServices.add(toRet);
		return toRet;
	}
	
	public void setTestRunFuture(ExecutorService exe, Future<?> future) {
		testRuns.put(exe,  future);
	}
	
	public void addTrialFuture(Future<?> future) {
		trialFutures.add(future);
	}
	
	public void addRemoteFuture(Future<?> future) {
		remoteFutures.add(future);
	}
	
	public List<Tests4J_RemoteRunner> getRemoteRunners() {
		return new ArrayList<Tests4J_RemoteRunner>(remoteRunners);
	}

	public void setRemoteRunners(
			Collection<Tests4J_RemoteRunner> p) {
		remoteRunners.clear();
		remoteRunners.addAll(p);
	}
	
	public void addRemoteRunner(Tests4J_RemoteRunner p) {
		remoteRunners.add(p);
	}
}
