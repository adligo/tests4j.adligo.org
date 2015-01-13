package org.adligo.tests4j.shared.common;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;

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
	//keep ordered for testing
  private I_Tests4J_Constants constants_;
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
	public MethodBlocker(I_Tests4J_Constants constants, Class<?> pBlockingClass, String pMethodName, Collection<String> pAllowedCallersClassNames) {
	  constants_ = constants;
	  if (pBlockingClass == null) {
			throw new IllegalArgumentException(constants.getMethodBlockerRequiresABlockingClass());
		}
		if (StringMethods.isEmpty(pMethodName)) {
			throw new IllegalArgumentException(constants.getMethodBlockerRequiresABlockingMethod());
		}
		if (pAllowedCallersClassNames == null) {
			throw new IllegalArgumentException(constants.getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames());
		}
		if (pAllowedCallersClassNames.isEmpty()) {
			throw new IllegalArgumentException(constants.getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames());
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
			throw new IllegalStateException(constants_.getTheMethodCanOnlyBeCalledBy_PartOne() + 
					blockingClass + "." + blockedMethodName + 
					constants_.getTheMethodCanOnlyBeCalledBy_PartTwo() + 
					allowedCallerNames);
		}
	}
}
