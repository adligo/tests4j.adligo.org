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

import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.run.remote.RemoteRunnerStateEnum;
import org.adligo.tests4j.run.remote.Tests4J_RemoteRunner;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * This class manages all child threads 
 * created by Tests4J or custom Trials.
 * 
 * @author scott
 *
 */
public class Tests4J_ThreadManager implements I_Tests4J_ThreadManager {
	private Tests4J_ThreadFactory trialFactory;
	private Tests4J_ThreadFactory testFactory;
	private Tests4J_ThreadFactory setupFactory;
	private Tests4J_ThreadFactory remoteFactory;
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
	private I_System system;
	private I_Tests4J_Log reporter;
	
	
	public Tests4J_ThreadManager(I_Tests4J_Memory memory, I_System pSystem, I_Tests4J_Log pReporter) {
		system = pSystem;
		reporter = pReporter;
		testFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TEST_THREAD_NAME,reporter);
		
		int setupThreads = memory.getSetupThreadCount();
		if (setupThreads >= 1) {
			setupFactory  = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.SETUP_THREAD_NAME,reporter);
			setupService = Executors.newFixedThreadPool(setupThreads, setupFactory);
		}
		int trialThreads = memory.getTrialThreadCount();
		if (trialThreads >= 1) {
			trialFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TRIAL_THREAD_NAME,reporter);
			trialRunService = Executors.newFixedThreadPool(trialThreads, trialFactory);
		}
		int remoteThreads = memory.getRemoteThreadCount();
		if (remoteThreads >= 1) {
			remoteFactory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TRIAL_THREAD_NAME,reporter);
			remoteService = Executors.newFixedThreadPool(remoteThreads, remoteFactory);
		}
		
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
		shutdownLocal();
		
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
		system.exitJvm(0);	
	}


	public void shutdownTestThreads() {
		for (ExecutorService testService: testExecutorServices) {
			testService.shutdownNow();
		}
	}
	
	public ExecutorService getTrialRunService() {
		return trialRunService;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#createNewTestRunService()
	 */
	@Override
	public ExecutorService createNewTestRunService() {
		ExecutorService toRet = Executors.newSingleThreadExecutor(testFactory);
		testExecutorServices.add(toRet);
		return toRet;
	}
	
	public void setTestRunFuture(ExecutorService exe, Future<?> future) {
		testRuns.put(exe,  future);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addTrialFuture(java.util.concurrent.Future)
	 */
	@Override
	public void addTrialFuture(Future<?> future) {
		trialFutures.add(future);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addSetupFuture(java.util.concurrent.Future)
	 */
	@Override
	public void addSetupFuture(Future<?> future) {
		setupFutures.add(future);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addRemoteFuture(java.util.concurrent.Future)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addRemoteRunner(org.adligo.tests4j.run.remote.Tests4J_RemoteRunner)
	 */
	@Override
	public void addRemoteRunner(Tests4J_RemoteRunner p) {
		remoteRunners.add(p);
	}


	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#getSetupService()
	 */
	@Override
	public ExecutorService getSetupService() {
		return setupService;
	}


	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#getRemoteService()
	 */
	@Override
	public ExecutorService getRemoteService() {
		return remoteService;
	}


	@Override
	public void shutdownLocal() {
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
		
		if (remoteService == null) {
			system.exitJvm(0);	
		}
	}
}
