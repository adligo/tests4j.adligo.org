package org.adligo.tests4j.system.shared.trials;

/**
 * Implement this interface in your trial to accept
 * outside parameters.  This could include anything
 * including data sets to test for science (I always though
 * seti at home was cool, so why not put it in javascript), or
 * what ever your data requirements are, jdbc connections;
 * i suppose trials should get their own parameters.
 *  
 * @author scott
 *
 * @param <T>
 */
public interface I_TrialParamsAware {

	/**
	 * 
	 * @param p
	 */
	public void setTrialParams(I_TrialParams p);
}
