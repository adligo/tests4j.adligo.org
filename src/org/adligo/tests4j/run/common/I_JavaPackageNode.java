package org.adligo.tests4j.run.common;

import java.util.List;

public interface I_JavaPackageNode {

  public abstract String getName();

  public abstract List<I_JavaPackageNode> getChildNodes();

  public abstract List<String> getClassNames();

  /**
   * counts the classes including the child node classes
   * @return
   */
  public int countClasses();
}