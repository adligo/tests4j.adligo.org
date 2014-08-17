package org.adligo.tests4j.models.shared.common;

import org.adligo.tests4j.models.shared.common.I_System;

public class DefaultSystem implements I_System {
	public static final String DEFAULT_THREAD_NAME = "main";

	@Override
	public void println(String p) {
		System.out.println(p);
	}

	@Override
	public void exitJvm(int p) {
		//donothing GWT had no System.exit so Tests4J_Sysetm is a JseSystem
	}

	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public String getLineSeperator() {
		return System.getProperty("line.separator", "\n");
	}

	@Override
	public String getCurrentThreadName() {
		return DEFAULT_THREAD_NAME;
	}

}
