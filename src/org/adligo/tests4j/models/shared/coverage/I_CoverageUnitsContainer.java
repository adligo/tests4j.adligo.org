package org.adligo.tests4j.models.shared.coverage;


/**
 * Implementations contain coverage units.
 * A coverage unit is either a instruction or branch
 * or other type of coverage counter.
 * 
 * @author scott
 *
 */
public interface I_CoverageUnitsContainer extends I_PercentCovered {
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

}
