package org.adligo.tests4j.run.common;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * a concurrent map wrapper with additional methods
 * to wait until a value is available, for use
 * when a instance may be created by another thread
 * for sharing.
 * @author scott
 *
 * @param <K>
 * @param <V>
 */
public class ConcurrentQualifiedMap<K,V> implements ConcurrentMap<K, V>{
	private ConcurrentMap<K,V> entries_;
	private I_Notifier notifier_;
	
	public ConcurrentQualifiedMap(ConcurrentMap<K,V> pEntries) {
		this(pEntries, null);
	}
	
	/**
	 * 
	 * @param pEntries
	 * @param notifier generally only used for test stubbing
	 * for wait/notify calls
	 */
	public ConcurrentQualifiedMap(ConcurrentMap<K,V> pEntries, I_Notifier notifier) {
    entries_ = pEntries;
    if (notifier != null) {
      notifier_ = notifier;
    } else {
      notifier_ = new NotifierDelegate(this);
    }
  }
	/**
	 * @param o
	 * @return if the put was successful return the KeyedAtomicBoolean<T>
	 *  null otherwise 
	 */
	public synchronized V putIfAbsent(K o, V v) {
    V toRet = entries_.putIfAbsent(o, v);
    notifier_.notifyAllDelegate();
    return toRet;
  }

	/**
	 * get the value from the backing
	 * map, if the backing map is null
	 * then wait for it to be put in the 
	 * map and return the value put in the map.
	 * @param o
	 * @return
	 */
	public V obtain(K o) {
	  if (!containsKey(o)) {
	    await(o);
	  }
    return get(o);
  }
	
	public synchronized void await(K o) {
		while (!entries_.containsKey(o)) {
			try {
			  notifier_.waitDelegate(26);
			} catch (InterruptedException e) {
			  //http://www.ibm.com/developerworks/library/j-jtp05236/
			  notifier_.interrupt();
			}
		}
		while (entries_.get(o) == null) {
			try {
			  notifier_.waitDelegate(25);
			} catch (InterruptedException e) {
			  //http://www.ibm.com/developerworks/library/j-jtp05236/
			  notifier_.interrupt();
			}
		}
	}

	/**
	 * return the backing maps entry set
	 * no synchronization
	 * @return
	 */
	public Set<Entry<K,V>> entrySet() {
	  return entries_.entrySet();
	}
	
  public I_Notifier getNotifier() {
    return notifier_;
  }

  @Override
  public int size() {
    return entries_.size();
  }

  @Override
  public boolean isEmpty() {
    return entries_.isEmpty();
  }


  @Override
  public V put(K key, V value) {
    return entries_.put(key, value);
  }

  @Override
  public V remove(Object key) {
    return entries_.remove(key);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    entries_.putAll(m);
  }

  @Override
  public void clear() {
    entries_.clear();
  }

  @Override
  public Set<K> keySet() {
    return entries_.keySet();
  }

  @Override
  public Collection<V> values() {
    return entries_.values();
  }

  @Override
  public boolean remove(Object key, Object value) {
    return entries_.remove(key, value);
  }

  @Override
  public boolean replace(K key, V oldValue, V newValue) {
    return entries_.replace(key, oldValue, newValue);
  }

  @Override
  public V replace(K key, V value) {
    return entries_.replace(key, value);
  }

  @Override
  public boolean containsKey(Object key) {
    return entries_.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return entries_.containsValue(value);
  }

  @Override
  public V get(Object key) {
    return entries_.get(key);
  }
}
