package org.adligo.tests4j.models.shared.results;

import java.util.Set;

import org.adligo.tests4j.shared.asserts.common.I_TestFailure;

public interface I_TestResult {

	public abstract String getName();

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