package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleAssertCommand;


/**
 * a class to represent 
 * boolean asserts (assertTrue, assertFalse, assertNull, assertNotNull)
 * with only one parameter (ie non comparison asserts).
 * 
 * @author scott
 *
 */
public class BooleanAssertCommand extends AbstractAssertCommand 
	implements I_SimpleAssertCommand, I_AssertionData {
	/**
	 * the expected value ie 
	 * true if this is a {@link AssertType#AssertTrue}
	 * false if this is a {@link AssertType#AssertFalse}
	 */
	public static final String EXPECTED_VALUE = "expected_value";
	public static final String VALUE = "value";
	
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
		switch (type) {
			case AssertTrue:
				if (Boolean.TRUE.equals(value)) {
					return true;
				}
				break;
			case AssertFalse:
				if (Boolean.FALSE.equals(value)) {
					return true;
				}
				break;
			case AssertNull:
				if (value == null) {
					return true;
				}
				break;
			case AssertNotNull:
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

	public Set<String> getKeys() {
		// TODO Auto-generated method stub
		return Collections.singleton(VALUE);
	}

	public Object getData(String key) {
		if (VALUE.equals(key)) {
			return value;
		} else if (EXPECTED_VALUE.equals(key)) {
			switch (type) {
				case AssertTrue:
					return true;
				case AssertFalse:
					return false;
				case AssertNull:
					return null;
				case AssertNotNull:
					return this;
			}
		}
		return null;
	}


	@Override
	public I_AssertionData getData() {
		return this;
	}
}