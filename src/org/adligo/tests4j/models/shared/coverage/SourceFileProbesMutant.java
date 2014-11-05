package org.adligo.tests4j.models.shared.coverage;

import java.util.ArrayList;
import java.util.List;

public class SourceFileProbesMutant implements I_SourceFileProbes {
  /**
   * This is the name of the class file/source file
   * with out .java and dots for names.
   */
  private String name;
  private ProbesMutant probes;
  private List<ClassProbesMutant> classProbes = new ArrayList<ClassProbesMutant>();
  
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.coverage.I_SourceFileProbes#getName()
   */
  @Override
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  /* (non-Javadoc)
   * @see org.adligo.tests4j.models.shared.coverage.I_SourceFileProbes#getProbes()
   */
  @Override
  public ProbesMutant getProbes() {
    return probes;
  }
  public void setProbes(ProbesMutant probes) {
    this.probes = probes;
  }
  public List<I_ClassProbes> getClassProbes() {
    return new ArrayList<I_ClassProbes>(classProbes);
  }
  public void setClassProbes(List<ClassProbesMutant> classProbes) {
    this.classProbes = classProbes;
  }
}
