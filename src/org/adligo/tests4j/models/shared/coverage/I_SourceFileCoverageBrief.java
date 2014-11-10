package org.adligo.tests4j.models.shared.coverage;

import java.util.List;


public interface I_SourceFileCoverageBrief extends I_ClassProbes, I_ProbesCoverageContainer, I_PercentCovered {

  /**
   * inner class probes
   * @return
   */
  public List<I_ClassProbes> getClassProbes();
}