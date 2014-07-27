package org.adligo.tests4j.models.shared.system;



/**
 * Implementations of this interface are intended to report 
 * out message from the test run.  It is basically a 
 * logging facade for tests4j internals.
 * 
 * Implementations are intended to be thread safe, and
 * have a default no argument constructor so they can
 * be passed into Tests4j via the I_Tests4j_Params.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Log {
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
	public void onException(Throwable p);
	/**
	 * If the log is enabled, used to optimize string 
	 * creation of log messages.
	 * 
	 * @return
	 */
	public boolean isLogEnabled(Class<?> clazz);

	/**
	 * if this is a regular Tests4J test run 
	 * this will return true when the Tests4J_System
	 * is a DefaultSystem, which allows developers of tests4j
	 * to know if they are looking at the regular trial run
	 * or a trial run that was started by the regular trial run.
	 * i.e. org.adligo.tests4j_tests.trials_api.AssertionsFail_Trial.java
	 *  
	 * If tests4J is testing itself through
	 * trial run recursion this will return false.
	 * 
	 * @return
	 */
	public boolean isMainLog();
	
	/**
	 * return the underlying line separator
	 * (usually comes from I_Tests4J_System)
	 * so it can be fixed for testing.
	 * 
	 * @return
	 */
	public String getLineSeperator();

}
