package org.adligo.tests4j.shared.asserts;

import org.adligo.tests4j.shared.asserts.common.I_Asserts;
import org.adligo.tests4j.shared.common.ClassMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
	
	public AssertionFailureLocation(I_Asserts trialInstance) {
		Exception stack = new Exception();
		stack.fillInStackTrace();
		StackTraceElement [] elements = stack.getStackTrace();
		List<StackTraceElement> forStack = new ArrayList<StackTraceElement>();
		
		String trialInstanceName = "";
		if (trialInstance != null) {
		  trialInstanceName = trialInstance.getClass().getName();
		}
		boolean hadTrialClassInStack = false;
		for(StackTraceElement e: elements) {
		  String cn = e.getClassName();
			if (!NON_LOCATION_STACK_CLASSES.contains(cn)) {
			  if (trialInstanceName.equals(cn))  {
			    hadTrialClassInStack = true;
			  }
				forStack.add(e);
			}
		}
		if (!hadTrialClassInStack && trialInstanceName.length() > 1) {
		  forStack.add(0, new StackTraceElement(trialInstanceName, "init", 
		      trialInstance.getClass().getSimpleName() + ".java", 1));
		}
		StackTraceElement [] stackTrace = forStack.toArray(new StackTraceElement[forStack.size()]);
		super.setStackTrace(stackTrace);
	}

	
	private static Set<String> getNonLocationStackClasses() {
		Set<String> toRet = new HashSet<String>();
		
		toRet.add(AssertionFailureLocation.class.getName());
		toRet.add("org.adligo.tests4j.system.shared.trials.AbstractTrial");
		toRet.add("org.adligo.tests4j.shared.asserts.AssertionProcessor");
		
		return Collections.unmodifiableSet(toRet);
	}
	

}
