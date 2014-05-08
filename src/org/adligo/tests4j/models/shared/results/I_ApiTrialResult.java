package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public interface I_ApiTrialResult extends I_TrialResult {
	public List<I_PackageCoverage> getPackageCoverage();
	public abstract String getTestedPackageName();
}
