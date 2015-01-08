package org.adligo.tests4j.run.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is a ThreadLocal which 
 * obtains it's initial value from a a factory.
 * 
 * @author scott
 *
 * @param <T>
 */
public class ThreadGroupLocal<T> extends ThreadLocal<Holder<T>> implements I_ThreadGroupLocal<T>{
	private final ConcurrentHashMap<String, Holder<T>> threadGroupLocalMap_ = new ConcurrentHashMap<String, Holder<T>>();
	private final I_ThreadGroupFilter filter_;
	private final I_InitalValueFactory<T> factory_;
	
	/**
	 * 
	 * @param filter 
	 * @param factory
	 */
	public ThreadGroupLocal(I_ThreadGroupFilter filter, I_InitalValueFactory<T> factory) {
	  if (filter == null) {
	    throw new NullPointerException();
	  }
		filter_ = filter;
		if (factory == null) {
		  throw new NullPointerException();
		}
		factory_ = factory;
	}
	
	@Override 
  protected Holder<T> initialValue() {
  	String groupName = filter_.getThreadGroupNameMatchingFilter();
  	if (groupName == null) {
  	  return null;
  	}
  	Holder<T> holder = threadGroupLocalMap_.get(groupName);
  	if (holder == null) {
  		holder = new Holder<T>();
  		holder.setHeld(factory_.createNew());
  		threadGroupLocalMap_.putIfAbsent(groupName,holder);
  		holder = threadGroupLocalMap_.get(groupName);
  	}
  	return holder;
  }

  /* (non-Javadoc)
   * @see org.adligo.tests4j.run.common.I_ThreadGroupLocal#getValue()
   */
  @Override
  public T getValue() { 
  	Holder<T> holder = super.get();
  	if (holder == null) {
  	  return null;
  	}
  	T toRet = holder.getHeld();
  	if (toRet == null) {
  		toRet = factory_.createNew();
  		holder.setHeld(toRet);
  	}
  	return toRet;
  }
	
}
