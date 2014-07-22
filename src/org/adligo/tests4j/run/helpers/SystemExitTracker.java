package org.adligo.tests4j.run.helpers;

import java.util.concurrent.ArrayBlockingQueue;

import org.adligo.tests4j.models.shared.system.I_Tests4J_System;


/**
 * a dummy/mock implementation of I_SystemExit
 * mostly for mocking, but can be used to derail
 * the System.exit(0); at the end of a Tests4j trial run.
 * @author scott
 *
 */
public class SystemExitTracker implements I_Tests4J_System {
	private final ArrayBlockingQueue<Integer> lastStatus = new ArrayBlockingQueue<>(10);
	
	@Override
	public void doSystemExit(final int p) {
		synchronized (lastStatus) {
			lastStatus.add(p);
		}
		
	}

	public int getLastStatus() throws InterruptedException {
		return lastStatus.take();
	}

	@Override
	public void println(String p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
