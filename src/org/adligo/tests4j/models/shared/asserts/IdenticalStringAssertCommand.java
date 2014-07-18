package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleCompareAssertCommand;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.shared.report.summary.LineDiffTextDisplay;


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
	private CompareAssertionData<String> data;
	private AssertType type;
	private I_TextLinesCompareResult result;
	
	public IdenticalStringAssertCommand(AssertType pType, String failureMessage, CompareAssertionData<String> pData) {
		super(pType, failureMessage, pData);
		if (pData.getExpected() == null) {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
		if (!AssertType.EQUAL_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean evaluate() {
		String expected = data.getExpected();
		String actual = data.getActual();
		
		TextLinesCompare tlc = new TextLinesCompare();
		result = tlc.compare(expected, actual, false);
		switch (type) {
			case AssertEquals:
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
				break;
			case AssertNotEquals:
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
				break;
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public I_AssertionData getData() {
		if (result == null) {
			return data;
		}
		StringCompareAssertionData toRet = new StringCompareAssertionData(data, result);
		return toRet;
	}
}
