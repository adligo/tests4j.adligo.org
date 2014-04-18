package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.I_AbstractTrial;


public interface I_CoveragePlugin {
	/**
	 * this instruments the classes so that
	 * they notify the recorder
	 */
	public List<Class<? extends I_AbstractTrial>> instrumentClasses(I_Tests4J_Params params);
	
	public I_CoverageRecorder createRecorder(String scope);
}
