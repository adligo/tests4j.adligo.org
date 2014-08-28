package org.adligo.tests4j.gen.dependency_groups;


public class GenDepGroupContext {
	private String trialPackageName_;
	private Class<?> groupFactoryClass_;
	private String apiVersion_ = "";
	private ConstantLookup constantLookup = new ConstantLookup();
	private String trialClass_ = "org.adligo.tests4j.models.shared.trials.SourceFileTrial";
	private String trialClassSimple_ = "SourceFileTrial";
	private String extraTrialContent_ = "";
	private String extraTrialAnnotations_ = "";
	
	public String getTrialPackageName() {
		return trialPackageName_;
	}
	public Class<?> getGroupFactoryClass() {
		return groupFactoryClass_;
	}
	public void setTrialPackageName(String trialPackageName) {
		trialPackageName_ = trialPackageName;
	}
	public void setGroupFactoryClass(Class<?> groupFactoryClass) {
		groupFactoryClass_ = groupFactoryClass;
	}
	public String getApiVersion() {
		return apiVersion_;
	}
	public void setApiVersion(String apiVersion) {
		if (apiVersion != null) {
			apiVersion_ = apiVersion;
		}
	}
	public ConstantLookup getConstantLookup() {
		return constantLookup;
	}
	public void setConstantLookup(ConstantLookup constantLookup) {
		this.constantLookup = constantLookup;
	}
	public String getTrialClass() {
		return trialClass_;
	}
	public String getTrialClassSimpleName() {
		return trialClassSimple_;
	}
	public void setTrialClass(String trialClass) {
		trialClass_ = trialClass;
	}
	public void setTrialClassSimpleName(String trialClassSimple) {
		trialClassSimple_ = trialClassSimple;
	}
	public String getExtraTrialContent() {
		return extraTrialContent_;
	}
	public void setExtraTrialContent(String extraTrialContent) {
		extraTrialContent_ = extraTrialContent;
	}
	public String getExtraTrialAnnotations() {
		return extraTrialAnnotations_;
	}
	public void setExtraTrialAnnotations(String extraTrialAnnotations) {
		extraTrialAnnotations_ = extraTrialAnnotations;
	}
	
	
}	
