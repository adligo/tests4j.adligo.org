package org.adligo.tests4j.shared.asserts.common;


/**
 * a model like (not threadsafe)
 * class to build TestFailure instances
 * it returns a mutant
 * so that the AssertionProcessor can put the location failed correctly.
 * 
 * @author scott
 *
 */
public class TestFailureBuilder {
  private I_AssertionData data_;
  private String failureMessage_;
  private AssertType type_;
  
  public TestFailureMutant build(I_AssertionData data, String failureMessage) {
    data_ = data;
    failureMessage_ = failureMessage;
    if (data_ == null) {
      return buildError();
    }
    I_AssertType at = data_.getType();
    
    if (at == null) {
      return buildError();
    } else {
      type_ =  AssertType.getType(at);
      switch (type_) {
        case AssertThrownUniform:
        case AssertThrown:
          return buildThrown();
        case AssertTrue:
        case AssertFalse:
          return buildTrueFalse();
        case AssertContains:
          return buildContains();
        default:
          return buildDefault();
      }
    }
  }

  private TestFailureMutant buildError() {
    AssertCompareFailureMutant atfm = new AssertCompareFailureMutant();
    atfm.setFailureMessage(failureMessage_);
    return atfm;
  }
  
  private AssertCompareFailureMutant buildDefault() {
    I_CompareAssertionData<?> cad = (I_CompareAssertionData<?>) data_;
    AssertCompareFailureMutant atfm = new AssertCompareFailureMutant();
    atfm.setAssertType(type_);
    atfm.setFailureMessage(failureMessage_);
    
    Object cadExp = cad.getExpected();
    if (cadExp != null) {
      atfm.setExpectedValue(cadExp.toString());
      atfm.setExpectedClass(cadExp.getClass().getName());
    }
    Object cadObj = cad.getActual();
    if (cadObj != null) {
      atfm.setActualValue(cadObj.toString());
      atfm.setActualClass(cadObj.getClass().getName());
    }
    return atfm;
  }

  
  private AssertCompareFailureMutant buildContains() {
    I_CollectionContainsAssertionData cdNot = (I_CollectionContainsAssertionData) data_;
    AssertCompareFailureMutant toRet = new AssertCompareFailureMutant();
    toRet.setAssertType(type_);
    toRet.setFailureMessage(failureMessage_);
    
    Object value = cdNot.getValue();
    if (value != null) {
      toRet.setExpectedValue(value.toString());
      toRet.setExpectedClass(value.getClass().getName());
    } else {
      toRet.setExpectedValue("null");
      toRet.setExpectedClass("null");
    }
    return toRet;
  }
  private AssertCompareFailureMutant buildTrueFalse() {
    I_CompareAssertionData<?> cd = (I_CompareAssertionData<?>) data_;
    AssertCompareFailureMutant tfm = new AssertCompareFailureMutant();
    tfm.setAssertType(type_);
    tfm.setFailureMessage(failureMessage_);
    
    Object expected = cd.getExpected();
    if (expected != null) {
      tfm.setExpectedValue(expected.toString());
      tfm.setExpectedClass(Boolean.class.getName());
    }
    Object aObj = cd.getActual();
    if (aObj != null) {
      tfm.setActualValue(aObj.toString());
      tfm.setActualClass(Boolean.class.getName());
    }
    return tfm;
  }

  private AssertThrownFailureMutant buildThrown() {
    I_ThrownAssertionData tad = (I_ThrownAssertionData) data_;
    
    AssertThrownFailureMutant mut = new AssertThrownFailureMutant();
    mut.setAssertType(type_);
    mut.setFailureMessage(failureMessage_);
    
    I_ExpectedThrownData exp = tad.getExpected();
    ThrowableInfo ei = new ThrowableInfo(exp);
    mut.setExpected(ei);
    
    Throwable actual = tad.getActual();
    if (actual != null) {
      ThrowableInfo ai = new ThrowableInfo(actual);
      mut.setActual(ai);
    }
    mut.setFailureReason(tad.getFailureReason());
    mut.setThrowable(tad.getFailureThrowable());
    return mut;
  }
}
