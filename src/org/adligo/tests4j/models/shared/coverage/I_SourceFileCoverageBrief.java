package org.adligo.tests4j.models.shared.coverage;

import java.util.List;


public interface I_SourceFileCoverageBrief extends I_ClassProbes, I_CoverageIntContainer, I_PercentCovered {

  /**
   * inner class probes
   * @return
   */
  public List<I_ClassProbes> getClassProbes();
}