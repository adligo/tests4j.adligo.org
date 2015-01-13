package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.shared.asserts.uniform.I_Evaluation;
import org.adligo.tests4j.shared.asserts.uniform.I_UniformAssertionCommand;
import org.adligo.tests4j.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;

/**
 *  A class to represent a assertUniform or assertNotUniform
 *  assertion.  
 *  Note this class is NOT in the .uniform package
 *  so that it can extend AbstractAssertCommand with out
 *  creating a circular package dependency.  
 *  (.asserts - - depends on - - > .asserts.uniform)
 *  
 * @author scott
 *
 */
public class UniformAssertCommand<T,D> extends AbstractAssertCommand 
  implements I_UniformAssertionCommand<D> {
  
  public static final String UNIFORM_ASSERT_COMMAND_REQUIRES_EVAULATOR = "UniformAssertCommand requires a evaluator.";
  public static final String NULL_DATA = "UniformAssertCommand requires non null CompareAssertionData.";
  public static final String BAD_TYPE = 
      "UniformAssertCommand requires it's type to be one of AssertType.UNIFORM_TYPES";
  private I_CompareAssertionData<?> data;
  private I_Evaluation<D> result;
  private I_UniformAssertionEvaluator<T, D> evaluator;
  
  
  public UniformAssertCommand(I_Tests4J_Constants constants,  String failureMessage, 
      I_CompareAssertionData<T> pData, I_UniformAssertionEvaluator<T, D> pEvaluator) {
    super(pData.getType(), failureMessage);
    
    //copy it between classloaders
    //classloader issues
    AssertType type = AssertType.getType(super.getType());
    if (!AssertType.UNIFORM_TYPES.contains(type)) {
      throw new IllegalArgumentException(BAD_TYPE);
    }
    data = pData;
    if (data == null) {
      throw new IllegalArgumentException(NULL_DATA);
    }
    if (data.getExpected() == null) {
      I_Tests4J_AssertionInputMessages messages = constants.getAssertionInputMessages();
      throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
    }
    if (pEvaluator == null) {
      throw new IllegalArgumentException(UNIFORM_ASSERT_COMMAND_REQUIRES_EVAULATOR);
    }
    evaluator = pEvaluator;
  }

  @Override
  public Object getExpected() {
    return data.getExpected();
  }

  @Override
  public Object getActual() {
    return data.getActual();
  }

  @Override
  public I_AssertionData getData() {
    return data;
  }

  
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand#evaluate(org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean evaluate() {
    //classloader issues
    AssertType type = AssertType.getType(super.getType());
    switch (type) {
      case AssertUniform:
          result = evaluator.isUniform((I_CompareAssertionData<T>) data);
          if (result.isSuccess()) {
            return true;
          } 
        break;
      case AssertNotUniform:
        result = evaluator.isNotUniform((I_CompareAssertionData<T>) data);
        if (result.isSuccess()) {
          return true;
        } 
        break;
    }
    
    return false;
  }
  
  @Override
  public I_Evaluation<D> getEvaluation() {
    return result;
  }

}
