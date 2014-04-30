package org.adligo.tests4j.run;

import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

public class Tests4J_UncaughtExceptionHandler implements UncaughtExceptionHandler {
	private static final PrintStream OUT = System.out;
	public static final Tests4J_UncaughtExceptionHandler HANDLER = new Tests4J_UncaughtExceptionHandler();
	
	Tests4J_UncaughtExceptionHandler() {}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace(OUT);
		Throwable cause = e.getCause();
		while (cause != null) {
			cause.printStackTrace(OUT);
			cause = cause.getCause();
		}
	}
}
