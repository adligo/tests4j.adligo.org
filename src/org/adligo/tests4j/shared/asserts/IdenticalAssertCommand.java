package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.CompareAssertionData;
import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.shared.asserts.common.I_SimpleCompareAssertCommand;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;


/**
 * a class that
 * represents the comparison of two objects to see if they are
 * identical (equals or same).
 * 
 * @author scott
 *
 */
public class IdenticalAssertCommand extends AbstractCompareAssertCommand 
	implements I_SimpleCompareAssertCommand {
	public static final String BAD_TYPE = 
			"IdenticalAssertCommand requires it's type to be one of AssertType.IDENTICAL_TYPES";
	private CompareAssertionData<?> data;
	
	public IdenticalAssertCommand(String failureMessage, CompareAssertionData<?> pData) {
		super(failureMessage, pData);
		if (pData.getExpected() == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
		//classloader issues
		AssertType type = AssertType.getType(super.getType());
		if (!AssertType.IDENTICAL_TYPES.contains(type)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		//copy it between classloaders
		data = pData;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean evaluate() {
		Object expected = data.getExpected();
		Object actual = data.getActual();
		
		//classloader issues
		AssertType type = AssertType.getType(super.getType());
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
		IdenticalAssertCommand other = (IdenticalAssertCommand) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public I_AssertionData getData() {
		return data;
	}
}
