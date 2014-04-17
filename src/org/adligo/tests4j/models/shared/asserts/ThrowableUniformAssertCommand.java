package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompare;

public class ThrowableUniformAssertCommand extends UniformAssertCommand {
	private CompareAssertionData<Throwable> data;
	
	public ThrowableUniformAssertCommand(I_AssertType pType, String failureMessage,
			CompareAssertionData<Throwable> pData) {
		super(pType, failureMessage, pData);
		data = pData;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean evaluate() {
		Throwable expected = data.getExpected();
		Throwable actual = data.getActual();
		
		switch (type) {
			case AssertUniform:
				return assertUniform(expected, actual);
			case AssertNotUniform:
				return !assertUniform(expected, actual);
		}
		return false;
	}

	private boolean assertUniform(Throwable expected, Throwable actual) {
		if(expected == null) {
			   if (actual == null) {
				   failureSubMessage = BOTH_EXPECTED_AND_ACTUAL_WERE_NULL;
				   return true;
			   }
		   } else if (actual == null){ 
			  return false;
		   } else {
			   if (!expected.getClass().equals(actual.getClass())) {
				   super.failureSubMessage = "The exception classes are not the same.";
				   return false;
			   }
			   lineTextResult =  LineTextCompare.compare(expected.getMessage(), actual.getMessage());
			   if (lineTextResult.isMatched()) {
				   return true;
			   } 
		   }
		return false;
	}

}
