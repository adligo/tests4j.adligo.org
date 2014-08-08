package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.common.I_PlatformContainer;
import org.adligo.tests4j.models.shared.system.I_Tests4J_AssertListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

/**
 * A interface for binding the run(/helpers) 
 * TrialInstanceProcessor instance 
 * to the Trial instances.
 * 
 * @author scott
 *
 */
public interface I_TrialBindings extends I_PlatformContainer {
	public I_Tests4J_AssertListener getAssertListener();
	public I_Tests4J_Log getLog();
	public I_EvaluatorLookup getDefalutEvaluatorLookup();
}
