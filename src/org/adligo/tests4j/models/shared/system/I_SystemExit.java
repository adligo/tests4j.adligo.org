package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.run.helpers.SystemExitTracker;

/**
 * mostly for mocking System.exit
 * but can also be used to 
 * keep System.exit from happening at the end of your
 * Test4j trial run.
 * 
 * Known Implementations
 * @see SystemExitTracker
 * @see DefaultSystemExitor
 * 
 * @author scott
 *
 */
public interface I_SystemExit {
	/**
	 * this is just so that 
	 * System.exit(0) can be tested for
	 */
	public void doSystemExit(int p);
}
