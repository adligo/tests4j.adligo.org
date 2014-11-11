package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SourceFileCoverageBriefMutant extends ClassProbesMutant implements I_SourceFileCoverageBriefMutant {

  /**
   * note the classes in classProbes_ are only for inner classes ie Foo$1.class
   * not for the source file outer class itself.
   */
  private List<ClassProbesMutant> classProbes_ = new ArrayList<ClassProbesMutant>();
  private int coveredCoverageUnits_;
  private int coverageUnits_;
  
  public SourceFileCoverageBriefMutant() {}
  
  public SourceFileCoverageBriefMutant(I_ClassProbes classProbes) {
    super(classProbes);
  }
  public SourceFileCoverageBriefMutant(I_SourceFileCoverageBrief sourceFileCoverageBrief) {
    super(sourceFileCoverageBrief);
    
    setClassProbes(sourceFileCoverageBrief.getClassProbes());
    coverageUnits_ = sourceFileCoverageBrief.getCoverageUnits();
    coveredCoverageUnits_ = sourceFileCoverageBrief.getCoveredCoverageUnits();
  }
  
  public List<I_ClassProbes> getClassProbes() {
    return new ArrayList<I_ClassProbes>(classProbes_);
  }
  
  public void setClassProbes(Collection<? extends I_ClassProbes> classProbes) {
    classProbes_.clear();
    if (classProbes != null) {
      for (I_ClassProbes cp: classProbes) {
        addClassProbe(cp);
      }
    }
  }

  public void addClassProbe(I_ClassProbes cp) {
    //class cast exception instead of instanceof is for GWT
    try {
      classProbes_.add((ClassProbesMutant) cp);
    } catch (ClassCastException x) {
      classProbes_.add(new ClassProbesMutant(cp));
    }
  }

  @Override
  public int getCoverageUnits() {
    return coverageUnits_;
  }

  @Override
  public int getCoveredCoverageUnits() {
    return coveredCoverageUnits_;
  }

  @Override
  public BigDecimal getPercentageCovered() {
    return new BigDecimal(getPercentageCoveredDouble()).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public double getPercentageCoveredDouble() {
    double top = getCoveredCoverageUnits();
    double bottom = getCoverageUnits();
    double pct = top/bottom;
    return 100 * pct;
  }

  public void setCoveredCoverageUnits(int coveredCoverageUnits) {
    this.coveredCoverageUnits_ = coveredCoverageUnits;
  }

  public void setCoverageUnits(int coverageUnits) {
    this.coverageUnits_ = coverageUnits;
  }
}
