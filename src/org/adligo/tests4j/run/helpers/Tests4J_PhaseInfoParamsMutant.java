package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.shared.common.I_Time;

public class Tests4J_PhaseInfoParamsMutant {
  private String processName_;
  private int threadCount_; 
  /**
   * This is the total number of things
   * that must be done for this to to
   * be considered finished.
   * 
   */
  private int count_;
  private long notificationInterval_;
  private I_Time time_;
  
  public String getProcessName() {
    return processName_;
  }
  public void setProcessName(String processName) {
    processName_ = processName;
  }
  public int getThreadCount() {
    return threadCount_;
  }
  public void setThreadCount(int threadCount) {
    threadCount_ = threadCount;
  }
  public int getCount() {
    return count_;
  }
  public void setCount(int count) {
    count_ = count;
  }
  public long getNotificationInterval() {
    return notificationInterval_;
  }
  public void setNotificationInterval(long notificationInterval) {
    notificationInterval_ = notificationInterval;
  }
  public I_Time getTime() {
    return time_;
  }
  public void setTime(I_Time time) {
    time_ = time;
  }
  
}
