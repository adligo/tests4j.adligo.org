package org.adligo.tests4j.run.common;

public class ThreadingFactory implements I_ThreadingFactory {
  public static final ThreadingFactory INSTANCE = new ThreadingFactory();

  /**
   * use ThreadingFactory.INSTANCE 
   * assign to I_ThreadingFactory
   * and you have a stub that can 
   * have a mock injected into it.
   */
  private ThreadingFactory() { 
    
  }
  
  @Override
  public I_ThreadGroupFilter createThreadGroupFilter(String filter) {
    return new ThreadGroupFilter(filter);
  }

  @Override
  public I_ThreadGroupFilter createThreadGroupFilter(String filter, I_Threads threads) {
    return new ThreadGroupFilter(filter, threads);
  }
  
  @Override
  public <T> I_ThreadGroupLocal<T> createThreadGroupLocal(I_ThreadGroupFilter filter, I_InitalValueFactory<T> factory) {
    return new ThreadGroupLocal<T>(filter, factory);
  }
  
}
