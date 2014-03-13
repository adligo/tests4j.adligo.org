package org.adligo.jtests.run;

import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

public class JTestUncaughtExceptionHandler implements UncaughtExceptionHandler {
	public static final PrintStream OUT = System.out;
	public static final JTestUncaughtExceptionHandler HANDLER = new JTestUncaughtExceptionHandler();
	
	private JTestUncaughtExceptionHandler() {}
	
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
