package org.adligo.jtests.models.shared.asserts;



public class IdenticalAssertCommand extends AbstractCompareAssertCommand implements I_CompareAssertCommand {
	private static final String BAD_TYPE = 
			"IdenticalAssertCommand requires it's type to be one of AssertType.IDENTICAL_TYPES";
	private CompareAssertionData<?> data;
	private AssertType type;
	
	public IdenticalAssertCommand(AssertType pType, String failureMessage, CompareAssertionData<?> pData) {
		super(pType, failureMessage, pData);
		if (!AssertType.IDENTICAL_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
	}

	@SuppressWarnings("incomplete-switch")
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
