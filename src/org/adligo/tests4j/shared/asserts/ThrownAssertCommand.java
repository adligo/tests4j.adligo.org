package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_MatchType;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.shared.asserts.common.ThrownAssertionData;
import org.adligo.tests4j.shared.asserts.common.ThrownAssertionDataMutant;
import org.adligo.tests4j.shared.common.I_Immutable;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
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

  private I_Tests4J_Constants constants_;
	private I_ExpectedThrowable expected;
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
	
	public ThrownAssertCommand(I_Tests4J_Constants contants, String pFailureMessage, I_ExpectedThrowable pExpected) {
		super(AssertType.AssertThrown, pFailureMessage);
		constants_ = contants;
		expected = pExpected;
		if (expected == null) {
			I_Tests4J_AssertionInputMessages messages = contants.getAssertionInputMessages();
			
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
	}

	@Override
	public boolean evaluate(I_Thrower thrower) {
	  if (thrower == null) {
			I_Tests4J_AssertionInputMessages messages = constants_.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getIThrowerIsRequired());
		}
		
		failureThrowable = 1;
		try {
			thrower.run();
		} catch (Throwable x) {
			caught = x;
		}
		I_Tests4J_ResultMessages messages =  constants_.getResultMessages();
		if (caught == null) {
			failureReason = messages.getNothingWasThrown();
			return false;
		}

		return evaluateMessageAndCause(expected, caught, 1);
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
	
	private boolean evaluateMessageAndCause(I_ExpectedThrowable expected, Throwable caught, int throwable) {
	  I_Tests4J_ResultMessages messages = constants_.getResultMessages();
	  if (expected == null) {
	    return true;
	  }
	  if (caught == null) {
	    failureThrowable = throwable;
	    failureReason = messages.getThrowableClassMismatch();
      return false;
	  }
	  Class<?> clazz = expected.getThrowableClass();
	  if (!clazz.getName().equals(caught.getClass().getName())) {
	    failureThrowable = throwable;
      failureReason = messages.getThrowableClassMismatch();
      return false;
	  }
    String expectedMessage = expected.getMessage();
    I_MatchType matchType = expected.getMatchType();
    MatchType mt = MatchType.get(matchType);
    String caughtMessage = caught.getMessage();
    
    switch (mt) {
      case ANY:
          //don't compare the messages
        break;
      case CONTAINS:
          if (expectedMessage == null) {
            failureThrowable = throwable;
            failureReason = messages.getThrowableMessagesMismatch();
            return false;
          }
          if (caughtMessage == null) {
            failureThrowable = throwable;
            failureReason = messages.getThrowableMessagesMismatch();
            return false;
          }
          String caughtLower = caughtMessage.toLowerCase();
          String expectedLower = expectedMessage.toLowerCase();
          if (caughtLower.indexOf(expectedLower) == -1) {
            failureThrowable = throwable;
            failureReason = messages.getThrowableMessagesMismatch();
            return false;
          }
        break;
      case EQUALS:
        if (expectedMessage == null) {
          failureThrowable = throwable;
          failureReason = messages.getThrowableMessagesMismatch();
          return false;
        }
        if (!expectedMessage.equals(caughtMessage)) {
          failureThrowable = throwable;
          failureReason = messages.getThrowableMessagesMismatch();
          return false;
        }
        break;
      case NULL:
      default:
        if (caughtMessage != null) {
          failureThrowable = throwable;
          failureReason = messages.getThrowableMessagesMismatch();
          return false;
        }
    }
    I_ExpectedThrowable expectedCause = expected.getExpectedCause();
    if (expectedCause == null) {
      return true;
    }
    return evaluateMessageAndCause(expectedCause, caught.getCause(), throwable + 1);
	}
}
