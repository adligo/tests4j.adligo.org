package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class PackageCoverageBriefDelegator implements I_PackageCoverageBrief {
	private I_PackageCoverageBrief delegate;
	
	public PackageCoverageBriefDelegator() {}
	
	public PackageCoverageBriefDelegator(I_PackageCoverageBrief p) {
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

	public List<I_PackageCoverageBrief> getChildPackageCoverage() {
		return delegate.getChildPackageCoverage();
	}

	public boolean hasChildPackageCoverage() {
		return delegate.hasChildPackageCoverage();
	}
}
