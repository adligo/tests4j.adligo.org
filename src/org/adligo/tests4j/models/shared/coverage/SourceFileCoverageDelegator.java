package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;

public class SourceFileCoverageDelegator implements I_SourceFileCoverage {
	private I_SourceFileCoverage delegate;
	
	public SourceFileCoverageDelegator() {}
	
	public SourceFileCoverageDelegator(I_SourceFileCoverage p) {
		delegate = p;
	}

	public String getClassName() {
		return delegate.getClassName();
	}

	public I_CoverageUnits getCoverageUnits() {
		return delegate.getCoverageUnits();
	}

	public int getLastLine() {
		return delegate.getLastLine();
	}

	public I_CoverageUnits getCoveredCoverageUnits() {
		return delegate.getCoveredCoverageUnits();
	}

	public I_LineCoverage getLineCoverage(int p) {
		return delegate.getLineCoverage(p);
	}

	public BigDecimal getPercentageCovered() {
		return delegate.getPercentageCovered();
	}

	public double getPercentageCoveredDouble() {
		return delegate.getPercentageCoveredDouble();
	}
	
}
