package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.AssertThrownFailure;
import org.adligo.tests4j.shared.asserts.common.AssertThrownFailureMutant;
import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.shared.asserts.common.I_TestFailure;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.shared.asserts.common.ThrowableInfo;
import org.adligo.tests4j.shared.asserts.common.ThrownAssertionData;
import org.adligo.tests4j.shared.asserts.common.ThrownAssertionDataMutant;
import org.adligo.tests4j.shared.common.I_Immutable;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;

/**
 * a class to to help
 * process a assertThrown assertion.
 * 
 * @author scott
 *
 */
public class ThrownAssertCommand extends AbstractAssertCommand 
	implements I_Immutable, I_ThrownAssertCommand {

	private I_ExpectedThrownData expected;
	private Throwable caught;
	/**
	 * which exception in the chain 
	 * didn't match, one based (not zero based).
	 */
	private int failureThrowable = 1;
	/**
	 * the reason the throwable wasn't a match.
	 */
	private String failureReason;
	
	public ThrownAssertCommand( String pFailureMessage) {
		super(AssertType.AssertThrown, pFailureMessage);
	}
	
	public ThrownAssertCommand(String pFailureMessage, I_ExpectedThrownData pExpected) {
		super(AssertType.AssertThrown, pFailureMessage);
		expected = pExpected;
		if (expected == null) {
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
				expected.getThrowableClass();
		String expected_message = 
				expected.getMessage();
		
		failureThrowable = 1;
		try {
			thrower.run();
		} catch (Throwable x) {
			caught = x;
		}
		if (caught == null) {
			I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
			failureReason = messages.getNothingWasThrown();
			return false;
		}
		if ( !throwableClazz.equals(caught.getClass())) {
			I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
			failureReason = messages.getThrowableClassMismatch();
			return false;
		}
		String caughtMessage = caught.getMessage();
		if (expected_message == null) {
			if (caughtMessage != null) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				failureReason = messages.getThrowableMessageNotEquals();
				return false;
			}
		} else if (caughtMessage == null) {
			I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
			failureReason = messages.getThrowableMessageNotEquals();
			return false;
		} else {
			if ( !expected_message.equals(caughtMessage)) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				failureReason = messages.getThrowableMessageNotEquals();
				return false;
			}
		}
		
		Throwable cause = caught.getCause();
		I_ExpectedThrownData ec = expected.getExpectedCause();
		while (ec != null) {
			failureThrowable++;
			Class<? extends Throwable> expectedCauseClass = ec.getThrowableClass();
			if (cause == null) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				failureReason = messages.getThrowableClassMismatch();
				return false;
			}
			
			if (!cause.getClass().equals(expectedCauseClass)) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				failureReason = messages.getThrowableClassMismatch();
				return false;
			}
			String expectedMessage = ec.getMessage();
			String message = cause.getMessage();
			if (expectedMessage == null) {
				if (message != null) {
					I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
					failureReason = messages.getThrowableMessageNotEquals();
					return false;
				}
			} else if (message == null) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				failureReason = messages.getThrowableMessageNotEquals();
				return false;
			} else {
				if ( !expectedMessage.equals(message)) {
					I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
					failureReason = messages.getThrowableMessageNotEquals();
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
		tadm.setType(AssertType.AssertThrown);
		tadm.setExpected(expected);
		if (caught != null) {
			tadm.setActual(caught);
		}
		tadm.setFailureReason(failureReason);
		tadm.setFailureThrowable(failureThrowable);
		return new ThrownAssertionData(tadm);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((caught == null) ? 0 : caught.hashCode());
		result = prime * result + ((expected == null) ? 0 : expected.hashCode());
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
		if (expected == null) {
			if (other.expected != null)
				return false;
		} else if (!expected.equals(other.expected))
			return false;
		return true;
	}

	@SuppressWarnings("boxing")
  public Integer getFailureThrowable() {
		return failureThrowable;
	}

	public String getFailureReason() {
		return failureReason;
	}
}
