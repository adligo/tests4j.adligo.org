package org.adligo.tests4j.run.helpers;

/**
 * intended for use on a single thread to monitor the current thread
 * or other threads.
 * 
 * @author scott
 *
 */
public class Tests4J_ProgressMonitor {
	private String process;
	private Tests4J_Memory memory;
	private I_Tests4J_NotificationManager notifier;
	private long timeBetweenLongs = 450;
	private long startTime;
	private double complete = 0.0;
	private double total;
	private boolean notifyProgress = true;
	
	public Tests4J_ProgressMonitor(Tests4J_Memory pMemory, I_Tests4J_NotificationManager pNotifier, int pTotal, String pProcess) {
		process = pProcess;
		memory = pMemory;
		notifier = pNotifier;
		startTime = memory.getTime();
		total = pTotal;
	}
	
	public synchronized void notifyIfProgressedTime() {
		complete++;
		long time = memory.getTime();
		if (time >= startTime + timeBetweenLongs) {
			startTime = time;
			double progress = complete/ total * 100.0;
			notifier.onProgress(process, progress);
		}
	}
	
	public synchronized void notifyIfProgressedTime(double numberComplete) {
		long time = memory.getTime();
		if (time >= startTime + timeBetweenLongs) {
			startTime = time;
			double progress = numberComplete/ total;
			notifier.onProgress(process, progress * 100.0);
		}
	}
	
	public void notifyDone() {
		notifier.onProgress(process, 100.0);
	}

	protected boolean isNotifyProgress() {
		return notifyProgress;
	}

	protected void setNotifyProgress(boolean notifyProgress) {
		this.notifyProgress = notifyProgress;
	}

	protected long getTimeBetweenLongs() {
		return timeBetweenLongs;
	}

	protected void setTimeBetweenLongs(long timeBetweenLongs) {
		this.timeBetweenLongs = timeBetweenLongs;
	}
}
