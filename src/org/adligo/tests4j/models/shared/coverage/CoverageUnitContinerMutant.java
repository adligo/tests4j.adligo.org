package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.adligo.tests4j.models.shared.common.ClassMethods;

/**
 * @see I_CoverageUnitsContainer 
 * 
 * @author scott
 *
 */
public class CoverageUnitContinerMutant implements I_CoverageUnitsContainer {
	private I_CoverageUnits coverageUnits_;
	private I_CoverageUnits coveredCoverageUnits_;
	
	public CoverageUnitContinerMutant() {}
	
	public CoverageUnitContinerMutant(I_CoverageUnitsContainer p) {
		coverageUnits_ = p.getCoverageUnits();
		coveredCoverageUnits_ = p.getCoveredCoverageUnits();
	}
	

	@Override
	public I_CoverageUnits getCoverageUnits() {
		return coverageUnits_;
	}
	
	@Override
	public I_CoverageUnits getCoveredCoverageUnits() {
		return coveredCoverageUnits_;
	}
	
	@Override
	public BigDecimal getPercentageCovered() {
		if (coverageUnits_ == null) {
			return new BigDecimal("100.00");
		}
		BigDecimal coverageUnitsBD = new BigDecimal(coverageUnits_.getBig());
		if (coverageUnitsBD == null || coverageUnitsBD.doubleValue() == 0.0) {
			return new BigDecimal("100.00");
		}
		if (coveredCoverageUnits_ == null) {
			return new BigDecimal("0.00");
		}
		BigDecimal coveredCoverageUnitsBD = new BigDecimal(coveredCoverageUnits_.getBig());
		if (coverageUnitsBD == null || coveredCoverageUnitsBD == null ||
				coverageUnitsBD.doubleValue() == 0.0) {
			return new BigDecimal("100.00");
		} else if (coverageUnitsBD.intValue() == 0) {
			return new BigDecimal("100.00");
		} else {
			if (coveredCoverageUnitsBD.equals(coverageUnitsBD)) {
				return new BigDecimal("100.00");
			}
			BigDecimal pct = coveredCoverageUnitsBD.divide(coverageUnitsBD, MathContext.DECIMAL128);
			BigDecimal toRet = pct.multiply(new BigDecimal(100), MathContext.DECIMAL128);
			toRet = toRet.setScale(2, RoundingMode.HALF_UP);
			return toRet;
		} 
	}
	

	public void setCoverageUnits(I_CoverageUnits coverageUnits) {
		this.coverageUnits_ = coverageUnits;
	}

	public void setCoveredCoverageUnits(I_CoverageUnits coveredCoverageUnits) {
		this.coveredCoverageUnits_ = coveredCoverageUnits;
	}

	public void addCoverageUnits(I_CoverageUnits coverageUnits) {
		if (coverageUnits_ == null) {
			coverageUnits_ = new CoverageUnits(coverageUnits.getBig());
		} else {
			coverageUnits_ = new CoverageUnits(
					coverageUnits_.getBig().add(coverageUnits.getBig()));
		}
	}

	public void addCoveredCoverageUnits(I_CoverageUnits coveredCoverageUnits) {
		if (coveredCoverageUnits_ == null) {
			coveredCoverageUnits_ = new CoverageUnits(coveredCoverageUnits.getBig());
		} else {
			coveredCoverageUnits_ = new CoverageUnits(
				coveredCoverageUnits_.getBig().add(coveredCoverageUnits.getBig()));
		}
	}
	
	public String toString() {
		return toString(this) + "]";
	}
	public String toString(I_CoverageUnitsContainer other) {
		return ClassMethods.getSimpleName(other.getClass()) + 
				" [" + other.getCoverageUnits() + 
				"/" + other.getCoveredCoverageUnits();
	}

	@Override
	public double getPercentageCoveredDouble() {
		return getPercentageCovered().doubleValue();
	}
}
