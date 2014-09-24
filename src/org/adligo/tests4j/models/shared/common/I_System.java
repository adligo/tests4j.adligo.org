package org.adligo.tests4j.models.shared.common;

import java.io.PrintStream;

/**
 * simple wrapper for System.out/System.err exc
 * implementations are required to have a no
 * arg constructor
 * 
 * @author scott
 *
 */
public interface I_System {
	/**
	 * just like System.out.println(p)
	 * stubbed for testing
	 * @param p
	 */
	public void println(String p);
	
	/**
	 * this is just so that 
	 * System.exit(0) can be tested for
	 */
	public void exitJvm(int p);
	
	/**
	 * just a wrapper for System.currentTimeMillis
	 * @return
	 */
	public long getTime();
	
	/**
	 * a wrapper around System.getLineSeperator
	 * so it can be fixed to unix for the trials/tests.
	 * @return
	 */
	public String lineSeperator();
	
	/**
	 * this should default to 'main'
	 * when Thread.currentThread.getName()
	 * isn't available (ie in GWT).
	 * @return
	 */
	public String getCurrentThreadName();
	
	/**
	 * the result of System.getProperty("java.version", "");
	 * @return
	 */
	public String getJseVersion();
	/**
	 * the print stream for this system,
	 * ie System.out
	 * @return
	 */
	public PrintStream getOut();
	
	/**
	 * this is used to distinguish if it is the main log
	 * for debugging breakpoints
	 * @return
	 */
	public boolean isMainSystem();
}
