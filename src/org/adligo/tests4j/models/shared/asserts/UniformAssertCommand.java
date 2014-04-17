package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;

public abstract class UniformAssertCommand extends AbstractAssertCommand 
	implements I_CompareAssertCommand {
	
	public static final String THE_CLASSES_DID_NOT_MATCH = "The Classes did not match.";
	public static final String THE_THROWABLE_CLASSES_DID_NOT_MATCH = "The Throwable classes did not match.";
	public static final String BOTH_EXPECTED_AND_ACTUAL_WERE_NULL = "Both expected and actual were null.";
	
	public static final String NULL_DATA = "UniformAssertCommand requires non null CompareAssertionData.";
	public static final String BAD_TYPE = 
			"UniformAssertCommand requires it's type to be one of AssertType.UNIFORM_TYPES";
	protected CompareAssertionData<?> data;
	protected AssertType type;
	protected LineTextCompareResult lineTextResult;
	protected String failureSubMessage;
	
	public UniformAssertCommand(I_AssertType pType, String failureMessage, CompareAssertionData<?> pData) {
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

	
}
