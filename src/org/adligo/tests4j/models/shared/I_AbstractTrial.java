package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.asserts.I_AssertionHelperInfo;
import org.adligo.tests4j.models.shared.asserts.I_Asserts;

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
	
	void setRuntime(I_AssertionHelperInfo pMemory);
}
