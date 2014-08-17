package org.adligo.tests4j.run;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.common.MethodBlocker;
import org.adligo.tests4j.models.shared.system.DefaultLog;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

/**
 * generally catches uncaught exceptions and
 * displays the exception on regular System.out,
 * to keep things sequential in Test4J 
 * we only use System.out (never System.err).
 * 
 * @author scott
 *
 */
public class Tests4J_UncaughtExceptionHandler implements UncaughtExceptionHandler {
	/**
	 * note this is not volatile as it will probably only change 
	 * in the trial
	 */
	private static I_Tests4J_Log log = new DefaultLog();
	private static final MethodBlocker METHOD_BLOCKER = getSetLoggerMethodBlocker();
	
	private static MethodBlocker getSetLoggerMethodBlocker() {
		List<String> allowedCallers = new ArrayList<String>();
		allowedCallers.add("org.adligo.tests4j_tests.run.mocks.MockTests4J_UncaughtExceptionHandler");
		
		return new MethodBlocker(Tests4J_UncaughtExceptionHandler.class, "setLogger", allowedCallers);
	}
	public Tests4J_UncaughtExceptionHandler() {}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		String threadName = null;
		if (t != null) {
			threadName = t.getName();
		}
		log.log("uncaughtException on thread " + threadName);
		log.onThrowable(e);
		Throwable cause = e.getCause();
		while (cause != null) {
			log.onThrowable(cause);
			cause = cause.getCause();
		}
	}

	protected static I_Tests4J_Log getLog() {
		return log;
	}

	/**
	 * a way to set this from a test only
	 * @param logger
	 */
	protected static synchronized void setLog(I_Tests4J_Log logger) {
		METHOD_BLOCKER.checkAllowed();
		Tests4J_UncaughtExceptionHandler.log = logger;
	}
}
