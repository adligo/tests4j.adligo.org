package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;

public interface I_Duration {
	public long getStartTime();
	public long getEndTime();
	public long getDuration();
	public BigDecimal getDurationSecs();
}
