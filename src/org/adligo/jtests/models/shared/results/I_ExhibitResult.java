package org.adligo.jtests.models.shared.results;

public interface I_ExhibitResult {

	public abstract String getExhibitName();

	public abstract int getAssertionCount();

	public abstract int getUniqueAsserts();

	public abstract boolean isPassed();

	public abstract boolean isIgnored();

	public abstract FailureMutant getFailure();

	public abstract String getBeforeOutput();

	public abstract String getOutput();

	public abstract String getAfterOutput();

}