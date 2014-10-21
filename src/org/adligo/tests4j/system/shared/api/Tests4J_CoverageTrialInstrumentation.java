package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.models.shared.association.I_ClassAssociationsLocal;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;

public class Tests4J_CoverageTrialInstrumentation implements I_Tests4J_CoverageTrialInstrumentation {
	private Class<? extends I_AbstractTrial> instrumentedClass;
	private I_ClassAssociationsLocal classDependencies;
	
	public Tests4J_CoverageTrialInstrumentation(Class<? extends I_AbstractTrial> instrumentedClassIn, 
			I_ClassAssociationsLocal classDependenciesIn) {
		instrumentedClass = instrumentedClassIn;
		classDependencies = classDependenciesIn;
	}
	
	public Class<? extends I_AbstractTrial> getInstrumentedClass() {
		return instrumentedClass;
	}
	public I_ClassAssociationsLocal getSourceClassDependencies() {
		return classDependencies;
	}
	
}
