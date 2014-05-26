package org.adligo.tests4j.models.shared.common;

import java.util.Collections;

/**
 * for GWT bridge Windows, Unix
 * @author scott
 *
 */
public class LineSeperator {
	private static String lineSeperator;
	private static String callingClass = "org.adligo.tests4j.run.Tests4J";
			
	public static void setLineSeperator(String p) {
		new MethodBlocker(LineSeperator.class, "setLineSeperator", 
				Collections.singletonList(callingClass));
		lineSeperator = p;
	}
	
	public static String getLineSeperator() {
		return lineSeperator;
	}
	
	protected static void setAllowedCallingClass(String p) {
		callingClass = p;
	}
}
