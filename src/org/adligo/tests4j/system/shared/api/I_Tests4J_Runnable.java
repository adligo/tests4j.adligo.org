package org.adligo.tests4j.system.shared.api;


/**
 * this interface helps tests4j determine what is 
 * still being worked on, when if it hangs
 * @author scott
 *
 */
public interface I_Tests4J_Runnable {
	/**
	 * return the trial that this runnable is currently working on
	 * @return the class name of the trial.
	 */
	public I_Tests4J_TrialProgress getTrial();
}
