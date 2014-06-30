package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.asserts.common.I_Asserts;

/**
 * classes that implement this class must also contain the
 * @TrialType annotation at some level.
 * 
 * @author scott
 *
 */
public interface I_AbstractTrial extends I_Asserts {
	/**
	 * this method is run before each method
	 * annotated with @Test.  Override it 
	 * to change the default logic (nothing). 
	 */
	public void beforeTests();
	/**
	 * this method is run after each method
	 * annotated with @Test.  Override it 
	 * to change the default logic (nothing). 
	 */
	public void afterTests();
	
	/**
	 * bind the trial instance to the 
	 * TrialInstancesProcessor
	 * or other processor for assertion notification
	 * and reporting.
	 * 
	 * @param bindings
	 */
	void setBindings(I_TrialProcessorBindings bindings);
	
	/**
	 * log to the reporter
	 * ie ConsoleReporter -> System.out
	 * @param p
	 */
	public void log(String p);
	/**
	 * log to the reporter
	 * ie ConsoleReporter -> System.out
	 * @param p
	 */
	public void log(Throwable p);
	/**
	 * check if a specific class 
	 * has it's log enabled
	 * ie ConsoleReporter -> isLogEnabled(Class<?> c)
	 * @param p
	 */
	public boolean isLogEnabled(Class<?> c);

}
