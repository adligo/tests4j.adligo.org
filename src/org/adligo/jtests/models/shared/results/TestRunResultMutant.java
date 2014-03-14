package org.adligo.jtests.models.shared.results;

import java.math.BigDecimal;

public class TestRunResultMutant implements I_TestRunResult {
	private long startTime;
	private long runTime;
	
	public TestRunResultMutant() {}
	
	public TestRunResultMutant(I_TestRunResult results) {
		startTime = results.getStartTime();
		runTime = results.getRunTime();
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
}
