package org.adligo.tests4j.models.shared.system.report;

import org.adligo.tests4j.models.shared.system.I_TrialRunListener;


/**
 * Implementations of this interface are intended to report 
 * out progress of the test run. 
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Reporter extends I_TrialRunListener {
	/**
	 * log a message out to the main test run location.
	 * @param p
	 */
	public void log(String p);

	/**
	 * Notify the reporter that a 
	 * internal Test4J
	 * Error has been thrown.
	 * 
	 * This should generally not occur.
	 * 
	 * @param p
	 */
	public void onError(Throwable p);
	/**
	 * If the log is enabled, used to optimize string 
	 * creation of log messages.
	 * 
	 * @return
	 */
	public boolean isLogEnabled(String clazzName);
	
	/**
	 * When this returns true, the original System.out
	 * and System.err are not delegated to.
	 * 
	 * When this returns false the original System.out
	 * and System.err are delegated to.
	 * 
	 * Regardless the System.out and System.err
	 * are replaced.
	 * @return
	 */
	public boolean isSnare();
	/**
	 * if this is true
	 * then the Tests4J should redirect output
	 * ie System.setOut System.setErr
	 * 
	 * if false it should leave the output streams alone.
	 * @return
	 */
	public boolean isRedirect();
}
