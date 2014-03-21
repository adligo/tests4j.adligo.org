package org.adligo.jtests.models.shared.results;

import java.util.Set;

public interface I_TestResult {

	public abstract String getExhibitName();

	public abstract int getAssertionCount();

	public abstract int getUniqueAssertionCount();

	public Set<Integer> getUniqueAsserts();
	
	public abstract boolean isPassed();

	public abstract boolean isIgnored();

	public abstract I_TestFailure getFailure();

	public abstract String getBeforeOutput();

	public abstract String getOutput();

	public abstract String getAfterOutput();

}