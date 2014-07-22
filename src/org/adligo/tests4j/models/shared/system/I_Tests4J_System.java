package org.adligo.tests4j.models.shared.system;

/**
 * simple wrapper for System.out/System.err exc
 * implementations are required to have a no
 * arg constructor
 * 
 * @author scott
 *
 */
public interface I_Tests4J_System {
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
	public void doSystemExit(int p);
	
	/**
	 * just a wrapper for System.currentTimeMillis
	 * @return
	 */
	public long getTime();
}
