package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;

/**
 * @see I_CoverageUnitsContainer 
 * 
 * @author scott
 *
 */
public class CoverageUnitContinerMutant implements I_CoverageUnitsContainer {
	private I_CoverageUnits coverageUnits;
	private I_CoverageUnits coveredCoverageUnits;
	
	public CoverageUnitContinerMutant() {}
	
	public CoverageUnitContinerMutant(I_CoverageUnitsContainer p) {
		coverageUnits = p.getCoverageUnits();
		coveredCoverageUnits = p.getCoveredCoverageUnits();
	}
	

	@Override
	public I_CoverageUnits getCoverageUnits() {
		return coverageUnits;
	}
	
	@Override
	public I_CoverageUnits getCoveredCoverageUnits() {
		return coveredCoverageUnits;
	}
	
	@Override
	public BigDecimal getPercentageCovered() {
		BigDecimal coverageUnitsBD = new BigDecimal(coverageUnits.getBig());
		BigDecimal coveredCoverageUnitsBD = new BigDecimal(coveredCoverageUnits.getBig());
		BigDecimal toRet = coveredCoverageUnitsBD.divide(coverageUnitsBD).multiply(new BigDecimal(100));
		return toRet;
	}
	

	public void setCoverageUnits(I_CoverageUnits coverageUnits) {
		this.coverageUnits = coverageUnits;
	}

	public void setCoveredCoverageUnits(I_CoverageUnits coveredCoverageUnits) {
		this.coveredCoverageUnits = coveredCoverageUnits;
	}

	public void toString(StringBuilder sb) {
		sb.append("coverageUnits=");
		sb.append(coverageUnits);
		sb.append(", coveredCoverageUnits=");
		sb.append(coveredCoverageUnits);
	}

	@Override
	public double getPercentageCoveredDouble() {
		return getPercentageCovered().doubleValue();
	}
}
