package org.adligo.tests4j.run.helpers;

import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.run.common.I_Tests4J_ThreadManager;
import org.adligo.tests4j.run.memory.Tests4J_Memory;
import org.adligo.tests4j.system.shared.I_Tests4J_Controls;

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
	private AtomicBoolean waiting = new AtomicBoolean(false);
	private AtomicBoolean running = new AtomicBoolean(true);
	
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

	@Override
	public synchronized void waitForResults() {
		try {
			waiting.set(true);
			while (running.get()) {
				this.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace(ps);
		}
	}

	protected synchronized void notifyFinished() {
		running.set(false);
		if (waiting.get()) {
			this.notifyAll();
		}
	}
	
}
