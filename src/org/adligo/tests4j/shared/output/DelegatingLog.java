package org.adligo.tests4j.shared.output;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.common.DefaultSystem;
import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.common.Tests4J_System;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;

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
public class DelegatingLog implements I_Tests4J_Log {
	public static final String DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM = "DefaultReporter requires a non null I_System.";
	private Map<String, Boolean>  logs = new HashMap<String,Boolean>();
	private String lineSeperator;
	private boolean mainLog;
	private I_OutputBuffer out;
	/**
	 * create a DefaultReporter, this should be able to handle
	 * any sort of error/exception from the I_Tests4J_Params 
	 * as it could be a custom implementation not in this project.
	 * 
	 * @param pSystem
	 * @param params
	 */
	public DelegatingLog(I_System pSystem, Map<Class<?>, Boolean> logsOn, I_OutputBuffer pOut) {
		if (pSystem == null) {
			throw new IllegalArgumentException(DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM);
		}
		lineSeperator = pSystem.lineSeperator();
		mainLog = pSystem.isMainSystem();
		out = pOut;
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
	public void log(String p) {
		out.add(p);
	}

	@Override
	public void onThrowable(Throwable p) {
		logThrowable("\t", p);
	}

	private void logThrowable(String indentString, Throwable t) {
		logThrowable(indentString, indentString, t, new StringBuilder());
	}
	
	private void logThrowable(String currentIndent, String indentString, Throwable t, StringBuilder sb) {
		StackTraceElement [] stack = t.getStackTrace();
		log(currentIndent + t.toString());
		for (int i = 0; i < stack.length; i++) {
			sb.append(currentIndent +"at " + stack[i]);
			sb.append(lineSeperator);
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			logThrowable(currentIndent + indentString, indentString,  cause, sb);
		}
		log(sb.toString());
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
		return mainLog;
	}

	@Override
	public String getLineSeperator() {
		return lineSeperator;
	}

	@Override
	public String getCurrentThreadName() {
		I_Tests4J_ReportMessages reportMessages = 
				Tests4J_Constants.CONSTANTS.getReportMessages();
		
		return reportMessages.getOnThreadZ().replaceAll("<Z/>", 
				Tests4J_System.getCurrentThreadName());
	}



}
