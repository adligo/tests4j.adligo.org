package org.adligo.tests4j.shared.output;

import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LogMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	private I_Tests4J_LogMessages logMessages_ = Tests4J_Constants.CONSTANTS.getLogMessages();
	protected I_System system;
	
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
	public DefaultLog(I_System pSystem, Map<Class<?>, Boolean> logsOn) {
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
	public synchronized void onThrowable(Throwable p) {
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
		return system.isMainSystem();
	}

	@Override
	public String getLineSeperator() {
		return system.lineSeperator();
	}

	@Override
	public String getCurrentThreadName() {
		I_Tests4J_ReportMessages reportMessages = 
				Tests4J_Constants.CONSTANTS.getReportMessages();
		
		return reportMessages.getOnThreadZ().replaceAll("<Z/>", 
				system.getCurrentThreadName());
	}

  @Override
  public String getThreadWithGroupNameMessage() {
    return logMessages_.getThreadSlashThreadGroup() + system.getCurrentThreadName() + 
        "~~~" + system.getCurrentThreadGroupName();
  }

  @Override
  public String getThreadMessage() {
    return logMessages_.getThread() + system.getCurrentThreadName();
  }
}
