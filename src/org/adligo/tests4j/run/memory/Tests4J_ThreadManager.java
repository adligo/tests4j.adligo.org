package org.adligo.tests4j.run.memory;

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

import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.run.common.I_RemoteRunner;
import org.adligo.tests4j.run.common.I_ThreadManager;
import org.adligo.tests4j.run.helpers.ThreadLogMessageBuilder;
import org.adligo.tests4j.run.remote.RemoteRunnerStateEnum;
import org.adligo.tests4j.run.remote.Tests4J_RemoteRunner;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * This class manages all child threads 
 * created by Tests4J or custom Trials.
 * 
 * @author scott
 *
 */
public class Tests4J_ThreadManager implements I_ThreadManager {
	private Tests4J_ThreadFactory tests4jFactory_;
	private Tests4J_ThreadFactory trialFactory_;
	private Tests4J_ThreadFactory testFactory_;
	private Tests4J_ThreadFactory setupFactory_;
	private Tests4J_ThreadFactory remoteFactory_;
	private ExecutorService tests4jService_;
	private ExecutorService setupService_;
	private ExecutorService trialRunService_;
	private ExecutorService remoteService_;
	
	private List<ExecutorService> testExecutorServices_ = new CopyOnWriteArrayList<ExecutorService>();
	private List<Future<?>> setupFutures_ = new CopyOnWriteArrayList<Future<?>>();
	private List<Future<?>> trialFutures_ = new CopyOnWriteArrayList<Future<?>>();
	private List<Future<?>> remoteFutures_ = new CopyOnWriteArrayList<Future<?>>();
	private CopyOnWriteArrayList<I_RemoteRunner> remoteRunners_ = 
			new CopyOnWriteArrayList<I_RemoteRunner>();
			
	private Map<ExecutorService, Future<?>> testRuns_ = new ConcurrentHashMap<ExecutorService, Future<?>>();
	private I_System system_;
	private I_Tests4J_Log log_;
	
	
	public Tests4J_ThreadManager(I_System systemIn, I_Tests4J_Log logIn) {
		if (systemIn == null) {
			throw new IllegalArgumentException("" + this.getClass().getSimpleName() + " requires a system.");
		}
		system_ = systemIn;
		if (logIn == null) {
			throw new IllegalArgumentException("" + this.getClass().getSimpleName() + " requires a log.");
		}
		log_ = logIn;
		tests4jFactory_ = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.THREAD_NAME, log_);
		tests4jService_ = Executors.newSingleThreadExecutor(tests4jFactory_);
		testFactory_ = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TEST_THREAD_NAME,log_);

	}
	
	public void setupSetupProcess(int threadCount) {
		setupFactory_  = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.SETUP_THREAD_NAME,log_);
		setupService_ = Executors.newFixedThreadPool(threadCount, setupFactory_);
	}
	
	public void setupTrialsProcess(int threadCount) {
		trialFactory_ = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TRIAL_THREAD_NAME,log_);
		trialRunService_ = Executors.newFixedThreadPool(threadCount, trialFactory_);
	}
	
	public void setupRemoteProcess(int threadCount) {
		remoteFactory_ = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.TRIAL_THREAD_NAME,log_);
		remoteService_ = Executors.newFixedThreadPool(threadCount, remoteFactory_);
	}
	
	public void shutdown() {
		if (log_.isLogEnabled(Tests4J_ThreadManager.class)) {
			log_.log("Tests4J_Manager.shutdown on " + ThreadLogMessageBuilder.getThreadWithGroupNameForLog());
		}
		
		if (remoteService_ != null) {
			//notify the remote runners first, as it will take some time
			//for the socket communication to occur.
			for (I_RemoteRunner remote: remoteRunners_) {
				remote.shutdown();
			}
		}
		shutdownLocal();
		
		boolean remotesShutdown = false;
		if (remoteRunners_.size() == 0) {
			remotesShutdown = true;
		} else {
			while (!remotesShutdown) {
				for (I_RemoteRunner remote: remoteRunners_) {
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
		system_.exitJvm(0);	
	}


	public void shutdownTestThreads() {
		for (ExecutorService testService: testExecutorServices_) {
			testService.shutdownNow();
		}
	}
	
	public ExecutorService getTrialRunService() {
		return trialRunService_;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#createNewTestRunService()
	 */
	@Override
	public ExecutorService createNewTestRunService() {
		ExecutorService toRet = Executors.newSingleThreadExecutor(testFactory_);
		testExecutorServices_.add(toRet);
		return toRet;
	}
	
	public void setTestRunFuture(ExecutorService exe, Future<?> future) {
		testRuns_.put(exe,  future);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addTrialFuture(java.util.concurrent.Future)
	 */
	@Override
	public void addTrialFuture(Future<?> future) {
		trialFutures_.add(future);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addSetupFuture(java.util.concurrent.Future)
	 */
	@Override
	public void addSetupFuture(Future<?> future) {
		setupFutures_.add(future);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addRemoteFuture(java.util.concurrent.Future)
	 */
	@Override
	public void addRemoteFuture(Future<?> future) {
		remoteFutures_.add(future);
	}
	
	public List<I_RemoteRunner> getRemoteRunners() {
		return new ArrayList<I_RemoteRunner>(remoteRunners_);
	}

	public void setRemoteRunners(
			Collection<I_RemoteRunner> p) {
		remoteRunners_.clear();
		remoteRunners_.addAll(p);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#addRemoteRunner(org.adligo.tests4j.run.remote.Tests4J_RemoteRunner)
	 */
	@Override
	public void addRemoteRunner(I_RemoteRunner p) {
		remoteRunners_.add(p);
	}


	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#getSetupService()
	 */
	@Override
	public ExecutorService getSetupService() {
		return setupService_;
	}


	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.helpers.I_Tests4J_ThreadManager#getRemoteService()
	 */
	@Override
	public ExecutorService getRemoteService() {
		return remoteService_;
	}


	@Override
	public void shutdownLocal() {
		//if there is a setupService there is also a trialService
		if (setupService_ != null) {
			for (Future<?> future: setupFutures_) {
				if (!future.isDone()) {
					future.cancel(true);
				}
			}
			setupService_.shutdownNow();
			
			shutdownTestThreads();
			Set<Entry<ExecutorService, Future<?>>> testRunEntries = testRuns_.entrySet();
			for (Entry<ExecutorService, Future<?>> e: testRunEntries) {
				ExecutorService es =  e.getKey();
				es.shutdownNow();
				Future<?> future = e.getValue();
				if (!future.isDone()) {
					future.cancel(true);
				}
			}
			for (Future<?> future: trialFutures_) {
				if (!future.isDone()) {
					future.cancel(true);
				}
			}
			trialRunService_.shutdownNow();
		}
		
		if (remoteService_ == null) {
			system_.exitJvm(0);	
		}
	}

	@Override
	public ExecutorService getTests4jService() {
		return tests4jService_;
	}
}
