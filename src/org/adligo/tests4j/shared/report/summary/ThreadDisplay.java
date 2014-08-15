package org.adligo.tests4j.shared.report.summary;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public class ThreadDisplay {
	private I_Tests4J_Log log;
	
	public ThreadDisplay(I_Tests4J_Log p) {
		log = p;
	}
	
	public boolean isOn() {
		return log.isLogEnabled(ThreadDisplay.class);
	}
	
	public String getThreadInfo() {
		return Thread.currentThread().getName();
	}
}