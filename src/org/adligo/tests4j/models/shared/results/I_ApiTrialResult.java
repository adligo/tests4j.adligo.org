package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.system.shared.trials.PackageScope;

public interface I_ApiTrialResult extends I_TrialResult {
  /**
   * The package coverage for the package
   * which is in the PackageScope for this trial
   * @return
   */
	public I_PackageCoverageBrief getPackageCoverage();
	/**
	 * @see PackageScope#packageName()
	 * @return
	 */
	public abstract String getPackageName();
}
