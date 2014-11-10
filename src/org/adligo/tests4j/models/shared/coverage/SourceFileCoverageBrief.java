package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SourceFileCoverageBrief extends ClassProbes implements I_SourceFileCoverageBrief {
  private int coveredCoverageUnits_;
  private int coverageUnits_;

  /**
   * note the classes in here are only for inner classes ie Foo$1.class
   * not for the source file outer class itself.
   */
  private List<ClassProbes> classProbes_ = new ArrayList<ClassProbes>();

  
  public SourceFileCoverageBrief(I_SourceFileCoverageBrief sourceFileProbes) {
    super(sourceFileProbes);
    setClassProbes(sourceFileProbes.getClassProbes());
    coverageUnits_ = sourceFileProbes.getCoverageUnits();
    coveredCoverageUnits_ = sourceFileProbes.getCoveredCoverageUnits();

  }
  
  public List<I_ClassProbes> getClassProbes() {
    return new ArrayList<I_ClassProbes>(classProbes_);
  }
  
  
  private void setClassProbes(Collection<I_ClassProbes> classProbes) {
    this.classProbes_.clear();
    if (classProbes != null) {
      for (I_ClassProbes cp: classProbes) {
        //class cast exception instead of instanceof is for GWT
        try {
          this.classProbes_.add((ClassProbes) cp);
        } catch (ClassCastException x) {
          this.classProbes_.add(new ClassProbes(cp));
        }
      }
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
}
