package org.adligo.tests4j.run.common;

/**
 * This is a wrapper around the java.lang.Object
 * wait and notify methods, note none of the methods
 * are final, so they can be overridden by stubs.
 *   
 * @author scott
 *
 */
public class NotifierDelegate implements I_Notifier {
  private final Object delegate_;
  
  public NotifierDelegate(Object delegate) {
    if (delegate == null) {
      throw new NullPointerException();
    }
    delegate_ = delegate;
  }
  
  @Override
  public void notifyDelegate() throws IllegalMonitorStateException {
    delegate_.notify();
  }

  @Override
  public void notifyAllDelegate() throws IllegalMonitorStateException {
    delegate_.notifyAll();
  }

  @Override
  public void waitDelegate() throws InterruptedException {
    delegate_.wait();
  }

  @Override
  public void waitDelegate(long timeout) throws InterruptedException {
    delegate_.wait(timeout);
  }

  @Override
  public void waitDelegate(long timeout, int nanos) throws InterruptedException {
    delegate_.wait(timeout, nanos);
  }

  @Override
  public Object getInstance() {
    return delegate_;
  }

  @Override
  public void interrupt() {
    Thread.currentThread().interrupt();
  }

}
