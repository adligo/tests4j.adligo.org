package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.shared.asserts.common.I_Asserts;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

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
	void setBindings(I_TrialBindings bindings);
	
	/**
	 * log to the reporter
	 * ie ConsoleReporter -> System.out
	 * @param p
	 */
	public I_Tests4J_Log getLog();

	/**
	 * This will allow the view of a tests4j gui to view a
	 * widget during the trial/test run.  
	 * I think this would be;
	 * 
	 * for GWT this should be a com.google.gwt.user.client.ui.Widget
	 * for Mobile http://www.codenameone.com/ this should be a com.codename1.ui.Component
	 * for Eclipse org.eclipse.swt.widgets.Widget
	 * for Swing javax.swing.JComponent
	 * @param o
	 */
	public void showWidget(Object o);
	
	/**
	 * subclasses can implement this if they want
	 * 
	 * @return
	 */
	public double getPctDone(String testName);
}
