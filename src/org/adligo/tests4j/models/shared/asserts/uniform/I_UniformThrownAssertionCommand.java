package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertCommand;


public interface I_UniformThrownAssertionCommand<D> extends I_ThrownAssertCommand {
	public I_Evaluation<D> getEvaluation();
}