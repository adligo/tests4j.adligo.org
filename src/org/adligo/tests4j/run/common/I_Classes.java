package org.adligo.tests4j.run.common;

/**
 * This is just a stub for Class.forName
 * @author scott
 *
 */
public interface I_Classes {
  public Class<?> forName(String name) throws ClassNotFoundException;
  public Class<?> forName(String name, boolean initialize, ClassLoader loader)
     throws ClassNotFoundException;
}
