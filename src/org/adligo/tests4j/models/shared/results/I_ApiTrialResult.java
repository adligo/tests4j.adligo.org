package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.trials.PackageScope;

public interface I_ApiTrialResult extends I_TrialResult {
	public List<I_PackageCoverage> getPackageCoverage();
	/**
	 * @see PackageScope#packageName()
	 * @return
	 */
	public abstract String getPackageName();
}
