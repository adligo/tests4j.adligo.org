package org.adligo.tests4j.models.shared.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * for GWT bridge Windows, Unix
 * mostly for stubbing tests/trials 
 * @author scott
 *
 */
public class LineSeperator {
	private static String LINE_SEPERATOR = System.lineSeparator();
	private static List<String> CALLING_CLASSES = getCallingClasses();
	
	private static List<String> getCallingClasses() {
		List<String> toRet = new ArrayList<String>();
		toRet.add("org.adligo.tests4j.run.Tests4J");
		toRet.add("org.adligo.tests4j_tests.models.shared.common.LineSeperatorTrial");
		return Collections.unmodifiableList(toRet);
	}
			
	public static void setLineSeperator(String p) {
		new MethodBlocker(LineSeperator.class, "setLineSeperator", CALLING_CLASSES);
		LINE_SEPERATOR = p;
	}
	
	public static String getLineSeperator() {
		return LINE_SEPERATOR;
	}
	
}
