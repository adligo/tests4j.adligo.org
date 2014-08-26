package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.math.MathContext;

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
		if (coverageUnitsBD == null || coveredCoverageUnitsBD == null) {
			return new BigDecimal("100.00");
		} else if (coverageUnitsBD.intValue() == 0) {
			return new BigDecimal("100.00");
		} else {
			if (coveredCoverageUnitsBD.equals(coverageUnitsBD)) {
				return new BigDecimal("100.00");
			}
			BigDecimal pct = coveredCoverageUnitsBD.divide(coverageUnitsBD, MathContext.DECIMAL128);
			BigDecimal toRet = pct.multiply(new BigDecimal(100), MathContext.DECIMAL128);
			MathContext mc = new MathContext(2);
			return toRet.round(mc);
		} 
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
