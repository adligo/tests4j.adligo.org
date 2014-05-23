package org.adligo.tests4j.models.shared.metadata;

public interface I_AbstractTestRunInfo {

	public abstract boolean hasRecordedCoverage();

	public abstract I_TrialMetadata getTrialMetadata();

	public abstract long getUniqueAssertions();

	public abstract long getAssertions();

}