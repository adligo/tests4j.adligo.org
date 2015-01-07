package org.adligo.tests4j.run.common;

public interface I_ThreadGroupFilter {

  /**
   * Get the name of the thread group that matches the filter
   * by traversing the thread groups starting with the current
   * thread group and moving up through it's parent thread groups.
   * @return null if no thread groups matched the filter
   *  or the name of the thread group that did match.
   *  
   */
  public abstract String getThreadGroupNameMatchingFilter();

  public abstract String getFilter();

  public abstract I_Threads getThreads();

}