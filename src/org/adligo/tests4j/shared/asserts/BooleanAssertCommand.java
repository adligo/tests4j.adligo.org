package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.shared.asserts.common.I_SimpleAssertCommand;


/**
 * a class to represent 
 * boolean asserts (assertTrue, assertFalse, assertNull, assertNotNull)
 * with only one parameter (ie non comparison asserts).
 * 
 * @author scott
 *
 */
public class BooleanAssertCommand extends AbstractAssertCommand 
	implements I_SimpleAssertCommand, I_CompareAssertionData<Object> {
	
	public static final String BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE = "BooleanAssertCommand requires a boolean type.";
	private Object value;
	AssertType type;
	
	public BooleanAssertCommand(I_AssertType pType,
			String pFailureMessage, Object pValue) {
		super(pType, pFailureMessage);
		
		//copy it between classloaders
		type = AssertType.getType(pType);
		if (!AssertType.BOOLEAN_TYPES.contains(type)) {
			throw new IllegalArgumentException(
					BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE);
		}
		value = pValue;
	}


	@Override
	public boolean evaluate() {
		//switch statements cause the class to reference java.lang.NoSuchFieldError
		//which isn't in GWT yet
		if (AssertType.AssertTrue == type) {
			if (Boolean.TRUE.equals(value)) {
				return true;
			}
		} else if (AssertType.AssertFalse == type) {
			if (Boolean.FALSE.equals(value)) {
				return true;
			}
		} else if (AssertType.AssertNull == type) {
			if (value == null) {
				return true;
			}
		} else if (AssertType.AssertNotNull == type) {
			if (value != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			try {
				BooleanAssertCommand other = (BooleanAssertCommand) obj;
				if (value == null) {
					if (other.value != null)
						return false;
				} else if (value.equals(other.value))
					return true;
			} catch (ClassCastException x) {
				return false;
			}
		}
		return false;
	}



	@Override
	public I_AssertionData getData() {
		return this;
	}


	@Override
	public Object getExpected() {
		switch (type) {
			case AssertTrue:
				return true;
			case AssertFalse:
				return false;
			case AssertNull:
				return null;
			case AssertNotNull:
			  //a non null instance
				return "";
		}
	//a non null instance
		return "";
	}


	@Override
	public Object getActual() {
		return value;
	}
}