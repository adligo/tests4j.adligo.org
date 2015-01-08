package org.adligo.tests4j.shared.output;

import java.util.List;



/**
 * Implementations of this interface are intended to report 
 * out message from the test run.  It is basically a 
 * logging facade for tests4j internals.
 * 
 * Implementations are intended to be thread safe.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Log {
	/**
	 * This methods logs a message out to the main test run location.<br/>
	 * Multiple line log messages should be formatted into a single<br/>
	 * string before calling this method.  
	 * @param p
	 */
	public void log(String p);
	/**
   * This methods logs a message out to the main test run location.<br/>
   * This method allows<br/>
   * for a variable number of String parameters to assist in<br/>
   * right to left and left to right logic for i18n.<br/>
   * Note if the I_Tests4J_Constants isLeftToRight<br/>
   * then the array is left to right, otherwise<br/>
   * 0 is at the right, <br/>
   * 1 is left of 0<br/>
   * 2 is left of 1 etc.<br/>
   * This also appends a new line character to the string after it is ordered,<br/>
   * since new lines would always be at the right, regardless of the order of the <br/>
   * characters on the line.<br/>
   * @param p
   */
	public void logLine(String ... p);
	
	/**
	 * Log a Throwable including it's stack trace.
	 * Note the implementation should format the 
	 * stack trace into a multiple line string 
	 * before passing it to the log(String) method
	 * so that all lines are kept together in the console/log file.
	 * Note for now the stack trace is always left to right,
	 * I am not sure if they would be click-able in IDEs
	 * like eclipse if they were formated right to left.
	 * 
	 * @param p
	 */
	public void onThrowable(Throwable p);
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
	 * return a string with the following content;
	 * " on thread threadName1 "
	 * @return
	 */
	public String getCurrentThreadName();
	/**
	 * return the underlying line separator
	 * (usually comes from I_Tests4J_System)
	 * so it can be fixed for testing.
	 * 
	 * @return
	 */
	public String getLineSeperator();
	
	/**
	 * 
	 * @return something like;<br/>
	 * Thread/Group; main~~~~main-group
	 */
  public String getThreadWithGroupNameMessage();
  
  /**
   * @return something like;<br/>
   * Thread; main
   */
  public String getThreadMessage();
  /**
   * This method uses the Tests4J_Constants.isLeftToRight() setting
   * to determine which side the indent
   * should go on, and appends it to the StringBuilder
   * accordingly.
   * @param sb
   * @param line
   * @param indent
   */
  public void appendLine(StringBuilder sb, String line, String indent);
  /**
   * This method uses the Tests4J_Constants.isLeftToRight() setting
   * to determine which side the indent
   * should go on, and appends each line to the StringBuilder
   * accordingly.
   * @param sb
   * @param line
   * @param indent
   */
  public void appendLines(StringBuilder sb, List<String> lines, String indent);
  
}
