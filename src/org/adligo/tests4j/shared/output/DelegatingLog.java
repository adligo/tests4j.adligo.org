package org.adligo.tests4j.shared.output;

import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
public class DelegatingLog implements I_Tests4J_Log {
	public static final String DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM = "DefaultReporter requires a non null I_System.";
	private Map<String, Boolean>  logs_ = new HashMap<String,Boolean>();
	private I_Tests4J_Constants constants_;
	private boolean mainLog_;
	private I_OutputBuffer out_;
	private I_System system_;
	private DefaultLog log_ = new DefaultLog();
	/**
	 * create a DefaultReporter, this should be able to handle
	 * any sort of error/exception from the I_Tests4J_Params 
	 * as it could be a custom implementation not in this project.
	 * 
	 * @param pSystem
	 * @param params
	 */
	public DelegatingLog(I_System pSystem, I_Tests4J_Constants constants,  Map<Class<?>, Boolean> logsOn, I_OutputBuffer pOut) {
		if (pSystem == null) {
			throw new IllegalArgumentException(DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM);
		}
		system_ = pSystem;
		if (constants == null) {
		  throw new NullPointerException();
		}
		constants_ = constants;
		log_ = new DefaultLog(pSystem, constants, logsOn);
		mainLog_ = pSystem.isMainSystem();
		out_ = pOut;
		if (logsOn != null) {
			Set<Entry<Class<?>,Boolean>> entries = logsOn.entrySet();
			logs_ = new HashMap<String, Boolean>();
			for (Entry<Class<?>,Boolean> e: entries) {
				if (e != null) {
					Class<?> c = e.getKey();
					Boolean value = e.getValue();
					if (c != null && value != null) {
						logs_.put(c.getName(), value);
					}
				}
			}
		}
		logs_ = Collections.unmodifiableMap(logs_);
	}
	
	@Override
	public void log(String p) {
		out_.add(p);
	}

	@Override
  public void logLine(String ... p) {
    String message = StringMethods.orderLine(constants_.isLeftToRight(),  p);
    out_.add(message);
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
			sb.append(system_.lineSeperator());
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
		Boolean result = logs_.get(clazzName);
		if (result == null) {
			return false;
		}
		return result;
	}

	@Override
	public boolean isMainLog() {
		return mainLog_;
	}

	@Override
	public String lineSeparator() {
		return system_.lineSeperator();
	}

	@Override
	public String getCurrentThreadName() {
		I_Tests4J_ReportMessages reportMessages = constants_.getReportMessages();
		
		return reportMessages.getOnThreadZ().replaceAll("<Z/>", 
				system_.getCurrentThreadName());
	}

  @Override
  public String getThreadWithGroupNameMessage() {
    return log_.getThreadWithGroupNameMessage();
  }

  @Override
  public String getThreadMessage() {
    return log_.getThreadMessage();
  }

  @Override
  public void appendLine(StringBuilder sb, String line, String indent) {
    log_.appendLine(sb, line, indent);
  }

  @Override
  public void appendLines(StringBuilder sb, List<String> lines, String indent) {
    log_.appendLines(sb, lines, indent);
  }



}
