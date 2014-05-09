package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult;

public abstract class UniformAssertCommand extends AbstractAssertCommand 
	implements I_CompareAssertCommand {
	
	public static final String THE_CLASSES_DID_NOT_MATCH = "The Classes did not match.";
	public static final String THE_THROWABLE_CLASSES_DID_NOT_MATCH = "The Throwable classes did not match.";
	public static final String BOTH_EXPECTED_AND_ACTUAL_WERE_NULL = "Both expected and actual were null.";
	
	public static final String NULL_DATA = "UniformAssertCommand requires non null CompareAssertionData.";
	public static final String BAD_TYPE = 
			"UniformAssertCommand requires it's type to be one of AssertType.UNIFORM_TYPES";
	protected I_CompareAssertionData<?> data;
	protected AssertType type;
	protected I_LineTextCompareResult lineTextResult;
	protected String failureSubMessage;
	
	public UniformAssertCommand(I_AssertType pType, String failureMessage, I_CompareAssertionData<?> pData) {
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
}
