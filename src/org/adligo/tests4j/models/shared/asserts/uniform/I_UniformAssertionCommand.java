package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertCommand;


public interface I_UniformAssertionCommand extends I_CompareAssertCommand {

	public abstract boolean evaluate(I_UniformAssertionEvaluator<?> e);

}