package org.adligo.tests4j.shared.en;

import org.adligo.tests4j.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LogMessages;

public class Tests4J_LogMessages implements I_Tests4J_LogMessages {
	private static final String THREAD = "Thread; ";
	private static final String THREAD_THREAD_GROUP = "Thread/Group; ";

	
	Tests4J_LogMessages () {} 
	
  @Override
  public String getThread() {
    return THREAD;
  }

  @Override
  public String getThreadSlashThreadGroup() {
    return THREAD_THREAD_GROUP;
  }
}
