package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionInputMessages;
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
	
	public ExpectedThrownData() {}
	
	public ExpectedThrownData(I_ExpectedThrownData p) {
		this(p.getInstance());
	}

	
	public ExpectedThrownData(Throwable t) {
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
		ExpectedThrownData other = (ExpectedThrownData) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (throwableClass == null) {
			if (other.throwableClass != null)
				return false;
		} else if (!throwableClass.equals(other.throwableClass))
			return false;
		return true;
	}
	
}
