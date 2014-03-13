package org.adligo.jtests.base.shared.asserts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.jtests.base.shared.AbstractTest;

public class AssertionFailureLocation extends Exception {
	private static final Set<String> NON_LOCATION_STACK_CLASSES = getNonLocationStackClasses();
	private StackTraceElement[] stackTrace;
	
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
		stackTrace = forStack.toArray(new StackTraceElement[forStack.size()]);
	}
	
	private static Set<String> getNonLocationStackClasses() {
		Set<String> toRet = new HashSet<String>();
		
		toRet.add(AssertionFailureLocation.class.getName());
		toRet.add(AbstractTest.class.getName());
		return Collections.unmodifiableSet(toRet);
	}
	
	@Override
	public StackTraceElement[] getStackTrace() {
		return Arrays.copyOf(stackTrace, stackTrace.length, 
				StackTraceElement[].class);
	}
}
