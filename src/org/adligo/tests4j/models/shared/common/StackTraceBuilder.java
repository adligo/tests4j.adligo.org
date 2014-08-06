package org.adligo.tests4j.models.shared.common;

import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;

/**
 * a few threadsafe methods for building 
 * a stack trace string, which can be clicked on 
 * from a eclipse console.
 * 
 * @author scott
 *
 */
public class StackTraceBuilder {
	
	public static String toString(Throwable t, boolean deep) {
		StringBuilder sb = new StringBuilder();
		toString(t, sb, "\t");
		StringBuilder currentIndent = new StringBuilder("\t");
		if (deep) {
			Throwable cause = t.getCause();
			while (cause != null) {
				toString(cause, sb, "\t" + currentIndent.toString());
				currentIndent.append("\t");
				cause = cause.getCause();
			}
		}
		return sb.toString();
	}
	
	private static void toString(Throwable t, StringBuilder sb, String currentIndent) {
		
		sb.append(currentIndent + t.toString()  + LineSeperator.getLineSeperator());
		StackTraceElement [] stack = t.getStackTrace();
		if (stack != null) {
			//stack trace will, be null in GWT client
			for (int i = 0; i < stack.length; i++) {
				sb.append(currentIndent +"at " + stack[i] + LineSeperator.getLineSeperator());
			}
		}
	}
}
