package org.adligo.tests4j.run.helpers;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;

/**
 * @see I_Tests4J_Controls controlls
 * to abort the test run (needed for eclipse fast cancel)
 * 
 * @author scott
 *
 */
public class Tests4J_Controls implements I_Tests4J_Controls {
	private static final PrintStream ps = System.out;
	private I_Tests4J_ThreadManager threadManager;
	private I_Tests4J_NotificationManager notificationManager;
	private Future<?> future;
	
	public Tests4J_Controls() {
	}
	
	public Tests4J_Controls(Tests4J_Memory memory) {
		//anything can be null except memory
		threadManager = memory.getThreadManager();
		notificationManager = memory.getNotifier();
	}
	@Override
	public void shutdown() {
		if (threadManager != null) {
			threadManager.shutdown();
		}
	}

	@Override
	public boolean isRunning() {
		if (threadManager == null) {
			return false;
		}
		return notificationManager.isRunning();
	}

	public synchronized Future<?> getFuture() {
		return future;
	}

	public synchronized void setFuture(Future<?> future) {
		this.future = future;
	}

	@Override
	public void waitForResults() {
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace(ps);
		}
	}

	
}
