package org.adligo.tests4j.shared.common;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

  @SuppressWarnings("unchecked")
  public static <T> T[] copyOf(T[] t) {
    /*
    List<T> l = new ArrayList<T>();
    for (int i = 0; i < t.length; i++) {
      T ti = t[i];
      l.add(ti);
    }
    */
    return (T[]) t.clone();
  }
  
  public static boolean[] copyOf(boolean[] t) {
    boolean [] out = new boolean[t.length];
    for (int i = 0; i < t.length; i++) {
      out[i] = t[i];
    }
    return out;
  }
}
