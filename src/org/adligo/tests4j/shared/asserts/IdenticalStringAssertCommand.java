package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.CompareAssertionData;
import org.adligo.tests4j.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.shared.asserts.common.I_SimpleCompareAssertCommand;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;


/**
 * a class that
 * represents the comparison of two objects to see if they are
 * identical (equals or same).
 * 
 * @author scott
 *
 */
public class IdenticalStringAssertCommand extends AbstractCompareAssertCommand 
	implements I_SimpleCompareAssertCommand {
	public static final String BAD_TYPE = 
			"IdenticalStringAssertCommand requires it's type to be one of AssertType.EQUAL_TYPES";
	private I_Tests4J_Constants constants_;
	private CompareAssertionData<String> data;
	private I_TextLinesCompareResult result;
	
	public IdenticalStringAssertCommand(I_Tests4J_Constants constants, String failureMessage, CompareAssertionData<String> pData) {
		super(failureMessage, pData);
		constants_ = constants;
		if (pData.getExpected() == null) {
			I_Tests4J_AssertionInputMessages messages = constants.getAssertionInputMessages();
			
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
		//classloader issues
		AssertType type = AssertType.getType(super.getType());
		if (!AssertType.EQUAL_TYPES.contains(type)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		data = pData;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean evaluate() {
		String expected = data.getExpected();
		String actual = data.getActual();
		
		TextLinesCompare tlc = new TextLinesCompare();
		result = tlc.compare(constants_, expected, actual, false);
		
		//classloader issues
		AssertType type = AssertType.getType(super.getType());
		//switch statements cause the class to reference java.lang.NoSuchFieldError
		//which isn't in GWT yet
		if (AssertType.AssertEquals == type) {
			   if(expected == null) {
				   if (actual == null) {
					   return true;
				   }
			   } else {
				   boolean toRet = result.isMatched();
				   if (toRet) {
					   result = null;
				   }
				   return toRet;
			   }
		} else if (AssertType.AssertNotEquals == type) {
			   if(expected == null) {
				   if (actual == null) {
					   return false;
				   }
			   } else {
				   boolean toRet = !result.isMatched();
				   if (toRet) {
					   result = null;
				   }
				   return toRet;
			   }
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdenticalStringAssertCommand other = (IdenticalStringAssertCommand) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public I_AssertionData getData() {
		if (result == null) {
			return data;
		}
		//classloader issues
		AssertType type = AssertType.getType(super.getType());
		CompareAssertionData<String> toRet = new CompareAssertionData<String>(
				data.getExpected(), data.getActual(), type);
		return toRet;
	}
}
