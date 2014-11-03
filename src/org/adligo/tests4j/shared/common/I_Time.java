package org.adligo.tests4j.shared.common;

/**
 * this was abstracted out of I_System for ease 
 * of test stubbing.
 * 
 * @author scott
 *
 */
public interface I_Time {
  /**
   * just a wrapper for System.currentTimeMillis
   * @return
   */
  public long getTime();
}
