package org.adligo.tests4j.models.shared.system;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the main logging class for Tests4J,
 * all log messages go through here including 
 * progress reports exc.
 * 
 * Also note all output from instances of this class
 * are synchronized on the instance, so that
 * stack traces always show up with well formed ordering.
 * 
 * @author scott
 *
 */
public class DefaultLogger implements I_Tests4J_Logger {
	public static final String DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM = "DefaultReporter requires a non null I_System.";
	private Map<String, Boolean>  logs = Collections.emptyMap();
	private I_System system;
	
	public DefaultLogger() {
		this(new DefaultSystem(), null);
	}
	
	/**
	 * create a DefaultReporter, this should be able to handle
	 * any sort of error/exception from the I_Tests4J_Params 
	 * as it could be a custom implementation not in this project.
	 * 
	 * @param pSystem
	 * @param params
	 */
	public DefaultLogger(I_System pSystem, I_Tests4J_Params params) {
		if (pSystem == null) {
			throw new IllegalArgumentException(DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM);
		}
		system = pSystem;
		
		if (params == null) {
			return;
		}
		try {
			List<Class<?>> logsOn = params.getLoggingClasses();
			if (logsOn == null) {
				logs = Collections.emptyMap();
			} else {
				logs = new HashMap<String, Boolean>();
				for (Class<?> c: logsOn) {
					if (c != null) {
						logs.put(c.getName(), true);
					}
				}
				logs = Collections.unmodifiableMap(logs);
			}
		} catch (Throwable t) {
			onError(t);
		}
	}
	
	@Override
	public synchronized void log(String p) {
		system.println(p);
	}

	@Override
	public synchronized void onError(Throwable p) {
		logThrowable("\t", p);
	}

	private void logThrowable(String indentString, Throwable t) {
		logThrowable(indentString, indentString, t);
	}
	
	private void logThrowable(String currentIndent, String indentString, Throwable t) {
		StackTraceElement [] stack = t.getStackTrace();
		log(currentIndent + t.toString());
		for (int i = 0; i < stack.length; i++) {
			log(currentIndent +"at " + stack[i]);
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			logThrowable(currentIndent + indentString, indentString,  cause);
		}
	}
	
	@Override
	public boolean isLogEnabled(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		String clazzName = clazz.getName();
		Boolean result = logs.get(clazzName);
		if (result == null) {
			return false;
		}
		return result;
	}

	@Override
	public boolean isMainReporter() {
		String systemClassName = system.getClass().getName();
		if (DefaultSystem.class.getName().equals(systemClassName)) {
			return true;
		}
		return false;
	}


}
