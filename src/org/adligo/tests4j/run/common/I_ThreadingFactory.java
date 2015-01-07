package org.adligo.tests4j.run.common;

/**
 * This class creates instances for Threading (Thread, ThreadsDelegate, ThreadGroupFilter,
 * etc), for stubbing so mocks can be injected.
 * 
 * @author scott
 *
 */
public interface I_ThreadingFactory {
  public I_ThreadGroupFilter createThreadGroupFilter(String filter);
  public I_ThreadGroupFilter createThreadGroupFilter(String filter, I_Threads threads);
  public <T> I_ThreadGroupLocal<T> createThreadGroupLocal(I_ThreadGroupFilter filter, I_InitalValueFactory<T> factory);
}
