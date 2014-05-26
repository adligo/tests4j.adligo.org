package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.asserts.I_Asserts;
import org.adligo.tests4j.models.shared.bindings.I_TrialProcessorBindings;

/**
 * note this should always be a AbstractTrial,
 * this interface is only here to allow casting
 * between classloaders.
 * 
 * @author scott
 *
 */
public interface I_AbstractTrial extends I_Asserts {
	public void beforeTests();

	public void afterTests();
	
	void setBindings(I_TrialProcessorBindings bindings);
	
	public void log(String p);
	
	public void log(Throwable p);
	
	public boolean isLogEnabled(Class<?> c);
}
