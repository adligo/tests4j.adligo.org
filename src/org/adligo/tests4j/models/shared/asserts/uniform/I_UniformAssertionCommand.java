package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_SimpleCompareAssertCommand;


public interface I_UniformAssertionCommand<D> extends I_SimpleCompareAssertCommand {
	public I_Evaluation<D> getEvaluation();

}