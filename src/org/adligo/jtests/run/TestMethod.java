package org.adligo.jtests.run;

import java.lang.reflect.Method;

public class TestMethod {
	private long timeoutMillis;
	private Method method;
	private boolean ignore;
	
	public TestMethod(Method m, long pTimeout, boolean pIgnore) {
		timeoutMillis = pTimeout;
		method = m;
		if (method == null) {
			throw new IllegalArgumentException("TestMethod requires a method");
		}
		ignore = pIgnore;
	}

	public long getTimeoutMillis() {
		return timeoutMillis;
	}

	public Method getMethod() {
		return method;
	}

	public boolean isIgnore() {
		return ignore;
	}
}
