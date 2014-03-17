package org.adligo.jtests.models.shared.asserts;

import java.util.Set;


public class ThrowableAssertCommand extends AbstractCompareAssertCommand implements I_CompareAssertCommand {
	private static final String BAD_TYPE = 
			"NumberAssertCommand requires it's type to be one of AssertType.NUMBER_TYPES";
	private CompareAssertionData<Throwable> data;
	private AssertType type;
	
	public ThrowableAssertCommand(AssertType pType, String failureMessage, CompareAssertionData<Throwable> pData) {
		super(pType, failureMessage, pData);
		if (!AssertType.NUMBER_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
	}

	@Override
	public boolean evaluate() {
		Throwable expected = data.getExpected();
		Throwable actual = data.getActual();
		
		switch (type) {
			case AssertEquals:
				   if(expected == null) {
					   if (actual == null) {
						   return true;
					   }
				   } else if (actual == null){
					   return false;
				   } else {
					   if (!expected.getClass().equals(actual.getClass())) {
						   return false;
					   }
					   return expected.getMessage().equals(actual.getMessage());
				   }
				break;
			case AssertNotEquals:
				   if(expected == null) {
					   if (actual == null) {
						   return false;
					   }
				   } else if (actual == null){
					   return false;
				   } else {
					   if (!expected.getClass().equals(actual.getClass())) {
						   return true;
					   }
					   return !expected.getMessage().equals(actual.getMessage());
				   }
				break;
			
		}
		return false;
	}
}
