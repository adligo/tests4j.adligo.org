package org.adligo.tests4j.models.shared.system;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.adligo.tests4j.shared.report.summary.RemoteProgressDisplay;
import org.adligo.tests4j.shared.report.summary.SetupProgressDisplay;
import org.adligo.tests4j.shared.report.summary.SummaryReporter;
import org.adligo.tests4j.shared.report.summary.TestsFailedDisplay;
import org.adligo.tests4j.shared.report.summary.TestsProgressDisplay;
import org.adligo.tests4j.shared.report.summary.TestsDisplay;
import org.adligo.tests4j.shared.report.summary.TrialsFailedDisplay;
import org.adligo.tests4j.shared.report.summary.TrialsProgressDisplay;
import org.adligo.tests4j.shared.report.summary.TrialsDisplay;

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
public class DefaultLog implements I_Tests4J_Log {
	public static final String DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM = "DefaultReporter requires a non null I_System.";
	private Map<String, Boolean>  logs = new HashMap<String,Boolean>();
	private I_Tests4J_System system;
	
	public DefaultLog() {
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
	public DefaultLog(I_Tests4J_System pSystem, Map<Class<?>, Boolean> logsOn) {
		if (pSystem == null) {
			throw new IllegalArgumentException(DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM);
		}
		system = pSystem;
		
		
		if (logsOn != null) {
			Set<Entry<Class<?>,Boolean>> entries = logsOn.entrySet();
			logs = new HashMap<String, Boolean>();
			for (Entry<Class<?>,Boolean> e: entries) {
				if (e != null) {
					Class<?> c = e.getKey();
					Boolean value = e.getValue();
					if (c != null && value != null) {
						logs.put(c.getName(), value);
					}
				}
			}
		}
		logs = Collections.unmodifiableMap(logs);
	}
	
	@Override
	public synchronized void log(String p) {
		system.println(p);
	}

	@Override
	public synchronized void onException(Throwable p) {
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
	public boolean isMainLog() {
		String systemClassName = system.getClass().getName();
		if (DefaultSystem.class.getName().equals(systemClassName)) {
			return true;
		}
		return false;
	}



}
