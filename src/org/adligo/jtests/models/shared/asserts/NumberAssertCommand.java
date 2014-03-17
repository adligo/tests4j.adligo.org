package org.adligo.jtests.models.shared.asserts;

import java.math.BigDecimal;
import java.util.Set;


public class NumberAssertCommand extends AbstractAssertCommand implements I_CompareAssertCommand {
	public static final String NULL_DATA = "NumberAssertCommand requires non null CompareAssertionData.";
	private static final String BAD_TYPE = 
			"NumberAssertCommand requires it's type to be one of AssertType.NUMBER_TYPES";
	private CompareAssertionData<Number> data;
	private AssertType type;
	
	public NumberAssertCommand(I_AssertType pType, String failureMessage, CompareAssertionData<Number> pData) {
		super(pType, failureMessage);
		if (!AssertType.NUMBER_TYPES.contains(pType)) {
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
		Number expected = data.getExpected();
		Number actual = data.getActual();
		
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
			
		}
		
		return false;
	}
}
