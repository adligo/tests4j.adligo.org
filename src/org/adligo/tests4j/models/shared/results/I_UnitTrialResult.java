package org.adligo.tests4j.models.shared.results;

public interface I_UnitTrialResult extends I_TrialResult {
  /**
   * This returns the name of the analysis unit
   * which is usually something like a jar name,
   * a java package name or the name of a software
   * system.   If your doing use case analysis
   * this would be the subject of the use cases.
   */
  public String getUnit();
}
