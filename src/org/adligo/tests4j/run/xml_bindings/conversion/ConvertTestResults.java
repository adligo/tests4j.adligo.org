package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.run.xml.io.test_result.v1_0.TestFailureType;
import org.adligo.tests4j.run.xml.io.test_result.v1_0.TestResultType;
import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_TestFailure;
import org.adligo.tests4j.shared.asserts.common.I_TestFailureType;

public class ConvertTestResults {

  @SuppressWarnings("boxing")
  public static TestResultType to(I_TestResult result) {
    TestResultType ret = new TestResultType();
    ret.setAfterTestOutput(result.getAfterOutput());
    ret.setAsserts(result.getAssertionCount());
    ret.setBeforeTestOutput(result.getBeforeOutput());
    
    I_TestFailure failure = result.getFailure();
    if (failure != null) {
      TestFailureType tft = new TestFailureType();
      I_AssertType type = failure.getAssertType();
      
      tft.setAssertType(type.toString());
      tft.setDetail(failure.getFailureDetail());
      tft.setMessage(failure.getFailureMessage());
      I_TestFailureType failureType = failure.getType();
      
      tft.setType(failureType.toString());
      ret.setFailure(tft);
    }
    ret.setName(result.getName());
    ret.setOutput(result.getOutput());
    ret.setUniqueAsserts(result.getUniqueAssertionCount());
    
    return ret;
  }
}
