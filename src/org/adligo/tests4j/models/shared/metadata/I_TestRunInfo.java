package org.adligo.tests4j.models.shared.metadata;

public interface I_TestRunInfo {

	public abstract boolean hasRecordedCoverage();

	public abstract long getUniqueAssertions();

	public abstract long getAssertions();

}