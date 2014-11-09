package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;

public interface I_PercentCovered {
  
  /**
   * Return the percentage of coverage units covered
   * from 0.00 to 100.00.
   * @return
   */
  public BigDecimal getPercentageCovered();
  
  /**
   * same as getPercentageCovered but
   * return a double
   * @return
   */
  public double getPercentageCoveredDouble();
}
