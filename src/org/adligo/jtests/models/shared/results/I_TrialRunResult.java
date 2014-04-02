package org.adligo.jtests.models.shared.results;

import java.math.BigDecimal;
import java.util.List;

import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public interface I_TrialRunResult {
	public long getStartTime();
	public long getRunTime();
	public BigDecimal getRunTimeSecs();
	public List<I_PackageCoverage> getCoverage();
}
