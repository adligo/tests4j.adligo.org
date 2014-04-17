package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.common.IsEmpty;

public abstract class AbstractAssertCommand implements I_AssertCommand {
	public static final String ASSERT_COMMANDS_REQURES_A_NON_EMPTY_FAILUE_MESSAGE = "AssertCommands requres a non empty failue message.";
	public static final String ASSERT_COMMANDS_REQUIRES_A_TYPE = "AssertCommands requires a boolean type.";
	
	private String failureMessage;
	private I_AssertType type;
	
	AbstractAssertCommand(I_AssertType pType, String pFailureMessage) {
		type = pType;
		if (type == null) {
			throw new IllegalArgumentException(
					ASSERT_COMMANDS_REQURES_A_NON_EMPTY_FAILUE_MESSAGE);
		}
		failureMessage = pFailureMessage;
		IsEmpty.isEmpty(failureMessage, 
				ASSERT_COMMANDS_REQURES_A_NON_EMPTY_FAILUE_MESSAGE);
	}

	@Override
	public I_AssertType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		try {
			AbstractAssertCommand other = (AbstractAssertCommand) obj;
			if (failureMessage == null) {
				if (other.failureMessage != null)
					return false;
			} else if (!failureMessage.equals(other.failureMessage))
				return false;
			if (type != other.type)
				return false;
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}

	@Override
	public String getFailureMessage() {
		return failureMessage;
	}
}
