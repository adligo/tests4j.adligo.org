package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;

/**
 * @see I_SourceFileCoverage
 * @author scott
 *
 */
public class SourceFileCoverage {
	private SourceFileCoverageMutant mutant;
	
	public SourceFileCoverage() {
		mutant = new SourceFileCoverageMutant();
	}
	
	public SourceFileCoverage(I_SourceFileCoverage p) {
		mutant = new SourceFileCoverageMutant(p);
	}

	public I_CoverageUnits getCoverageUnits() {
		return mutant.getCoverageUnits();
	}

	public I_CoverageUnits getCoveredCoverageUnits() {
		return mutant.getCoveredCoverageUnits();
	}

	public BigDecimal getPercentageCovered() {
		return mutant.getPercentageCovered();
	}

	public String getClassName() {
		return mutant.getClassName();
	}

	public int getLastLine() {
		return mutant.getLastLine();
	}

	public I_LineCoverage getLineCoverage(int p) {
		return mutant.getLineCoverage(p);
	}

	public String toString() {
		return mutant.toString();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
}
