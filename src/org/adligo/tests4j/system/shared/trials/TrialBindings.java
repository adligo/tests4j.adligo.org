package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.shared.asserts.common.I_AssertListener;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.common.Platform;
import org.adligo.tests4j.shared.en.Tests4J_EnglishConstants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TrialBindings implements I_TrialBindings {
	private Platform platform;
	private I_Tests4J_Log reporter;
	private I_EvaluatorLookup evaluatorLookup;
	private I_AssertListener listener;
	private I_Tests4J_Constants constants_;
	
	public TrialBindings(Platform pPlatform, I_Tests4J_Constants constants, I_EvaluatorLookup pLookup, I_Tests4J_Log pReporter) {
		platform = pPlatform;
		evaluatorLookup = pLookup;
		reporter = pReporter;
		constants_ = constants;
	}
	@Override
	public Platform getPlatform() {
		return platform;
	}

	@Override
	public I_Tests4J_Log getLog() {
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
  @Override
  public I_Tests4J_Constants getConstants() {
    return constants_;
  }

}
