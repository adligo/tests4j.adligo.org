package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.run.common.I_JseSystem;

import java.io.File;
import java.io.PrintStream;

public class JseSystem implements I_JseSystem {
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

  @Override
  public File newFile(String path) {
    return new File(path);
  }

}
