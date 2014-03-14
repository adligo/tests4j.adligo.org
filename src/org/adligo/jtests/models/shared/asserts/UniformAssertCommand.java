package org.adligo.jtests.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompare;
import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompareResult;

public class UniformAssertCommand extends AbstractAssertCommand implements I_CompareAssertCommand {
	public static final String THE_CLASSES_DID_NOT_MATCH = "The Classes did not match.";
	public static final String THE_THROWABLE_CLASSES_DID_NOT_MATCH = "The Throwable classes did not match.";
	public static final String BOTH_EXPECTED_AND_ACTUAL_WERE_NULL = "Both expected and actual were null.";
	
	public static final String NULL_DATA = "UniformAssertCommand requires non null CompareAssertionData.";
	public static final String BAD_TYPE = 
			"UniformAssertCommand requires it's type to be one of AssertType.UNIFORM_TYPES";
	private CompareAssertionData data;
	private AssertType type;
	private LineTextCompareResult lineTextResult;
	private String failureSubMessage;
	
	public UniformAssertCommand(I_AssertType pType, String failureMessage, CompareAssertionData pData) {
		super(pType, failureMessage);
		if (!AssertType.UNIFORM_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(NULL_DATA);
		}
	}

	@Override
	public Set<String> getKeys() {
		Set<String> toRet = new HashSet<String>();
		if (lineTextResult != null) {
			toRet.add(LINE_TEXT_RESULT);
		}
		if (failureSubMessage != null) {
			toRet.add(FAILURE_SUB_MESSAGE);
		}
		toRet.addAll(data.getKeys());
		return Collections.unmodifiableSet(toRet);
	}

	@Override
	public Object getData(String key) {
		if (LINE_TEXT_RESULT.equals(key)) {
			return lineTextResult;
		} else if (FAILURE_SUB_MESSAGE.equals(key)) {
			return failureSubMessage;
		}
		return data.getData(key);
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
	public boolean evaluate() {
		Object expected = data.getExpected();
		Object actual = data.getActual();
		
		switch (type) {
			case AssertUniform:
				return assertUniform(expected, actual);
			case AssertNotUniform:
				return !assertUniform(expected, actual);
		}
		return false;
	}

	private boolean assertUniform(Object expected, Object actual) {
		if(expected == null) {
			   if (actual == null) {
				   failureSubMessage = BOTH_EXPECTED_AND_ACTUAL_WERE_NULL;
				   return true;
			   }
		   } else {
			   Throwable exampleThrow = null;
			   try {
				   //note this try catch is for GWT which doesn't have instanceof exc
				   exampleThrow = (Throwable) expected;
				   Throwable actualThrow = (Throwable) actual;
				   if (!exampleThrow.getClass().equals(actualThrow.getClass())) {
					   failureSubMessage = THE_THROWABLE_CLASSES_DID_NOT_MATCH;
					   return false;
				   }
				   lineTextResult =  LineTextCompare.equals(exampleThrow.getMessage(), 
						   actualThrow.getMessage());
				   if (lineTextResult.isMatched()) {
					   return true;
				   } 
			   } catch (ClassCastException x) {
				   if (exampleThrow != null) {
					   return false;
				   }
				   if (String.class.equals(expected.getClass())) {
					   if (String.class.equals(actual.getClass())) {
						   lineTextResult =  LineTextCompare.equals((String) expected, 
								   (String)  actual);
						   if (lineTextResult.isMatched()) {
							   return true;
						   } 
					   } else {
						   failureSubMessage = THE_CLASSES_DID_NOT_MATCH;
						   return false;
					   }
				   }
				   return expected.equals(actual);
			   }
		   }
		return false;
	}
}
