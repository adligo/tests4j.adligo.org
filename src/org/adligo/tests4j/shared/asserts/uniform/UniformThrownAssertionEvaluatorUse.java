package org.adligo.tests4j.shared.asserts.uniform;

import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.shared.asserts.common.I_MatchType;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertionData;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.shared.asserts.common.ThrownAssertionData;
import org.adligo.tests4j.shared.asserts.common.ThrownAssertionDataMutant;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;

/**
 * This is just a model like class that can 
 * is used to keep UniformThrownAssertionEvalutor.evaluate
 * thread safe.  Do not use this class directly.
 * 
 * @author scott
 *
 */
public class UniformThrownAssertionEvaluatorUse {
  private I_Tests4J_ResultMessages messages_ =  Tests4J_Constants.CONSTANTS.getResultMessages();
  private I_Evaluation<I_ThrownAssertionData> result_;
  private I_ExpectedThrownData expected_;
  private Throwable actual_;
  
  public UniformThrownAssertionEvaluatorUse(I_ExpectedThrownData expected, Throwable actual) {
    expected_ = expected;
    actual_ = actual;
    check(1, actual, expected);
  }
  
  public I_Evaluation<I_ThrownAssertionData> getResult() {
    return result_;
  }
  
  public void check(int throwable, Throwable actual, I_ExpectedThrownData expected) {
    Class<? extends Throwable> expectedCauseClass = expected.getThrowableClass();
    if (throwable == 1) {
      if (actual == null) {
        createFailure(messages_.getNothingWasThrown(), throwable);
        return;
      }
    } else {
      if (actual == null) {
        createFailure(messages_.getThrowableClassMismatch(), throwable);
        return;
      }
    }
    if (!actual.getClass().equals(expectedCauseClass)) {
      createFailure(messages_.getThrowableClassMismatch(), throwable);
      return;
    }
    String actualMessage = actual.getMessage();
    String expectedMessage = expected.getMessage();
    I_MatchType type = expected.getMatchType();
    MatchType mt = MatchType.get(type);
    switch (mt) {
      case ANY:
          //do nothing
        break;
      case EQUALS:
          if (actualMessage == null || expectedMessage == null) {
            createFailure(messages_.getThrowableMessageNotUniform(), throwable);
            return;
          }
          TextLinesCompare compare = new TextLinesCompare();
          I_TextLinesCompareResult result = compare.compare(expectedMessage, actualMessage, true);
          if (!result.isMatched()) {
            createFailure(messages_.getThrowableMessageNotUniform(), throwable);
            return;
          }
        break;
      case NULL:
      default:
        if (actualMessage != null) {
          createFailure(messages_.getThrowableMessageNotUniform(), throwable);
          return;
        }
    }
    I_ExpectedThrownData expectedCause = expected.getExpectedCause();
    if (expectedCause == null) {
      createSuccess();
      return;
    }
    Throwable actualCause = actual.getCause();
    check(throwable + 1, actualCause, expectedCause);
  }

  private void createSuccess() {
    EvaluationMutant<I_ThrownAssertionData> evaluation =  new EvaluationMutant<I_ThrownAssertionData>();
    ThrownAssertionDataMutant failureMutant = new ThrownAssertionDataMutant();
    failureMutant.setExpected(expected_);
    failureMutant.setActual(actual_);
    failureMutant.setType(AssertType.AssertThrownUniform);
    
    ThrownAssertionData data = new ThrownAssertionData(failureMutant);
    evaluation.setSuccess(true);
    evaluation.setData(data);
    result_ = new Evaluation<I_ThrownAssertionData>(evaluation);
  }
  
  private void createFailure(String message, int throwable) {
    EvaluationMutant<I_ThrownAssertionData> evaluation =  new EvaluationMutant<I_ThrownAssertionData>();
    ThrownAssertionDataMutant failureMutant = new ThrownAssertionDataMutant();
    failureMutant.setExpected(expected_);
    failureMutant.setActual(actual_);
    failureMutant.setFailureThrowable(throwable);
    failureMutant.setFailureReason(message);
    failureMutant.setType(AssertType.AssertThrownUniform);
    
    evaluation.setFailureReason(message);
    ThrownAssertionData failure = new ThrownAssertionData(failureMutant);
    evaluation.setSuccess(false);
    evaluation.setData(failure);
    result_ = new Evaluation<I_ThrownAssertionData>(evaluation);
  }

}
