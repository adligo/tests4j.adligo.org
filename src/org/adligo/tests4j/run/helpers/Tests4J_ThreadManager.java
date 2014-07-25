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

import org.adligo.tests4j.models.shared.system.I_Tests4J_System;
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
public class Tests4J_ThreadManager {
	private Tests4J_ThreadFactory trialFactory;
	private Tests4J_ThreadFactory testFactory;
	private ExecutorService setupService;
	private ExecutorService trialRunService;
	private ExecutorService remoteService;
	
	private List<ExecutorService> testExecutorServices = new CopyOnWriteArrayList<ExecutorService>();
	private List<Future<?>> setupFutures = new CopyOnWriteArrayList<Future<?>>();
	private List<Future<?>> trialFutures = new CopyOnWriteArrayList<Future<?>>();
	private List<Future<?>> remoteFutures = new CopyOnWriteArrayList<Future<?>>();
	private CopyOnWriteArrayList<Tests4J_RemoteRunner> remoteRunners = 
			new CopyOnWriteArrayList<Tests4J_RemoteRunner>();
			
	private Map<ExecutorService, Future<?>> testRuns = new ConcurrentHashMap<ExecutorService, Future<?>>();
	private I_Tests4J_System system;
	private I_Tests4J_Logger reporter;
	
	
	public Tests4J_ThreadManager(int trialThreads, int remoteThreads, I_Tests4J_System pSystem, I_Tests4J_Logger pReporter) {
		system = pSystem;
		reporter = pReporter;
		trialFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TRIAL_THREAD_NAME,reporter);
		testFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TEST_THREAD_NAME,reporter);
		if (trialThreads >= 1) {
			//it could be 0 if there were only remotes
			setupService = Executors.newFixedThreadPool(trialThreads, trialFactory);
			trialRunService = Executors.newFixedThreadPool(trialThreads, trialFactory);
		}
		/**
		if (remoteThreads >= 0) {
			remoteService = Executors.newFixedThreadPool(remoteThreads, remoteThreads);
		}
		*/
	}
	
	
	public void shutdown() {
		if (reporter.isLogEnabled(Tests4J_ThreadManager.class)) {
			reporter.log("Tests4J_Manager.shutdown on " + ThreadLogMessageBuilder.getThreadWithGroupNameForLog());
		}
		if (remoteService != null) {
			//notify the remote runners first, as it will take some time
			//for the socket communication to occur.
			for (Tests4J_RemoteRunner remote: remoteRunners) {
				remote.shutdown();
			}
		}
		//if there is a setupService there is also a trialService
		if (setupService != null) {
			for (Future<?> future: setupFutures) {
				if (!future.isDone()) {
					future.cancel(true);
				}
			}
			setupService.shutdownNow();
			
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
			trialRunService.shutdownNow();
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
		
		
		//shutdown remotes
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
	
	public void addSetupFuture(Future<?> future) {
		setupFutures.add(future);
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


	protected ExecutorService getSetupService() {
		return setupService;
	}
}
