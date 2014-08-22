package org.adligo.tests4j.models.shared.system;

import java.util.concurrent.Future;

/**
 * This interface provides control
 * of the running Tests4j Trials/Tests
 * @author scott
 *
 */
public interface I_Tests4J_Controls {
	public boolean isRunning();
	public void shutdown();
	/**
	 * wait for the results to finish,
	 * delegates to {@link Future#get()} in JSE.
	 */
	public void waitForResults();
}
