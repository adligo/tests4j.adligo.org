package org.adligo.tests4j.models.shared.i18n;

public interface I_Tests4J_AnnotationErrors {
	public String getIsStatic();
	public String getIsAbstract();
	public String getIsNotPublic();
	public String getHasParams();
	public String getHasNegativeTimeout();
	public String getWasAnnotatedIncorrectly();

	public String getAfterTrialHasParams();
	public String getAfterTrialNotStatic();
	public String getAfterTrialNotPublic();
	
	public String getBeforeTrialHasParams();
	public String getBeforeTrialNotStatic();
	public String getBeforeTrialNotPublic();
}
