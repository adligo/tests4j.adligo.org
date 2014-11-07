package org.adligo.tests4j.run.common;

public class ClassesDelegate implements I_Classes {

  @Override
  public Class<?> forName(String name) throws ClassNotFoundException {
    return Class.forName(name);
  }

  @Override
  public Class<?> forName(String name, boolean initialize, ClassLoader loader)
      throws ClassNotFoundException {
    return Class.forName(name, initialize, loader);
  }
  
}
