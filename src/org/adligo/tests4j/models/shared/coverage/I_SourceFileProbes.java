package org.adligo.tests4j.models.shared.coverage;

import java.util.List;


public interface I_SourceFileProbes {

  public abstract String getName();

  public abstract I_Probes getProbes();

  public List<I_ClassProbes> getClassProbes();
}