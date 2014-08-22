package org.adligo.tests4j.models.shared.common;

import java.io.PrintStream;

import org.adligo.tests4j.models.shared.common.I_System;

public class DefaultSystem implements I_System {
	public static final String DEFAULT_THREAD_NAME = "main";
	/**
	 * capture the initial System.out PrintStream
	 * at class load time, so it can never be changed.
	 */
	private static final PrintStream OUT = System.out;
	
	@Override
	public void println(String p) {
		OUT.println(p);
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

	@Override
	public PrintStream getOut() {
		return OUT;
	}

}
