package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.Set;


public class BooleanAssertCommand extends AbstractAssertCommand 
	implements I_BasicAssertCommand, I_AssertionData {
	
	public static final String VALUE = "value";
	public static final String BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE = "BooleanAssertCommand requires a boolean type.";
	private Object value;
	AssertType type;
	
	public BooleanAssertCommand(I_AssertType pType,
			String pFailureMessage, Object pValue) {
		super(pType, pFailureMessage);
		if (!AssertType.BOOLEAN_TYPES.contains(pType)) {
			throw new IllegalArgumentException(
					BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE);
		}
		type = (AssertType) pType;
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
				} else if (!value.equals(other.value))
					return false;
			} catch (ClassCastException x) {
				return false;
			}
			return true;
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
		}
		return null;
	}


	@Override
	public I_AssertionData getData() {
		return this;
	}
}