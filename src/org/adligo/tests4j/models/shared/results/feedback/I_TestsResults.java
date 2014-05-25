package org.adligo.tests4j.models.shared.results.feedback;

public interface I_TestsResults {

	public abstract boolean hasRecordedCoverage();

	public abstract long getUniqueAssertions();

	public abstract long getAssertions();

}