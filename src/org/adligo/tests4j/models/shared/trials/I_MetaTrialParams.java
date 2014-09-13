package org.adligo.tests4j.models.shared.trials;

/**
 * NOTE meta trial params doesn't need to go over
 * the network.
 * 
 * @author scott
 *
 * @param <T>
 */
public interface I_MetaTrialParams<T extends I_MetaTrialInputData> {
	public T getTrialParams();
}
