package org.adligo.tests4j.shared.en;

import org.adligo.tests4j.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LogMessages;

public class Tests4J_LogMessages implements I_Tests4J_LogMessages {
	private static final String THREAD = "Thread; ";
	private static final String THREAD_THREAD_GROUP = "Thread/Group; ";
	private static final String IS_GETTING_THE_FOLLOWING_THREAD_GROUP_LOCAL_PROBES = 
	      "is getting the following probes;";
  private static final String FOR_THE_FOLLOWING_CLASS = "for the following class;";
	private static final String IS_CREATING_NEW_PROBES = "is creating new probes for the following class;";
	private static final String DETECTED_THE_FOLLOWING_PROBE_HIT =  "detected the following probe hit;";
	
	Tests4J_LogMessages () {} 
	
  @Override
  public String getThread() {
    return THREAD;
  }

  @Override
  public String getThreadSlashThreadGroup() {
    return THREAD_THREAD_GROUP;
  }
  
  @Override
  public String getIsGettingTheFollowingThreadGroupLocalProbes() {
    return IS_GETTING_THE_FOLLOWING_THREAD_GROUP_LOCAL_PROBES;
  }

  @Override
  public String getForTheFollowingClass() {
    return FOR_THE_FOLLOWING_CLASS;
  }
  
  @Override
  public String getIsCreatingNewProbesForTheFollowingClass() {
    return IS_CREATING_NEW_PROBES;
  }
  
  @Override
  public String getDetectedTheFollowingProbeHit() {
    return DETECTED_THE_FOLLOWING_PROBE_HIT;
  }
}
