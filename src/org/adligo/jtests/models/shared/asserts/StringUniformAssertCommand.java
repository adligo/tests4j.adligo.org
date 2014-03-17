package org.adligo.jtests.models.shared.asserts;

import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompare;

public class StringUniformAssertCommand extends UniformAssertCommand {
	private CompareAssertionData<String> data;
	
	public StringUniformAssertCommand(I_AssertType pType, String failureMessage,
			CompareAssertionData<String> pData) {
		super(pType, failureMessage, pData);
		data = pData;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean evaluate() {
		String expected = data.getExpected();
		String actual = data.getActual();
		
		switch (type) {
			case AssertUniform:
				return assertUniform(expected, actual);
			case AssertNotUniform:
				return !assertUniform(expected, actual);
		}
		return false;
	}

	private boolean assertUniform(String expected, String actual) {
		if(expected == null) {
			   if (actual == null) {
				   failureSubMessage = BOTH_EXPECTED_AND_ACTUAL_WERE_NULL;
				   return true;
			   }
		   } else {
			   lineTextResult =  LineTextCompare.compare(expected, actual);
			   if (lineTextResult.isMatched()) {
				   return true;
			   } 
		   }
		return false;
	}

}
