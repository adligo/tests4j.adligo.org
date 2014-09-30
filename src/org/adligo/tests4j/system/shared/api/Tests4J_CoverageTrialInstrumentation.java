package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;

public class Tests4J_CoverageTrialInstrumentation implements I_Tests4J_CoverageTrialInstrumentation {
	private Class<? extends I_AbstractTrial> instrumentedClass;
	private I_ClassDependenciesLocal classDependencies;
	
	public Tests4J_CoverageTrialInstrumentation(Class<? extends I_AbstractTrial> instrumentedClassIn, 
			I_ClassDependenciesLocal classDependenciesIn) {
		instrumentedClass = instrumentedClassIn;
		classDependencies = classDependenciesIn;
	}
	
	public Class<? extends I_AbstractTrial> getInstrumentedClass() {
		return instrumentedClass;
	}
	public I_ClassDependenciesLocal getSourceClassDependencies() {
		return classDependencies;
	}
	
}
