package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

public interface I_Tests4J_CoverageTrialInstrumentation {
	public Class<? extends I_AbstractTrial> getInstrumentedClass();
	/**
	 * may be null only if it is NOT a SourceFileTrial
	 * otherwise it should be here.
	 * @return
	 */
	public I_ClassDependenciesLocal getSourceClassDependencies();
	
}
