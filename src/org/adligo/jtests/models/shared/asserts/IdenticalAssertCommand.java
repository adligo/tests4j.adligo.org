package org.adligo.jtests.models.shared.asserts;

import java.util.Set;


public class IdenticalAssertCommand extends AbstractAssertCommand implements I_CompareAssertCommand {
	public static final String NULL_DATA = "IdenticalAssertCommand requires non null CompareAssertionData.";
	private static final String BAD_TYPE = 
			"IdenticalAssertCommand requires it's type to be one of AssertType.IDENTICAL_TYPES";
	private CompareAssertionData data;
	private AssertType type;
	
	public IdenticalAssertCommand(I_AssertType pType, String failureMessage, CompareAssertionData pData) {
		super(pType, failureMessage);
		if (!AssertType.IDENTICAL_TYPES.contains(pType)) {
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
		return data.getKeys();
	}

	@Override
	public Object getData(String key) {
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
			case AssertEquals:
				   if(expected == null) {
					   if (actual == null) {
						   return true;
					   }
				   } else {
					   return expected.equals(actual);
				   }
				break;
			case AssertNotEquals:
				   if(expected == null) {
					   if (actual == null) {
						   return false;
					   }
				   } else {
					   return !expected.equals(actual);
				   }
				break;
			case AssertSame:
				   if(expected == null) {
					   return false;
				   } else {
					   return expected == actual;
				   }
			case AssertNotSame:
				   if(expected == null) {
					   return true;
				   } else {
					   return expected != actual;
				   }
		}
		
		return false;
	}
}
