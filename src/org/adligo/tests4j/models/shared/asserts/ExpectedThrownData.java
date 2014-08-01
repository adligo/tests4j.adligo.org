package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

/**
 * a immutable class to represent
 * when the author of a test expects 
 * a Throwable to be thrown.
 * 
 * @author scott
 *
 */
public class ExpectedThrownData implements I_ExpectedThrownData {
	private String message;
	private Class<? extends Throwable> throwableClass;
	private Throwable instance;
	private ExpectedThrownData expectedCause;
	
	public ExpectedThrownData() {}
	
	public ExpectedThrownData(I_ExpectedThrownData p) {
		Throwable inst = p.getInstance();
		if (inst == null) {
			setupThrowableClass(p.getThrowableClass());
		} else {
			setupInstance(inst);
		}
	}
	
	/**
	 * assert only the throwable class matches
	 * @param clazz
	 */
	public ExpectedThrownData(Class<? extends Throwable> clazz) {
		setupThrowableClass(clazz);
	}

	public ExpectedThrownData(Class<? extends Throwable> clazz, I_ExpectedThrownData p) {
		setupThrowableClass(clazz);
		setupCausationChain(p);
	}
	
	/**
	 * assert only the throwable class and it's message matches
	 * @param clazz
	 */
	private void setupThrowableClass(Class<? extends Throwable> clazz) {
		if (clazz == null) {
			I_Tests4J_AssertionInputMessages messages = 
					Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getExpectedThrownDataRequiresThrowable());
		}
		throwableClass = clazz;
	}
	
	/**
	 * assert the throwable class and messages match
	 * @param t
	 */
	public ExpectedThrownData(Throwable t) {
		setupInstance(t);
	}
	public ExpectedThrownData(Throwable t, I_ExpectedThrownData p) {
		setupInstance(t);
		setupCausationChain(p);
	}

	protected void setupCausationChain(I_ExpectedThrownData p) {
		//expect non null input
		expectedCause = new ExpectedThrownData(p);
		I_ExpectedThrownData dec = p.getExpectedCause();
		if (dec != null) {
			expectedCause.expectedCause = new ExpectedThrownData(dec);
		}
	}
	private void setupInstance(Throwable t) {
		if (t == null) {
			I_Tests4J_AssertionInputMessages messages = 
					Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getExpectedThrownDataRequiresThrowable());
		}
		instance = t;
		throwableClass = t.getClass();
		message = t.getMessage();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ExpectedThrownData#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ExpectedThrownData#getThrowableClass()
	 */
	@Override
	public Class<? extends Throwable> getThrowableClass() {
		return throwableClass;
	}

	@Override
	public Throwable getInstance() {
		return instance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((throwableClass == null) ? 0 : throwableClass.getName().hashCode());
		result = prime * result
				+ ((expectedCause == null) ? 0 : expectedCause.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_ExpectedThrownData other = (I_ExpectedThrownData) obj;
			if (message == null) {
				if (other.getMessage() != null)
					return false;
			} else if (!message.equals(other.getMessage()))
				return false;
			if (throwableClass == null) {
				if (other.getThrowableClass() != null)
					return false;
			} else if (!throwableClass.equals(other.getThrowableClass()))
				return false;
			if (expectedCause == null) {
				if (other.getExpectedCause() != null)
					return false;
			} else if (!expectedCause.equals(other.getExpectedCause()))
				return false;
		} catch (ClassCastException x) {
			//do nothing 
		}
		return true;
	}

	@Override
	public I_ExpectedThrownData getExpectedCause() {
		return expectedCause;
	}
	
}
