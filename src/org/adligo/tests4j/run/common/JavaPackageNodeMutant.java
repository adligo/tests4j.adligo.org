package org.adligo.tests4j.run.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JavaPackageNodeMutant implements I_JavaPackageNode {
  private String name_;
  private List<JavaPackageNodeMutant> childNodes_ = new ArrayList<JavaPackageNodeMutant>();
  /**
   * the Class.getShortName values
   */
  private List<String> classNames_ = new ArrayList<String>();
  
  public JavaPackageNodeMutant() {}
  
  public JavaPackageNodeMutant(String name) {
    name_ = name;
  }

  /* (non-Javadoc)
   * @see org.adligo.tests4j.run.common.I_JavaPackageNode#getName()
   */
  @Override
  public String getName() {
    return name_;
  }

  public void setName(String name) {
    name_ = name;
  }

  /* (non-Javadoc)
   * @see org.adligo.tests4j.run.common.I_JavaPackageNode#getChildNodes()
   */
  @Override
  public List<I_JavaPackageNode> getChildNodes() {
    return new ArrayList<I_JavaPackageNode>(childNodes_);
  }

  public void setChildNodes(Collection<JavaPackageNodeMutant> childNodes) {
    childNodes_.clear();
    if (childNodes != null) {
      childNodes_.addAll(childNodes);
    }
  }

  /* (non-Javadoc)
   * @see org.adligo.tests4j.run.common.I_JavaPackageNode#getClassNames()
   */
  @Override
  public List<String> getClassNames() {
    return classNames_;
  }

  public void setClassName(Collection<String> classNames) {
    classNames_.clear();
    if (classNames != null) {
      classNames_.addAll(classNames);
    }
  }
  
}
