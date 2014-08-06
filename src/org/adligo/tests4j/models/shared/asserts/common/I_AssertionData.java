package org.adligo.tests4j.models.shared.asserts.common;

import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionCommand;

/**
 * This is a marker interface that represents data 
 * that is input to the assert* method.
 * It should return all information about the objects (primitives)
 * passed into the assert* command except for the FailureMessage
 * which can be obtained by @see {@link I_AssertCommand#getFailureMessage()}.
 * 
 * If the assertion is a more complex uniform assertion
 * information about why the assertion was true or false
 * can be obtained by the methods in the evaluation
 * {@link I_UniformAssertionCommand#getEvaluation()},
 * {@link I_UniformThrownAssertionCommand#getEvaluation()},
 * @author scott
 *
 */
public interface I_AssertionData {
	public I_AssertType getType();
}
