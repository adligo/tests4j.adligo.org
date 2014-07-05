package org.adligo.tests4j.models.shared.asserts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.trials.AbstractTrial;

/**
 * this class represents the location (stack trace/line number) 
 * of a failed assertion.
 * 
 * @author scott
 *
 */
public class AssertionFailureLocation extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Set<String> NON_LOCATION_STACK_CLASSES = getNonLocationStackClasses();
	
	public AssertionFailureLocation() {
		Exception stack = new Exception();
		stack.fillInStackTrace();
		StackTraceElement [] elements = stack.getStackTrace();
		List<StackTraceElement> forStack = new ArrayList<StackTraceElement>();
		for(StackTraceElement e: elements) {
			if (!NON_LOCATION_STACK_CLASSES.contains(e.getClassName())) {
				forStack.add(e);
			}
		}
		
		StackTraceElement [] stackTrace = forStack.toArray(new StackTraceElement[forStack.size()]);
		super.setStackTrace(stackTrace);
	}

	
	private static Set<String> getNonLocationStackClasses() {
		Set<String> toRet = new HashSet<String>();
		
		toRet.add(AssertionFailureLocation.class.getName());
		toRet.add(AbstractTrial.class.getName());
		toRet.add(AssertionProcessor.class.getName());
		
		return Collections.unmodifiableSet(toRet);
	}
	

}
