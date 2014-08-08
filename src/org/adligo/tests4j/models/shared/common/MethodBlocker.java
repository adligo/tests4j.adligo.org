package org.adligo.tests4j.models.shared.common;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

/**
 * This class will block method calls based on the stack.
 * However since there is no stack in GWT, it always allows 
 * the method execution to continue on the first time, then 
 * it start blocking using the stack.
 * 
 * To use it instantiate it and put the checkAllowed()
 * method in the method you want to block.
 * 
 * I have not though this through as a security precaution, 
 * it is simply a way to make fairly sure
 * only a few callers can execute specific methods.
 *   ie ASM could potentially change class names, of the caller or something along those lines to circumvent this  
 *    protection, assuming it was loaded into memory.
 *    
 * @author scott
 *
 */
public class MethodBlocker {
	private static final I_Tests4J_Constants MESSAGES = 
			Tests4J_Constants.CONSTANTS;
	//keep ordered for testing
	private Set<String> allowedCallerNames = new TreeSet<String>();
	private Class<?> blockingClass;
	private String blockedMethodName;
	/**
	 * since this must work in GWT no AtomicBoolean
	 */
	private volatile boolean called = false;
	/**
	 * note these are passed and created as strings to
	 * keep Circular dependencies from getting created
	 * @param allowedCallersClassNames
	 */
	public MethodBlocker(Class<?> pBlockingClass, String pMethodName, Collection<String> pAllowedCallersClassNames) {
		if (pBlockingClass == null) {
			throw new IllegalArgumentException(MESSAGES.getMethodBlockerRequiresABlockingClass());
		}
		if (StringMethods.isEmpty(pMethodName)) {
			throw new IllegalArgumentException(MESSAGES.getMethodBlockerRequiresABlockingMethod());
		}
		if (pAllowedCallersClassNames == null) {
			throw new IllegalArgumentException(MESSAGES.getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames());
		}
		if (pAllowedCallersClassNames.isEmpty()) {
			throw new IllegalArgumentException(MESSAGES.getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames());
		}
		
		
		pAllowedCallersClassNames.remove(null);
		allowedCallerNames.addAll(pAllowedCallersClassNames);
		blockingClass = pBlockingClass;
		blockedMethodName = pMethodName;
		
	}
	
	
	public synchronized void checkAllowed() {
		if (called == false) {
			//allow anyone on the first time
			called = true;
			return;
		}
		//on subsuquent invocations, require class to be in the approved call list.
		final Exception x = new Exception();
		x.fillInStackTrace();
		StackTraceElement [] trace = x.getStackTrace();
		
		StackTraceElement callingClassE = trace[2];
		String callingClassName = callingClassE.getClassName();
		if (!allowedCallerNames.contains(callingClassName)) {
			throw new IllegalStateException(MESSAGES.getTheMethodCanOnlyBeCalledBy_PartOne() + 
					blockingClass + "." + blockedMethodName + 
					MESSAGES.getTheMethodCanOnlyBeCalledBy_PartTwo() + 
					allowedCallerNames);
		}
	}
}
