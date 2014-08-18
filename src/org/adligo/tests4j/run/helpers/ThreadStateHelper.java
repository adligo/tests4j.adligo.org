package org.adligo.tests4j.run.helpers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class ThreadStateHelper {
	private I_Tests4J_Log reporter;
	
	public ThreadStateHelper(I_Tests4J_Log p) {
		reporter = p;
	}
	
	public void logAllThreadStates() {
		Map<Thread,StackTraceElement[]> threadStacks = Thread.getAllStackTraces();
		Set<Entry<Thread, StackTraceElement[]>> entries = threadStacks.entrySet();
		for (Entry<Thread, StackTraceElement[]> e: entries) {
			reporter.log("");
			reporter.log("Thread " + e.getKey().getName());
			
			StackTraceElement[] stes = e.getValue();
			for (int i = 0; i < stes.length; i++) {
				StackTraceElement ste = stes[i];
				reporter.log("\t at " + ste);
			}
		}
	}
}
