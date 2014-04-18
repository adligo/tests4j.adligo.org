package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public interface I_TrialRunResult {
	public long getStartTime();
	public long getRunTime();
	public BigDecimal getRunTimeSecs();
	public List<I_PackageCoverage> getCoverage();
	public int getTrials();
	public int getTrialFailures();
	public long getTests();
	public long getTestFailures();
	public long getAsserts();
	public long getUniqueAsserts();
	public int getPassingTrials();
	public long getPassingTests();
}
