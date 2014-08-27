package org.adligo.tests4j.gen.dependency_groups;


public class GenDepGroupContext {
	public String trialPackageName_;
	public Class<?> groupFactoryClass_;
	public String apiVersion_ = "";
	public String factoryMethod = "";
	
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
	public String getFactoryMethod() {
		return factoryMethod;
	}
	public void setFactoryMethod(String factoryMethod) {
		this.factoryMethod = factoryMethod;
	}
	
	
}	
