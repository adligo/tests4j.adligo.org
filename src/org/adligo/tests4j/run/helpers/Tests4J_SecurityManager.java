package org.adligo.tests4j.run.helpers;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * This class helps block thread creation at inappropriate
 * locations so that the Tests4J will be aware of Threads created
 * in Trials created by the users of the API.   This is done
 * so that Tests4J can 
 *    
 * @author scott
 * @author Heinz Kabutz (Note Dr. Kabutz hasn't actually typed in 
 *    this file yet, but was paramount in helping Scott figure
 *    out how to block Thread creation!
 *    check out http://www.javaspecialists.eu)
 */
public class Tests4J_SecurityManager extends SecurityManager {
	private I_Tests4J_Reporter reporter;
	
	public Tests4J_SecurityManager(I_Tests4J_Reporter pReporter) {
		reporter = pReporter;
	}
	
	@Override
	public void checkPermission(Permission perm) {
	    // allow everything
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
	   // allow everything
	}

	/**
	 * block thread creation for certian threads in the following methods
	 * @Test
	 * yourTestName()
	 * 
	 * beforeTests()
	 * 
	 * afterTests()
	 * 
	 * if there is no trial recursion (a trial testing another trials failure)
	 *   in that case allow the thread creation if it is a trialThread.
	 *   
	 */
	@Override
	public void checkAccess(ThreadGroup g) {
		
		List<String> names = new ArrayList<String>();
		
		getThreadGroupNameTree(g, names);
		
		if (names.size() >= 3 ) {
			if (reporter.isLogEnabled(Tests4J_SecurityManager.class)) {
				reporter.log("Checking thread creation for thread group tree " + names + " .");
			}
			List<String> revNames = new ArrayList<String>();
			for (int i = names.size() - 1; i >= 0; i--) {
				String name = names.get(i);
				if (hasTrialOrTest(name, revNames)) {
					revNames.add(name);
				}
			}
			if (reporter.isLogEnabled(Tests4J_SecurityManager.class)) {
				reporter.log("Reversed Names are " + revNames + " . ");
			}
			if (revNames.size() >= 2) {
				String name = names.get(0);
				if (name.startsWith(Tests4J_ThreadFactory.TEST_THREAD_NAME)) {
					Exception e = new Exception();
					StackTraceElement [] stack = e.getStackTrace();
					if (!stackContainsTests4J_ThreadGroup_createNewThread(stack)) {
						if (reporter.isLogEnabled(Tests4J_SecurityManager.class)) {
							reporter.log("Throwing thread creation exception" + names + " .");
						}
						throw new SecurityException("During tests (beforeTest(), @Tests and afterTests()), "
					      		+ "we do not allow thread creation.");
					}
				}
			}
		}
	}

	private boolean stackContainsTests4J_ThreadGroup_createNewThread(StackTraceElement [] elements) {
		for (int i = 0; i < elements.length; i++) {
			StackTraceElement e = elements[i];
			if (Tests4J_ThreadFactory.class.getName().equals(e.getClassName())) {
				if ("createNewThread".equals(e.getMethodName()) ||
						"createNewThreadGroup".equals(e.getMethodName())) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * note this ommits thread group names we aren't interested in
	 * @param p
	 * @param names
	 */
	private void getThreadGroupNameTree(ThreadGroup p, List<String> names) {
		String name = p.getName();
		if (hasTrialOrTest(name, names)) { 
			names.add(p.getName());
		}
		ThreadGroup parent = p.getParent();
		if (parent != null) {
			getThreadGroupNameTree(parent, names);
		}
	}

	private boolean hasTrialOrTest(String name, List<String> names) {
		if (name.startsWith(Tests4J_ThreadFactory.TRIAL_THREAD_NAME) 
				|| name.startsWith(Tests4J_ThreadFactory.TEST_THREAD_NAME)
				|| names.contains(Tests4J_ThreadFactory.TRIAL_THREAD_NAME) 
				|| names.contains(Tests4J_ThreadFactory.TEST_THREAD_NAME)) { 
			return true;
		}
		return false;
	}
}
