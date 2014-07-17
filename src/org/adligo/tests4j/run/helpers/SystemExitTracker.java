package org.adligo.tests4j.run.helpers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.adligo.tests4j.models.shared.system.I_SystemExit;


/**
 * a dummy/mock implementation of I_SystemExit
 * mostly for mocking, but can be used to derail
 * the System.exit(0); at the end of a Tests4j trial run.
 * @author scott
 *
 */
public class SystemExitTracker implements I_SystemExit {
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

}
