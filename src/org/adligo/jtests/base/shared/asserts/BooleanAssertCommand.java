package org.adligo.jtests.base.shared.asserts;

import java.util.Collections;
import java.util.Map;

import org.adligo.jtests.models.shared.common.IsEmpty;

public class BooleanAssertCommand implements I_AssertCommand {
	public static final String BOOLEAN_ASSERT_COMMAND_REQURES_A_NON_EMPTY_FAILUE_MESSAGE = "BooleanAssertCommand requres a non empty failue message.";
	public static final String BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE = "BooleanAssertCommand requires a boolean type.";
	private String failureMessage;
	private AssertCommandType type;
	private Object assertion;
	
	public BooleanAssertCommand(AssertCommandType pType,
			String pFailureMessage, Object pAssertion) {
		type = pType;
		if (!AssertCommandType.BOOLEAN_TYPES.contains(type)) {
			throw new IllegalArgumentException(
					BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE);
		}
		failureMessage = pFailureMessage;
		IsEmpty.isEmpty(failureMessage, 
				BOOLEAN_ASSERT_COMMAND_REQURES_A_NON_EMPTY_FAILUE_MESSAGE);
		assertion = pAssertion;
	}

	@Override
	public AssertCommandType getType() {
		return type;
	}

	@Override
	public boolean evaluate() {
		switch (type) {
			case AssertTrue:
				if (Boolean.TRUE.equals(assertion)) {
					return true;
				}
				break;
			case AssertFalse:
				if (Boolean.FALSE.equals(assertion)) {
					return true;
				}
				break;
			case AssertNull:
				if (assertion == null) {
					return true;
				}
				break;
			case AssertNotNull:
				if (assertion != null) {
					return true;
				}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assertion == null) ? 0 : assertion.hashCode());
		result = prime * result
				+ ((failureMessage == null) ? 0 : failureMessage.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BooleanAssertCommand other = (BooleanAssertCommand) obj;
		if (assertion == null) {
			if (other.assertion != null)
				return false;
		} else if (!assertion.equals(other.assertion))
			return false;
		if (failureMessage == null) {
			if (other.failureMessage != null)
				return false;
		} else if (!failureMessage.equals(other.failureMessage))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String getFailureMessage() {
		return failureMessage;
	}

	@Override
	public Object getExpected() {
		switch (type) {
			case AssertTrue:
				return Boolean.TRUE;
			case AssertFalse:
				return Boolean.FALSE;
			default:
				return type;
		}
	}

	@Override
	public Object getActual() {
		return assertion;
	}

	@Override
	public Map<String, ?> getAdditionalData() {
		return Collections.EMPTY_MAP;
	}
}
