package org.adligo.tests4j.run.common;

/**
 * This is to provide some stubbing
 * for the test of Tests4J_ThreadFactory
 * which does a bunch of fancy thread creation
 * in a tree like structure.
 * @author scott
 *
 */
public interface I_Threads {
  /**
   * stub around Thread.currentThread()
   * @return
   */
  public Thread currentThread();
}
