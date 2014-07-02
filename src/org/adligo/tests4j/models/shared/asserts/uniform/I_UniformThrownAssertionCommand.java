package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;


public interface I_UniformThrownAssertionCommand extends I_CompareAssertCommand {
	public abstract boolean evaluate(I_Thrower thrower ,I_UniformAssertionEvaluator<?> e);
}