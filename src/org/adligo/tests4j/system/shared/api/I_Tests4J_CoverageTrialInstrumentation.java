package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.models.shared.association.I_ClassAssociationsLocal;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;

public interface I_Tests4J_CoverageTrialInstrumentation {
	public Class<? extends I_AbstractTrial> getInstrumentedClass();
	/**
	 * may be null only if it is NOT a SourceFileTrial
	 * otherwise it should be here.
	 * @return
	 */
	public I_ClassAssociationsLocal getSourceClassDependencies();
	
}
