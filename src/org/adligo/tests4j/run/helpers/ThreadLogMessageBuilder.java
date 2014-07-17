package org.adligo.tests4j.run.helpers;

/**
 * a helper class to make sure that thread and
 * group names are displayed in a similar manor across 
 * logs so that a log viewer can trace through the logs by 
 * a specific thread.
 * 
 * @author scott
 *
 */
public class ThreadLogMessageBuilder {

	public static String getThreadWithGroupNameForLog() {
		return "Thread/Group: " + Thread.currentThread().getName() + 
				"~~~" + Thread.currentThread().getThreadGroup().getName();
	}
	
	public static String getThreadForLog() {
		return "Thread: " + Thread.currentThread().getName();
	}
}
