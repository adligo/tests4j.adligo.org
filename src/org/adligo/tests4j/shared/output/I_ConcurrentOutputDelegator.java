package org.adligo.tests4j.shared.output;

/**
 * A output delegateor that can be used by multiple threads.
 * @author scott
 *
 */
public interface I_ConcurrentOutputDelegator extends I_OutputBuffer, I_OutputDelegateor {
	public abstract String poll();
}
