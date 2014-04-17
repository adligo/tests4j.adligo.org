package org.adligo.tests4j.models.shared.metadata;

import java.util.List;

public interface I_TrialMetadata {

	public abstract String getTrialName();

	public abstract Long getTimeout();

	public abstract boolean isSkipped();

	public abstract String getBeforeTrialMethodName();

	public abstract String getAfterTrialMethodName();

	public abstract List<? extends I_TestMetadata> getTests();

	public int getTestCount();
	
	public int getSkippedTestCount();
}