package org.adligo.tests4j.models.shared.coverage;

import java.util.List;


public interface I_SourceFileProbes extends I_ClassProbes, I_ProbeCoverageContainer, I_PercentCovered {

  /**
   * inner class probes
   * @return
   */
  public List<I_ClassProbes> getClassProbes();
}