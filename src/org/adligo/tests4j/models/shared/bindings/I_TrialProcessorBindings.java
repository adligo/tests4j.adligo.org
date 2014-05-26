package org.adligo.tests4j.models.shared.bindings;

import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * A interface for binding the run(/helpers) 
 * TrialInstanceProcessor instance 
 * to the Trial instances.
 * 
 * @author scott
 *
 */
public interface I_TrialProcessorBindings {
	public I_AssertListener getAssertionListener();
	public I_Tests4J_Reporter getReporter();
}
