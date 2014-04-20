package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;

/**
 * This provides runtime assistance for 
 * re-mapping the stack from instrumented 
 * java byte code back
 * to byte code that corresponds to the source code.
 *  
 *  Also the listener was added to go from 4 to 3 params in the AssertionProcessor.
 *  
 * @author scott
 *
 */
public interface I_AssertionHelperInfo {
	public I_CoveragePlugin getCoveragePlugin();
	public Class<?> getInstanceClass();
	public Class<?> getNonInstrumentedInstanceClass();
	public I_AssertListener getListener();
}
