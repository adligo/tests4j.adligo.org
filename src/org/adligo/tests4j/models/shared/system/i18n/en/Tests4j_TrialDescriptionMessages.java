package org.adligo.tests4j.models.shared.system.i18n.en;

import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_AfterTrialTestsErrors;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TestMethodErrors;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4j_AfterTrialErrors;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4j_BeforeTrialErrors;

public class Tests4j_TrialDescriptionMessages implements I_Tests4J_TrialDescriptionMessages {
	private Tests4J_BeforeTrialErrors beforeTrialErrors = new Tests4J_BeforeTrialErrors();
	private Tests4J_TestMethodErrors testMethodErrors = new Tests4J_TestMethodErrors();
	private Tests4J_AfterTrialTestsErrors afterTrialTestsErrors = new Tests4J_AfterTrialTestsErrors();
	private Tests4J_AfterTrialErrors afterTrialErrors = new Tests4J_AfterTrialErrors();
	
	@Override
	public I_Tests4j_BeforeTrialErrors getBeforeTrialErrors() {
		return beforeTrialErrors;
	}

	@Override
	public I_Tests4J_TestMethodErrors getTestMethodErrors() {
		return testMethodErrors;
	}

	@Override
	public I_Tests4J_AfterTrialTestsErrors getAfterTrialTestsErrors() {
		return afterTrialTestsErrors;
	}

	@Override
	public I_Tests4j_AfterTrialErrors getAfterTrialErrors() {
		return afterTrialErrors;
	}
	
	
}
