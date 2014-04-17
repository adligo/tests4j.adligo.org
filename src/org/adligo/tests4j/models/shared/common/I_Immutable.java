package org.adligo.tests4j.models.shared.common;

/**
 * this is just a marker interface to show that
 * the instances of classes which implement this 
 * do not allow mutation of any kind;
 * ie this can't be done
 * imutable.setName(String)
 * List<String> names  = imutable.getNames();
 * names.add("foo");
 * List<AtomicInteger> ids = imutable.getIds()l
 * ids.get(0).
 * @author scott
 *
 */
public interface I_Immutable {

}
