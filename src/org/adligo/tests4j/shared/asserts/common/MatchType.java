package org.adligo.tests4j.shared.asserts.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This enum represents different ways to match Throwable messages.
 * 
 * @author scott
 *
 */
public enum MatchType implements I_MatchType {
  /**
   * The actual Throwable must have a null message.
   */
  NULL(0), 
  /**
   * The actual Throwable may have any message, including null.
   */
  ANY(1), 
  /**
   * The actual Throwable message must contain the expected Throwable's message ignoring case.
   */
  CONTAINS(2), 
  /**
   * The actual Throwable and expected Throwable messages must match exactly.
   */
  EQUALS(3);
  
  private static final Map<Integer,MatchType> TYPES = getTypes();
  
  private static Map<Integer,MatchType> getTypes() {
    Map<Integer,MatchType> toRet = new HashMap<Integer,MatchType>();
    add(toRet, ANY);
    add(toRet, CONTAINS);
    add(toRet, EQUALS);
    add(toRet, NULL);
    return Collections.unmodifiableMap(toRet);
  }
  
  @SuppressWarnings("boxing")
  private static void add(Map<Integer, MatchType> map, MatchType type) {
    map.put(type.getId(), type);
  }
  
  private final int id_;

  private MatchType(int id) {
    id_ = id;
  }
  public int getId() {
    return id_;
  }
  
  @SuppressWarnings("boxing")
  public static MatchType get(I_MatchType t) {
    return TYPES.get(t.getId());
  }
}
