package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public class TrialRunResultMutant implements I_TrialRunResult {
	private long startTime;
	private long runTime;
	private List<I_PackageCoverage> coverage = new ArrayList<I_PackageCoverage>();
	
	public TrialRunResultMutant() {}
	
	public TrialRunResultMutant(I_TrialRunResult results) {
		startTime = results.getStartTime();
		runTime = results.getRunTime();
		coverage.addAll(results.getCoverage());
	}

	public long getStartTime() {
		return startTime;
	}

	public long getRunTime() {
		return runTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	
	public BigDecimal getRunTimeSecs() {
		BigDecimal rt = new BigDecimal(runTime);
		rt = rt.divide(new BigDecimal(1000));
		return rt;
	}

	public List<I_PackageCoverage> getCoverage() {
		return coverage;
	}

	public void setCoverage(List<I_PackageCoverage> coverage) {
		this.coverage = coverage;
	}
}
