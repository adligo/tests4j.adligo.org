package org.adligo.tests4j.run.helpers;

import java.io.PrintStream;

import org.adligo.tests4j.models.shared.common.DefaultSystem;
import org.adligo.tests4j.models.shared.common.I_System;

public class JseSystem implements I_System {
	/**
	 * capture the initial output stream at
	 * class loading time, so that it can't be changed
	 */
	private static final PrintStream OUT = System.out;
	/**
	 * when jdk 1.7 is last minor version change to 
	 * System.lineSeperator();
	 */
	private static final String LineSeperator = System.getProperty("line.seperator","\n");
	
	public JseSystem() {}
	
	@Override
	public void println(String p) {
		OUT.println(p);
	}

	@Override
	public void exitJvm(int p) {
		System.exit(p);
	}

	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public String lineSeperator() {
		return LineSeperator;
	}

	@Override
	public String getCurrentThreadName() {
		return Thread.currentThread().getName();
	}

	@Override
	public PrintStream getOut() {
		return OUT;
	}

	@Override
	public String getJseVersion() {
		return System.getProperty("java.version", "");
	}

	@Override
	public boolean isMainSystem() {
		return true;
	}

}
