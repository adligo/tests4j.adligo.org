package org.adligo.tests4j.shared.output;

import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
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
  private static final I_Tests4J_Constants CONSTANTS = Tests4J_Constants.CONSTANTS;
  
  /**
   * This method orders lines for left to right (English) <br/>
   * and right to left (Arabic) languages, when a <br/>
   * line header or tab is required (i.e); <br/>
   *  <br/>
   * Left to right (spaces simulate tabs) <br/>
   * Tests4j: setup <br/>
   *    org.adligo.tests4j.Foo 12.0% <br/>
   *    org.adligo.tests4j.Boo 13.0% <br/>
   *     <br/>
   * Right to left (spaces simulate tabs at right only)<br/>
   *                   setup :Tests4j<br/>
   *  12.0% org.adligo.tests4j.Foo   <br/>
   *  13.0% org.adligo.tests4j.Boo   <br/>
   * Also note I don't speak any or know much about any
   * right to left languages so this will take some time to get right.
   *  I assumed that java class names would stay left to right,
   *  and that the percent sign would still be to the right of the 
   *  number (after all it is the Arabic numeral system).
   *  The name of the Tests4J product would not change, as 
   *    translating it into other languages would be confusing.
   *    
   * @param p
   * @return
   */
  public static String orderLine(String ... p) {
    if (p.length == 1) {
      return p[0];
    }
    StringBuilder sb = new StringBuilder();
    if (CONSTANTS.isLeftToRight()) {
      for (int i = 0; i < p.length; i++) {
        sb.append(p[i]);
      }
    } else {
      for (int i = p.length - 1; i >= 0; i--) {
        sb.append(p[i]);
      }
    }
    return sb.toString();
  }
  
  private Map<String, Boolean>  logs = new HashMap<String,Boolean>();
	
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
  public void logLine(String ... p) {
    String message = orderLine(p);
    log(message + system.lineSeperator());
  }
	
	@Override
	public synchronized void onThrowable(Throwable p) {
		String message = logThrowable("\t", p);
		log(message);
	}

	private String logThrowable(String indentString, Throwable t) {
		return logThrowable(indentString, indentString, t, new StringBuilder());
	}
	
	private String logThrowable(String currentIndent, String indentString, Throwable t, StringBuilder sb) {
		StackTraceElement [] stack = t.getStackTrace();
		log(currentIndent + t.toString());
		for (int i = 0; i < stack.length; i++) {
			sb.append(currentIndent +"at " + stack[i]);
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			logThrowable(currentIndent + indentString, indentString,  cause,sb);
		}
		return sb.toString();
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
    I_Tests4J_LogMessages logMessages = CONSTANTS.getLogMessages();
    if (CONSTANTS.isLeftToRight()) {
      return logMessages.getThreadSlashThreadGroup() + system.getCurrentThreadName() + 
          "~~~" + system.getCurrentThreadGroupName();
    } else {
      return  system.getCurrentThreadGroupName() + "~~~" + system.getCurrentThreadName() + 
          logMessages.getThreadSlashThreadGroup();
    }
    
  }

  @Override
  public String getThreadMessage() {
    I_Tests4J_LogMessages logMessages = CONSTANTS.getLogMessages();
    if (CONSTANTS.isLeftToRight()) {
      return logMessages.getThread() + system.getCurrentThreadName();
    } else {
      return system.getCurrentThreadName() + logMessages.getThread();
    }
  }

  @Override
  public void appendLine(StringBuilder sb, String line, String indent) {
    if (CONSTANTS.isLeftToRight()) {
      sb.append(indent);
      sb.append(line);
    } else {
      sb.append(line);
      sb.append(indent);
    }
  }

  @Override
  public void appendLines(StringBuilder sb, List<String> lines, String indent) {
    for (String line: lines) {
      appendLine(sb, line, indent);
    }
  }
}
