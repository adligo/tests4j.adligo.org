package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.run.xml.io.test_result.v1_0.TestResultType;
import org.adligo.tests4j.run.xml.io.trial_result.v1_0.CommonResultType;

import java.util.List;

public class ConvertCommonTrialResults {


  @SuppressWarnings("boxing")
  public static CommonResultType to(I_TrialResult result) {
    CommonResultType cr = new CommonResultType();
    cr.setAfterTrialOutput(result.getAfterTrialOutput());
    cr.setAsserts(result.getAssertionCount());
    cr.setBeforeTrialOutput(result.getBeforeTrialOutput());
    if (!result.hasRecordedCoverage()) {
      cr.setCoverage(false);
    }
    if (result.isIgnored()) {
      cr.setIgnored(true);
    }
    cr.setName(result.getName());
    cr.setPass(result.isPassed());
    cr.setRun(result.getRunNumber());
    cr.setType("" + result.getType().getId());
    cr.setUniqueAsserts(result.getUniqueAssertionCount());
   
    List<I_TestResult> testResults = result.getResults();
    if (testResults.size() >= 1) {
      List<TestResultType> passes = cr.getPassed();
      List<TestResultType> failures = cr.getFailed();
      List<TestResultType> ignores = cr.getIsIgnored();
     
      for (I_TestResult tr: testResults) {
        TestResultType trt = ConvertTestResults.to(tr);
        if (tr.isPassed()) {
          passes.add(trt);
        } else if (tr.isIgnored()) {
          ignores.add(trt);
        } else {
          failures.add(trt);
        }
      }
    }
    return cr;
  }
}
