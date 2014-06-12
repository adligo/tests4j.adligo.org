package org.adligo.tests4j.models.shared.metadata;

import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;

public interface I_TrialMetadata {

	public abstract String getTrialName();

	public abstract Long getTimeout();

	public abstract boolean isSkipped();

	public abstract String getBeforeTrialMethodName();

	public abstract String getAfterTrialMethodName();

	public abstract List<? extends I_TestMetadata> getTests();

	public int getTestCount();
	
	public int getSkippedTestCount();
	
	public TrialTypeEnum getType();

	public String getTestedClass() ;

	public String getTestedPackage();

	public String getSystem();

	public I_UseCase getUseCase();
}