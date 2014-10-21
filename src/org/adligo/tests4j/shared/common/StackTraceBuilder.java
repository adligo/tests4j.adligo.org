package org.adligo.tests4j.shared.common;


/**
 * a few threadsafe methods for building 
 * a stack trace string, which can be clicked on 
 * from a eclipse console.
 * 
 * @author scott
 *
 */
public class StackTraceBuilder {
	
	private static final String TAB = "\t";

	public static String toString(Throwable t, boolean deep) {
		StringBuilder sb = new StringBuilder();
		toString(t, sb, TAB);
		StringBuilder currentIndent = new StringBuilder(TAB);
		if (deep) {
			Throwable cause = t.getCause();
			while (cause != null) {
				toString(cause, sb, TAB + currentIndent.toString());
				currentIndent.append(TAB);
				cause = cause.getCause();
			}
		}
		return sb.toString();
	}
	
	private static void toString(Throwable t, StringBuilder sb, String currentIndent) {
		
		sb.append(currentIndent + t.toString()  + Tests4J_System.SYSTEM.lineSeperator());
		StackTraceElement [] stack = t.getStackTrace();
		if (stack != null) {
			//stack trace will, be null in GWT client
			for (int i = 0; i < stack.length; i++) {
			  StackTraceElement ste = stack[i];
				sb.append(currentIndent +"at " + ste.getClassName() + "." + 
				      ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")" + Tests4J_System.SYSTEM.lineSeperator());
			}
		}
	}
}
