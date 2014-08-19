package org.adligo.tests4j.run.helpers;

import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.system.I_Tests4J_ProcessInfo;

/**
 * intended for use on a single thread to monitor the current thread
 * or other threads.
 * 
 * @author scott
 *
 */
public class Tests4J_ProgressMonitor {
	public static final String TESTS4J_PROGERESS_MONTIOR_REQUIRES_A_NOTIFIER = "Tests4J_ProgeressMontior requires a notifier.";
	public static final String TESTS4J_PROGERESS_MONTIOR_REQUIRES_A_SYSTEM = "Tests4J_ProgeressMontior requires a system.";
	public static final String TESTS4J_PROGERESS_MONTIOR_REQUIRES_PROCESS_INFO = "Tests4J_ProgeressMontior requires a Tests4J_ProcessInfo.";
	
	private I_System system;
	private I_Tests4J_NotificationManager notifier;
	private long timeBetweenLongs = 450;
	private long startTime;
	private double complete = 0.0;
	private double total;
	private AtomicBoolean sent100 = new AtomicBoolean(false);
	private Tests4J_ProcessInfo info;
	
	public Tests4J_ProgressMonitor(I_System pSystem, I_Tests4J_NotificationManager pNotifier, Tests4J_ProcessInfo pInfo) {
		
		if (pSystem == null) {
			throw new IllegalArgumentException(TESTS4J_PROGERESS_MONTIOR_REQUIRES_A_SYSTEM);
		}
		system = pSystem;
		if (pNotifier == null) {
			throw new IllegalArgumentException(TESTS4J_PROGERESS_MONTIOR_REQUIRES_A_NOTIFIER);
		}
		notifier = pNotifier;
		info = pInfo;
		if (pInfo == null) {
			throw new IllegalArgumentException(TESTS4J_PROGERESS_MONTIOR_REQUIRES_PROCESS_INFO);
		}
		
		startTime = system.getTime();
	}
	
	public void incrementAndNotify() {
		info.addDone(); 
		long time = system.getTime();
		if (time >= startTime + timeBetweenLongs) {
			startTime = time;
			notifier.onProgress(info);
		}
	}

	

	protected long getTimeBetweenLongs() {
		return timeBetweenLongs;
	}

	protected void setTimeBetweenLongs(long timeBetweenLongs) {
		this.timeBetweenLongs = timeBetweenLongs;
	}
}
