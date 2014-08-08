package org.adligo.tests4j.models.shared.common;

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
	public String getLineSeperator();
}
