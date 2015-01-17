package org.adligo.tests4j.shared.output;

import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.StackTraceBuilder;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.en.Tests4J_EnglishConstants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LogMessages;
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
public class DefaultLog implements I_Tests4J_Log {
	
  public static final String DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM = "DefaultReporter requires a non null I_System.";
 
  private Map<String, Boolean>  logs = new HashMap<String,Boolean>();
	private final I_Tests4J_Constants constants_;
	protected I_System system;
	
	public DefaultLog() {
		this(new DefaultSystem(), Tests4J_EnglishConstants.ENGLISH, null);
	}
	
	/**
	 * create a DefaultReporter, this should be able to handle
	 * any sort of error/exception from the I_Tests4J_Params 
	 * as it could be a custom implementation not in this project.
	 * 
	 * @param pSystem
	 * @param params
	 */
	public DefaultLog(I_System pSystem, I_Tests4J_Constants constants, Map<Class<?>, Boolean> logsOn) {
	  
		if (pSystem == null) {
			throw new IllegalArgumentException(DEFAULT_REPORTER_REQUIRES_A_NON_NULL_I_SYSTEM);
		}
		system = pSystem;
		if (constants == null) {
		  throw new NullPointerException();
		}
		constants_ = constants;
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
  public void logLine(String ... p) {
    String message = StringMethods.orderLine(constants_.isLeftToRight(), p);
    log(message + system.lineSeperator());
  }
	
	@Override
	public synchronized void onThrowable(Throwable p) {
		String message = new StackTraceBuilder().toString(p);
		log(message);
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
	public String lineSeparator() {
		return system.lineSeperator();
	}

	@Override
	public String getCurrentThreadName() {
		I_Tests4J_ReportMessages reportMessages = constants_.getReportMessages();
		
		return reportMessages.getOnThreadZ().replaceAll("<Z/>", 
				system.getCurrentThreadName());
	}

  @Override
  public String getThreadWithGroupNameMessage() {
    I_Tests4J_LogMessages logMessages = constants_.getLogMessages();
    return StringMethods.orderLine(constants_.isLeftToRight(), logMessages.getThreadSlashThreadGroup(), system.getCurrentThreadName(), 
          "~~~", system.getCurrentThreadGroupName());
  }

  @Override
  public String getThreadMessage() {
    I_Tests4J_LogMessages logMessages = constants_.getLogMessages();
    return StringMethods.orderLine(constants_.isLeftToRight(), logMessages.getThread(), system.getCurrentThreadName());
    
  }

  @Override
  public void appendLine(StringBuilder sb, String line, String indent) {
    String message = StringMethods.orderLine(constants_.isLeftToRight(), indent, line);
    sb.append(message);
  }

  @Override
  public void appendLines(StringBuilder sb, List<String> lines, String indent) {
    for (String line: lines) {
      appendLine(sb, line, indent);
    }
  }
}
