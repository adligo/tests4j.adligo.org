package org.adligo.tests4j.models.shared.common;

import java.util.Collection;

public class MethodBlocker {

	/**
	 * note these are passed and created as strings to
	 * keep Circular dependencies from getting created
	 * @param allowedCallersClassNames
	 */
	public MethodBlocker(Class<?> blockingClass, String methodName, Collection<String> allowedCallersClassNames) {
		Exception x = new Exception();
		x.fillInStackTrace();
		StackTraceElement [] trace = x.getStackTrace();
		if (trace != null && trace.length >= 2) {
			StackTraceElement callingClassE = trace[2];
			String callingClassName = callingClassE.getClassName();
			if (!allowedCallersClassNames.contains(callingClassName)) {
				throw new IllegalStateException("The method " + blockingClass +
						"." + methodName + " may only be called by " + allowedCallersClassNames);
			}
		}
	}
}
