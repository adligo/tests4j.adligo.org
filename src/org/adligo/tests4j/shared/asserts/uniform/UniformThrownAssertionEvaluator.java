package org.adligo.tests4j.shared.asserts.uniform;

import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertionData;

/**
 * @see I_UniformThrownAssertionEvaluator
 * @author scott
 *
 */
public class UniformThrownAssertionEvaluator implements I_UniformThrownAssertionEvaluator<I_ThrownAssertionData> {

	@Override
	public I_Evaluation<I_ThrownAssertionData> isUniform(
			I_ExpectedThrownData expected, Throwable actual) {
		
	  UniformThrownAssertionEvaluatorUse use = new UniformThrownAssertionEvaluatorUse(expected, actual);
	  return use.getResult();
	}



}
