package org.adligo.tests4j.models.shared.common;

import java.util.Collections;

/**
 * for GWT bridge Windows, Unix
 * @author scott
 *
 */
public class LineSeperator {
	private static String lineSeperator;
	
	public static void setLineSeperator(String p) {
		new MethodBlocker(LineSeperator.class, "setLineSeperator", 
				Collections.singletonList("org.adligo.tests4j.run.Tests4J"));
		lineSeperator = p;
	}
	
	public static String getLineSeperator() {
		return lineSeperator;
	}
}
