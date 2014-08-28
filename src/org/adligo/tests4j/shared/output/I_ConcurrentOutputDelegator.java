package org.adligo.tests4j.shared.output;

/**
 * A output delegateor that can be used by multiple threads.
 * @author scott
 *
 */
public interface I_ConcurrentOutputDelegator extends I_OutputBuffer {
	/**
	 * poll the main output
	 * @return
	 */
	public abstract String poll();
	/**
	 * a thread local delegate
	 * @param buffer
	 */
	public void setDelegate(I_ToggleOutputBuffer buffer);
}
