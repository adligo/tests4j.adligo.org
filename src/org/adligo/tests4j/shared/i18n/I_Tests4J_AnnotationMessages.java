package org.adligo.tests4j.shared.i18n;

public interface I_Tests4J_AnnotationMessages {
	public String getIsStatic();
	public String getIsAbstract();
	public String getIsNotPublic();
	public String getHasParams();
	public String getHasNegativeTimeout();


	public String getAfterTrialHasParams();
	public String getAfterTrialNotStatic();
	public String getAfterTrialNotPublic();
	
	public String getBeforeTrialHasWrongParams();
	public String getBeforeTrialNotStatic();
	public String getBeforeTrialNotPublic();
	
	
	public String getMinCoverageMustBeBetweenZeroAndOneHundred();
	
	public String getMultipleBeforeTrial();
	public String getMultipleAfterTrial();
	
	public String getNoSourceFileScope();
	public String getNoPackageScope();
	public String getNoUseCaseScope();
	
	public String getPackageScopeEmptyName();
	public String getSourceFileScopeEmptyClass();
	
	public String getUseCaseScopeEmptyName();
	public String getUseCaseScopeNameUnknown();
	
	public String getWasAnnotatedIncorrectly();
	public String getTrialTypeMissing();
	
	public String getAllowedDependenciesIsNotASingletonWarning();
}
