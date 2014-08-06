package org.adligo.tests4j.models.shared.i18n;

public interface I_Tests4J_AnnotationErrors {
	public String getIsStatic();
	public String getIsAbstract();
	public String getIsNotPublic();
	public String getHasParams();
	public String getHasNegativeTimeout();


	public String getAfterTrialHasParams();
	public String getAfterTrialNotStatic();
	public String getAfterTrialNotPublic();
	
	public String getBeforeTrialHasParams();
	public String getBeforeTrialNotStatic();
	public String getBeforeTrialNotPublic();
	
	
	public String getMinCoverageMustBeBetweenZeroAndOneHundred();
	
	public String getMultipleBeforeTrial();
	public String getMultipleAfterTrial();
	
	public String getNoTests();
	public String getNoSourceFileScope();
	public String getNoPackageScope();
	
	public String getNoUseCaseScope();
	public String getPackageScopeEmptyName();
	public String getSourceFileScopeEmptyClass();
	
	public String getUseCaseScopeEmptyNown();
	public String getUseCaseScopeEmptySystem();
	public String getUseCaseScopeEmptyVerb();
	public String getWasAnnotatedIncorrectly();
	public String getTrialTypeMissing();
}
