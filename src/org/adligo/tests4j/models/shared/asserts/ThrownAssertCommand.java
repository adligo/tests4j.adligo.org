package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.models.shared.common.I_Immutable;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

/**
 * a class to to help
 * process a assertThrown assertion.
 * 
 * @author scott
 *
 */
public class ThrownAssertCommand extends AbstractAssertCommand 
	implements I_Immutable, I_ThrownAssertCommand {

	private I_ExpectedThrownData data;
	private Throwable caught;
	
	public ThrownAssertCommand( String pFailureMessage) {
		super(AssertType.AssertThrown, pFailureMessage);
	}
	
	public ThrownAssertCommand(String pFailureMessage, I_ExpectedThrownData pData) {
		super(AssertType.AssertThrown, pFailureMessage);
		data = pData;
		if (data == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
	}

	@Override
	public boolean evaluate(I_Thrower thrower) {
		if (thrower == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getIThrowerIsRequired());
		}
		Class<? extends Throwable> throwableClazz = 
				data.getThrowableClass();
		String expected_message = 
				data.getMessage();
		
		
		try {
			thrower.run();
		} catch (Throwable x) {
			caught = x;
		}
		if (caught == null) {
			return false;
		}
		if ( !throwableClazz.equals(caught.getClass())) {
			return false;
		}
		String caughtMessage = caught.getMessage();
		if (expected_message == null) {
			if (caughtMessage != null) {
				return false;
			}
		} else if (caughtMessage == null) {

			return false;
		} else {
			if ( !expected_message.equals(caughtMessage)) {
				return false;
			}
		}
		
		Throwable cause = caught.getCause();
		I_ExpectedThrownData ec = data.getExpectedCause();
		while (ec != null) {
			if (cause == null) {
				return false;
			}
			Class<? extends Throwable> expectedCauseClass = ec.getThrowableClass();
			if (!cause.getClass().equals(expectedCauseClass)) {
				return false;
			}
			String expectedMessage = ec.getMessage();
			String message = cause.getMessage();
			if (expectedMessage == null) {
				if (message != null) {
					return false;
				}
			} else if (message == null) {

				return false;
			} else {
				if ( !expectedMessage.equals(message)) {
					return false;
				}
			}
			ec = ec.getExpectedCause();
			cause = cause.getCause();
			
		}
		return true;
	}

	@Override
	public I_AssertionData getData() {
		ThrownAssertionDataMutant tadm = new ThrownAssertionDataMutant();
		tadm.setExpectedMessage(data.getMessage());
		tadm.setExpectedThrowable(data.getThrowableClass());
		
		//there may not have been a caught exception
		if (caught != null) {
			tadm.setActualThrowable(caught.getClass());
			tadm.setActualMessage(caught.getMessage());
		}
		return new ThrownAssertionData(tadm);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((caught == null) ? 0 : caught.hashCode());
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
		ThrownAssertCommand other = (ThrownAssertCommand) obj;
		if (caught == null) {
			if (other.caught != null)
				return false;
		} else if (!caught.equals(other.caught))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}
