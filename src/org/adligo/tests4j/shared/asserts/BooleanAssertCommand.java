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
	private Object value_;
	AssertType type_;
	
	public BooleanAssertCommand(I_AssertType type,
			String failureMessage, Object value) {
		super(type, failureMessage);
		
		//copy it between classloaders
		type_ = AssertType.getType(type);
		if (!AssertType.BOOLEAN_TYPES.contains(type_)) {
			throw new IllegalArgumentException(
					BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE);
		}
		value_ = value;
	}


	@Override
	public boolean evaluate() {
		//switch statements cause the class to reference java.lang.NoSuchFieldError
		//which isn't in GWT yet
		if (AssertType.AssertTrue == type_) {
			if (Boolean.TRUE.equals(value_)) {
				return true;
			}
		} else if (AssertType.AssertFalse == type_) {
			if (Boolean.FALSE.equals(value_)) {
				return true;
			}
		} else if (AssertType.AssertNull == type_) {
			if (value_ == null) {
				return true;
			}
		} else if (AssertType.AssertNotNull == type_) {
			if (value_ != null) {
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
				+ ((value_ == null) ? 0 : value_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			try {
				BooleanAssertCommand other = (BooleanAssertCommand) obj;
				if (value_ == null) {
					if (other.value_ != null)
						return false;
				} else if (value_.equals(other.value_))
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


	@SuppressWarnings({"boxing", "incomplete-switch"})
  @Override
	public Object getExpected() {
		switch (type_) {
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
		return value_;
	}
}