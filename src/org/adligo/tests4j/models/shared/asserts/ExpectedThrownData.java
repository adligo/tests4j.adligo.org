package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionInputMessages;

public class ExpectedThrownData implements I_ExpectedThrownData {
	private static final I_Tests4J_AssertionInputMessages MESSAGES = 
			Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
	private String message;
	private Class<? extends Throwable> throwableClass;
	
	public ExpectedThrownData() {}
	
	public ExpectedThrownData(I_ExpectedThrownData p) {
		this(p.getThrowableClass(), p.getMessage());
	}
	
	public ExpectedThrownData(Class<? extends Throwable> pThrowableClass, String pMessage) {
		if (pThrowableClass == null) {
			throw new IllegalArgumentException(MESSAGES.getExpectedThrownDataRequiresThrowable());
		}
		throwableClass = pThrowableClass;
		if (pMessage == null) {
			throw new IllegalArgumentException(MESSAGES.getExpectedThrownDataRequiresMessage());
		}
		message = pMessage;
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
	
}
