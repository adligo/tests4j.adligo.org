package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class PackageCoverageDelegator implements I_PackageCoverage {
	private I_PackageCoverage delegate;
	
	public PackageCoverageDelegator() {}
	
	public PackageCoverageDelegator(I_PackageCoverage p) {
		delegate = p;
	}

	public I_CoverageUnits getCoverageUnits() {
		return delegate.getCoverageUnits();
	}

	public I_CoverageUnits getCoveredCoverageUnits() {
		return delegate.getCoveredCoverageUnits();
	}

	public BigDecimal getPercentageCovered() {
		return delegate.getPercentageCovered();
	}

	public String getPackageName() {
		return delegate.getPackageName();
	}

	public I_SourceFileCoverage getCoverage(String sourceFileName) {
		return delegate.getCoverage(sourceFileName);
	}

	public double getPercentageCoveredDouble() {
		return delegate.getPercentageCoveredDouble();
	}

	public Set<String> getSourceFileNames() {
		return delegate.getSourceFileNames();
	}

	public List<I_PackageCoverage> getChildPackageCoverage() {
		return delegate.getChildPackageCoverage();
	}

	public boolean hasChildPackageCoverage() {
		return delegate.hasChildPackageCoverage();
	}

	public I_CoverageUnits getTotalCoverageUnits() {
		return delegate.getTotalCoverageUnits();
	}

	public I_CoverageUnits getTotalCoveredCoverageUnits() {
		return delegate.getTotalCoveredCoverageUnits();
	}

	public BigDecimal getTotalPercentageCovered() {
		return delegate.getTotalPercentageCovered();
	}

	public double getTotalPercentageCoveredDouble() {
		return delegate.getTotalPercentageCoveredDouble();
	}
}
