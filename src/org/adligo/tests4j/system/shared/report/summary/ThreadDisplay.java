package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class ThreadDisplay {
	private I_Tests4J_Log log_;
	private I_System system_;
	
	public ThreadDisplay(I_Tests4J_Log p, I_System sys) {
		log_ = p;
		system_ = sys;
	}
	
	public boolean isOn() {
		return log_.isLogEnabled(ThreadDisplay.class);
	}
	
	public String getThreadInfo() {
		return system_.getCurrentThreadName();
	}
}
