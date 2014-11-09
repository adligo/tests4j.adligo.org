package org.adligo.tests4j.run.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public interface I_ThreadManager {

	public ExecutorService createNewTestRunService();

	public void addTrialFuture(Future<?> future);

	public void addSetupFuture(Future<?> future);

	public void addRemoteFuture(Future<?> future);

	public void addRemoteRunner(I_RemoteRunner p);

	public ExecutorService getTests4jService();
	
	public ExecutorService getRemoteService();
	
	public ExecutorService getSetupService();

	public ExecutorService getTrialRunService();
	
	public ThreadFactory getCustomThreadFactory();
	
	public void setTestRunFuture(ExecutorService exe, Future<?> future);
	/**
	 * all of the local setup/trial/test executor services can be shutdown
	 * wait for remote stuff to finish, keeping the monitor thread running
	 * if nothing remote is running shutdown everything (may call System.exit)
	 */
	public void shutdownLocal();
	
	/**
	 * shutdown everything now, no waiting
	 * (may call System.exit)
	 */
	public void shutdown();
}