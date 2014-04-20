package org.adligo.tests4j.models.shared.asserts;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.AbstractTrial;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;

public class AssertionFailureLocation extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Set<String> NON_LOCATION_STACK_CLASSES = getNonLocationStackClasses();
	
	public AssertionFailureLocation(I_AssertionHelperInfo info) {
		Exception stack = new Exception();
		stack.fillInStackTrace();
		StackTraceElement [] elements = stack.getStackTrace();
		List<StackTraceElement> forStack = new ArrayList<StackTraceElement>();
		for(StackTraceElement e: elements) {
			if (!NON_LOCATION_STACK_CLASSES.contains(e.getClassName())) {
				forStack.add(e);
			}
		}
		StackTraceElement topElement = forStack.get(0);
		if (topElement != null) {
			changeTopStackElementLineNumber(info, forStack, topElement);
		}
		StackTraceElement [] stackTrace = forStack.toArray(new StackTraceElement[forStack.size()]);
		super.setStackTrace(stackTrace);
	}

	private void changeTopStackElementLineNumber(I_AssertionHelperInfo info,
			List<StackTraceElement> forStack, StackTraceElement topElement) {
		//manipulate the top stack element to match up to the source code
		//if there is a instrumented byte code test class
		I_CoveragePlugin plugin = info.getCoveragePlugin();
		if (plugin != null) {
			Integer newLine = plugin.getSourceFileLine(topElement);
			//defensive coding, who knows what the plugin will deliver
			if (newLine != null) {
				StackTraceElement ste = new StackTraceElement(
						topElement.getClassName(), 
						topElement.getMethodName(), 
						topElement.getFileName(),
						newLine);
				forStack.set(0, ste);
			}
		}
	}
	
	private static Set<String> getNonLocationStackClasses() {
		Set<String> toRet = new HashSet<String>();
		
		toRet.add(AssertionFailureLocation.class.getName());
		toRet.add(AbstractTrial.class.getName());
		toRet.add(AssertionProcessor.class.getName());
		
		return Collections.unmodifiableSet(toRet);
	}
	

}
