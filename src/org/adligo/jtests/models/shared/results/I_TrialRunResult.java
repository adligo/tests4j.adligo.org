package org.adligo.jtests.models.shared.results;

import java.math.BigDecimal;

public interface I_TrialRunResult {
	public long getStartTime();
	public long getRunTime();
	public BigDecimal getRunTimeSecs();
}