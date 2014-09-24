package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.shared.asserts.common.I_AssertListener;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.common.I_PlatformContainer;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * A interface for binding the run(/helpers) 
 * TrialInstanceProcessor instance 
 * to the Trial instances.
 * 
 * @author scott
 *
 */
public interface I_TrialBindings extends I_PlatformContainer {
	public I_AssertListener getAssertListener();
	public I_Tests4J_Log getLog();
	public I_EvaluatorLookup getDefalutEvaluatorLookup();
}
