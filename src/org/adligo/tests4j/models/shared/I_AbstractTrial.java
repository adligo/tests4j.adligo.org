package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.asserts.I_AssertionHelperInfo;
import org.adligo.tests4j.models.shared.asserts.I_Asserts;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

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
	
	void setRuntime(I_AssertionHelperInfo pMemory, I_Tests4J_Reporter pReporter);
	
	public void log(String p);
	
	public void log(Throwable p);
	
	public boolean isLogEnabled(Class<?> c);
}
