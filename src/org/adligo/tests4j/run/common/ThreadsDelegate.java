package org.adligo.tests4j.run.common;

public class ThreadsDelegate implements I_Threads {

  @Override
  public Thread currentThread() {
    return Thread.currentThread();
  }

}
