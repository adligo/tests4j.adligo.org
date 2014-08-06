package org.adligo.tests4j.models.shared.asserts.common;


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
	private I_AssertionData data;
	private String failureMessage;
	private AssertType type;
	
	public TestFailureMutant build(I_AssertionData pData, String pFailureMessage) {
		data = pData;
		failureMessage = pFailureMessage;
		if (data == null) {
			return buildError();
		}
		I_AssertType at = data.getType();
		
		if (at == null) {
			return buildError();
		} else {
			type =  AssertType.getType(at);
			switch (type) {
				case AssertThrownUniform:
				case AssertThrown:
					return buildThrown();
				case AssertTrue:
				case AssertFalse:
					return buildBoolean();
				case AssertNull:
					return buildNullOrNotNull();
				case AssertContains:
					return buildContains();
				default:
					return buildDefault();
			}
		}
	}

	private TestFailureMutant buildError() {
		AssertCompareFailureMutant atfm = new AssertCompareFailureMutant();
		atfm.setFailureMessage(failureMessage);
		return atfm;
	}
	
	private AssertCompareFailureMutant buildDefault() {
		I_CompareAssertionData<?> cad = (I_CompareAssertionData<?>) data;
		AssertCompareFailureMutant atfm = new AssertCompareFailureMutant();
		atfm.setAssertType(type);
		atfm.setFailureMessage(failureMessage);
		
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

	private AssertCompareFailureMutant buildNullOrNotNull() {
		I_CompareAssertionData<?> cdNot = (I_CompareAssertionData<?>) data;
		AssertCompareFailureMutant tfmNot = new AssertCompareFailureMutant();
		tfmNot.setAssertType(type);
		tfmNot.setFailureMessage(failureMessage);
		
		return tfmNot;
	}

	private AssertCompareFailureMutant buildContains() {
		I_CollectionContainsAssertionData cdNot = (I_CollectionContainsAssertionData) data;
		AssertCompareFailureMutant toRet = new AssertCompareFailureMutant();
		toRet.setAssertType(type);
		toRet.setFailureMessage(failureMessage);
		
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
	private AssertCompareFailureMutant buildBoolean() {
		I_CompareAssertionData<?> cd = (I_CompareAssertionData<?>) data;
		AssertCompareFailureMutant tfm = new AssertCompareFailureMutant();
		tfm.setAssertType(type);
		tfm.setFailureMessage(failureMessage);
		
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
		I_ThrownAssertionData tad = (I_ThrownAssertionData) data;
		
		AssertThrownFailureMutant mut = new AssertThrownFailureMutant();
		mut.setAssertType(type);
		mut.setFailureMessage(failureMessage);
		
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
