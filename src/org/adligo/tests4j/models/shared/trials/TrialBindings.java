package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.common.Platform;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

public class TrialBindings implements I_TrialBindings {
	private Platform platform;
	private I_Tests4J_Logger reporter;
	private I_EvaluatorLookup evaluatorLookup;
	private I_AssertListener listener;
	
	public TrialBindings(Platform pPlatform, I_EvaluatorLookup pLookup, I_Tests4J_Logger pReporter) {
		platform = pPlatform;
		evaluatorLookup = pLookup;
		reporter = pReporter;
	}
	@Override
	public Platform getPlatform() {
		return platform;
	}

	@Override
	public I_Tests4J_Logger getReporter() {
		return reporter;
	}

	@Override
	public I_EvaluatorLookup getDefalutEvaluatorLookup() {
		return evaluatorLookup;
	}
	
	public I_AssertListener getAssertListener() {
		return listener;
	}
	
	public void setAssertListener(I_AssertListener listener) {
		this.listener = listener;
	}

}
