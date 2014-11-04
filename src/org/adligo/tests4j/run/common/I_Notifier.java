package org.adligo.tests4j.run.common;

/**
 * This interface used to help 
 * trials that test code which does low level wait 
 * and notify calls on Object instances.
 * This can be somewhat tricky as a call to wait
 * or notify could derail tests4j's execution
 * by causing a trial or test thread to wait forever
 * without ever getting notification, which would
 * cause tests4j to hang.
 *   Also note this class will probably be duplicated
 * in a the i_util.adligo.org project for production
 * code that shouldn't be directly aware of tests4j apis.
 * 
 * @author scott
 *
 */
public interface I_Notifier {
  /**
   * since the wait methods must be in a 
   * synchronized block pertaining to the instance
   * where wait is called,
   * synchronized on the instance returned from this method.
   * @return
   */
  public Object getInstance();
  
  /**
   * since calling 
   * Thread.currentThread.interrrupt()
   * would cause many problems during a trial run
   * this allows calling it in a way that can
   * be stubbed out for testing.
   */
  public void interrupt();
  /**
   * In production code this method should call
   * @see Object#notify()
   * In test code this method will delegate
   * to some sort of stub, for assertion that notify was called.
   */
  public void notifyDelegate() throws IllegalMonitorStateException;
  /**
   * In production code this method should call
   * @see Object#notifyAll()
   * In test code this method will delegate
   * to some sort of stub, for assertion that notifyAll was called.
   */
  public void notifyAllDelegate()  throws IllegalMonitorStateException;
  /**
   * In production code this method should call
   * @see Object#wait()
   * In test code this method will delegate
   * to some sort of stub, for assertion that wait was called.
   */
  public void waitDelegate() throws InterruptedException;
  
  /**
   * In production code this method should call
   * @see Object#wait(long)
   * In test code this method will delegate
   * to some sort of stub, for assertion that wait(long) was called.
   */
  public void waitDelegate(long timeout) throws InterruptedException;
  
  /**
   * In production code this method should call
   * @see Object#wait(long, int)
   * In test code this method will delegate
   * to some sort of stub, for assertion that wait(long, int) was called.
   */
  public void waitDelegate(long timeout, int nanos) throws InterruptedException;
}
