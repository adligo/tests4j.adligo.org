package org.adligo.tests4j.shared.asserts.uniform;

import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertCommand;


public interface I_UniformThrownAssertionCommand<I_AssertThrownFailure> extends I_ThrownAssertCommand {
	public I_Evaluation<I_AssertThrownFailure> getEvaluation();
}