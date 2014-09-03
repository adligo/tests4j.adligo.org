package org.adligo.tests4j.models.shared.trials;

/**
 * Implement this interface to 
 * pass parameters to your trial instance.
 * MetaTrial parameters are passed by the 
 * metaTrialParams methods in I_Tests4J_Params.
 * For other trial types, the I_TrialParamsQueue next method is used
 * each instance using a trailing id. I.E.
 * create("org.adligo.tests4j.models.shared.trials.SourceFileTrial[0]");
 * create("org.adligo.tests4j.models.shared.trials.SourceFileTrial[0]");
 * 
 * 
 * @author scott
 *
 */
public interface I_TrialParams<T extends I_TrialInputData> {

	public T getTrialParams();
}
