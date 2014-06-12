package org.adligo.tests4j.models.shared.system;

/**
 * This interface provides control
 * of the running Tests4j Trials/Tests
 * @author scott
 *
 */
public interface I_Tests4J_Controls {
	public boolean isRunning();
	public void shutdown();
}
