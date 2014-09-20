package org.adligo.tests4j.run.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.adligo.tests4j.run.remote.Tests4J_RemoteRunner;

public interface I_Tests4J_ThreadManager {

	public ExecutorService createNewTestRunService();

	public void addTrialFuture(Future<?> future);

	public void addSetupFuture(Future<?> future);

	public void addRemoteFuture(Future<?> future);

	public void addRemoteRunner(I_Tests4J_RemoteRunner p);

	public ExecutorService getTests4jService();
	
	public ExecutorService getRemoteService();
	
	public ExecutorService getSetupService();

	public ExecutorService getTrialRunService();
	
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