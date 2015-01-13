package org.adligo.tests4j.shared.asserts.uniform;

import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertionData;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;

/**
 * @see I_UniformThrownAssertionEvaluator
 * @author scott
 *
 */
public class UniformThrownAssertionEvaluator implements I_UniformThrownAssertionEvaluator<I_ThrownAssertionData> {
  private final I_Tests4J_Constants constants_;
  
  public UniformThrownAssertionEvaluator(I_Tests4J_Constants constants) {
    constants_ = constants;
  }
	@Override
	public I_Evaluation<I_ThrownAssertionData> isUniform(
			I_ExpectedThrowable expected, Throwable actual) {
		
	  UniformThrownAssertionEvaluatorUse use = new UniformThrownAssertionEvaluatorUse(constants_, expected, actual);
	  return use.getResult();
	}



}
