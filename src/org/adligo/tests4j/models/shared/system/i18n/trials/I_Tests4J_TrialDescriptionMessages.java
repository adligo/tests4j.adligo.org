package org.adligo.tests4j.models.shared.system.i18n.trials;


public interface I_Tests4J_TrialDescriptionMessages extends I_Tests4J_AnnotationErrors {
	public I_Tests4j_BeforeTrialErrors getBeforeTrialErrors();
	public I_Tests4J_TestMethodErrors getTestMethodErrors();
	public I_Tests4J_AfterTrialTestsErrors getAfterTrialTestsErrors();
	public I_Tests4j_AfterTrialErrors getAfterTrialErrors();
	
	public String getNoUseCaseScope();
	public String getUseCaseScopeEmptySystem();
	public String getUseCaseScopeEmptyNown();
	public String getUseCaseScopeEmptyVerb();
	public String getNoPackageScope();
	public String getPackageScopeEmptyName();
	public String getNoSourceFileScope();
	public String getSourceFileScopeEmptyClass();
	public String getBadConstuctor();
	public String getMultipleBeforeTrial();
	public String getMultipleAfterTrialTests();
	public String getMultipleAfterTrial();
	public String getNoTests();
	public String getMissingTypeAnnotationPre();
	public String getMissingTypeAnnotationPost();
}
