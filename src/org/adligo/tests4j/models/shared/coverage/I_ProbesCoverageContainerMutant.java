package org.adligo.tests4j.models.shared.coverage;

/**
 * for data structures that are backed 
 * by probes, and not aggregate structures
 * use the int values for coverage units.
 * 
 * @author scott
 *
 */
public interface I_ProbesCoverageContainerMutant extends I_ProbesCoverageContainer {
  /**
   * The number of probes;
   * @return
   */
  public void setCoverageUnits(int p);
  /**
   * The number of probes with a true value.
   * @return
   */
  public void setCoveredCoverageUnits(int p);
}
