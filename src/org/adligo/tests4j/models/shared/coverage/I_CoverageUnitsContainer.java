package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;

/**
 * Implementations contain coverage units.
 * A coverage unit is either a instruction or branch
 * or other type of coverage counter.
 * 
 * @author scott
 *
 */
public interface I_CoverageUnitsContainer {
	/**
	 * Coverage Units are simply the count of all 
	 * instructions/branches exc
	 * @return
	 */
	public I_CoverageUnits getCoverageUnits();
	/**
	 * Return the number of CoverageUnits that
	 * were actually covered 
	 * @return
	 */
	public I_CoverageUnits getCoveredCoverageUnits();
	
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
