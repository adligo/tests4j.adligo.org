package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

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
		if (message == null) {
			I_Tests4J_AssertionInputMessages messages = 
					Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getExpectedThrownDataRequiresMessage());
		}
		
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
	
}
