package org.adligo.tests4j.models.shared.common;

import java.util.Collection;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

/**
 * This class will block method calls based on the stack.
 * To use instantiate it in your method.
 * 
 * @author scott
 *
 */
public class MethodBlocker {
	private static final I_Tests4J_Constants MESSAGES = 
			Tests4J_Constants.CONSTANTS;
	
	/**
	 * note these are passed and created as strings to
	 * keep Circular dependencies from getting created
	 * @param allowedCallersClassNames
	 */
	public MethodBlocker(Class<?> blockingClass, String methodName, Collection<String> allowedCallersClassNames) {
		
		if (allowedCallersClassNames.isEmpty()) {
			throw new IllegalArgumentException(MESSAGES.getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames());
		}
		
		final Exception x = new Exception();
		x.fillInStackTrace();
		StackTraceElement [] trace = x.getStackTrace();
		
		StackTraceElement callingClassE = trace[2];
		String callingClassName = callingClassE.getClassName();
		if (!allowedCallersClassNames.contains(callingClassName)) {
			throw new IllegalStateException(MESSAGES.getTheMethodCanOnlyBeCalledBy_PartOne() + 
					blockingClass + "." + methodName + 
					MESSAGES.getTheMethodCanOnlyBeCalledBy_PartTwo() + 
					allowedCallersClassNames);
		}
	}
}
