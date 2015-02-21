package org.adligo.tests4j.shared.common;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

  public static <T> T[] copyOf(T[] src, T[] dest) {
    List<T> l = new ArrayList<T>();
    for (int i = 0; i < src.length; i++) {
      T ti = src[i];
      l.add(ti);
    }
    return l.toArray(dest);
  }
  
  public static boolean[] copyOf(boolean[] t) {
    boolean [] out = new boolean[t.length];
    for (int i = 0; i < t.length; i++) {
      out[i] = t[i];
    }
    return out;
  }
}
