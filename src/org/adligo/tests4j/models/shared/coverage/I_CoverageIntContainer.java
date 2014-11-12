package org.adligo.tests4j.models.shared.coverage;

/**
 * for data structures that are backed 
 * by probes, and not aggregate structures
 * use the int values for coverage units.
 * 
 * @author scott
 *
 */
public interface I_CoverageIntContainer {
  /**
   * The number of probes;
   * @return
   */
  public int getCoverageUnits();
  /**
   * The number of probes with a true value.
   * @return
   */
  public int getCoveredCoverageUnits();
}
