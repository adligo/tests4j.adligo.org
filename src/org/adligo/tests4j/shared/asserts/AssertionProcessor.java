package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.AssertCompareFailure;
import org.adligo.tests4j.shared.asserts.common.AssertThrownFailure;
import org.adligo.tests4j.shared.asserts.common.I_AssertCompareFailure;
import org.adligo.tests4j.shared.asserts.common.I_AssertListener;
import org.adligo.tests4j.shared.asserts.common.I_AssertThrownFailure;
import org.adligo.tests4j.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.shared.asserts.common.I_SimpleAssertCommand;
import org.adligo.tests4j.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.shared.asserts.common.TestFailure;
import org.adligo.tests4j.shared.asserts.common.TestFailureBuilder;
import org.adligo.tests4j.shared.asserts.common.TestFailureMutant;
import org.adligo.tests4j.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.shared.common.StackTraceBuilder;

/**
 * this class processes the assert commands
 * by calling .evaluate and then notifying the I_AssertListener
 * about the outcome.
 * 
 * @author scott
 *
 */
public class AssertionProcessor {

  /**
   * @param cmd
   */
  public static void evaluate(I_AssertListener listener, I_SimpleAssertCommand cmd) {
    if (cmd.evaluate()) {
      synchronized (listener) {
        listener.assertCompleted(cmd);
      }
    } else {
      synchronized (listener) {
        onAssertionFailure(listener, cmd.getData(), cmd.getFailureMessage());
      }
    }
  }
  
  public static void evaluate(I_AssertListener listener, I_ThrownAssertCommand cmd, I_Thrower p) {
    if (cmd.evaluate(p)) {
      synchronized (listener) {
        listener.assertCompleted(cmd);
      }
    } else {
      synchronized (listener) {
        onAssertionFailure(listener, cmd.getData(), cmd.getFailureMessage());
      }
    }
  }

  public static void onAssertionFailure(I_AssertListener listener,
      I_AssertionData data, String failureMessage) {
    
    TestFailureBuilder builder = new TestFailureBuilder();
    TestFailureMutant tfm =  builder.build(data, failureMessage);
    
    AssertionFailureLocation afl = new AssertionFailureLocation(listener.getTrialInstance());
    String locationFailedStackTrace = StackTraceBuilder.toString(afl, true);
    tfm.setFailureDetail(locationFailedStackTrace);
    
    I_TestFailureType tft = tfm.getType();
    TestFailureType type = TestFailureType.get(tft);
    switch(type) {
      case TestFailure:
          TestFailure failure = new TestFailure(tfm);
          listener.assertFailed(failure);
        break;
      case AssertCompareFailure:
          AssertCompareFailure acf = new AssertCompareFailure((I_AssertCompareFailure) tfm);
          listener.assertFailed(acf);
        break;
      default:
          AssertThrownFailure atf = new AssertThrownFailure((I_AssertThrownFailure) tfm);
          listener.assertFailed(atf);
    }
  }
  
}
