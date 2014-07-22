package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.common.I_Platform;
import org.adligo.tests4j.models.shared.system.I_Tests4J_AssertListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

/**
 * A interface for binding the run(/helpers) 
 * TrialInstanceProcessor instance 
 * to the Trial instances.
 * 
 * @author scott
 *
 */
public interface I_TrialBindings extends I_Platform {
	public I_Tests4J_AssertListener getAssertListener();
	public I_Tests4J_Logger getReporter();
	public I_EvaluatorLookup getDefalutEvaluatorLookup();
}
