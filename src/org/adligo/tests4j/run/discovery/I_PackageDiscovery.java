package org.adligo.tests4j.run.discovery;

import java.util.List;

/**
 * This should find all of the classes 
 * and sub packages using any means necessary
 * to get accurate results
 * 
 * @author scott
 *
 */
public interface I_PackageDiscovery {

  public abstract String getPackageName();

  /**
   * the full class name including the package
   */
  public abstract List<String> getClassNames();

  public abstract List<I_PackageDiscovery> getSubPackages();

  /**
   * recurses into sub packages
   * @return
   */
  public abstract int getClassCount();

}