package org.adligo.jtests.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ThrownAssertionData implements I_AssertionData {
	public static final String THROWABLE_ASSERTION_DATA_REQUIRES_A_THROWABLE_CLASS_EXTENDS_THROWABLE = "ThrowableAssertionData requires a throwable Class<? extends Throwable>";
	public static final String THROWABLE_CLASS = "throwable_class";
	public static final String EXPECTED_MESSAGE = "expected_message";
	private Class<? extends Throwable> throwable;
	private String expectedMessage;
	
	public ThrownAssertionData(Class<? extends Throwable> pThrowable, String pExpectedMessage) {
		throwable = pThrowable;
		if (throwable == null) {
			throw new IllegalArgumentException(THROWABLE_ASSERTION_DATA_REQUIRES_A_THROWABLE_CLASS_EXTENDS_THROWABLE);
		}
		expectedMessage = pExpectedMessage;
	}
	
	@Override
	public Set<String> getKeys() {
		Set<String> toRet = new HashSet<String>();
		toRet.add(THROWABLE_CLASS);
		toRet.add(EXPECTED_MESSAGE);
		return Collections.unmodifiableSet(toRet);
	}
	@Override
	public Object getData(String key) {
		if (THROWABLE_CLASS.equals(key)) {
			return throwable;
		} else if (EXPECTED_MESSAGE.equals(key)) {
			return expectedMessage;
		}
		return null;
	}
}
