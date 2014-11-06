package org.adligo.tests4j.run.common;

import java.util.concurrent.ConcurrentHashMap;

import org.adligo.tests4j.run.helpers.ThreadLogMessageBuilder;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * This class is a ThreadLocal which 
 * obtains it's initial value from a a factory
 * @author scott
 *
 * @param <T>
 */
public class ThreadGroupLocal<T> extends ThreadLocal<Holder<T>>{
	private final ConcurrentHashMap<String, Holder<T>> threadGroupLocalMap_ = new ConcurrentHashMap<String, Holder<T>>();
	private final ThreadGroupFilter filter_;
	private final I_InitalValueFactory<T> factory_;
	
	public ThreadGroupLocal(ThreadGroupFilter filter, I_InitalValueFactory<T> factory) {
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