package org.adligo.tests4j.models.shared.system;


public interface I_CoveragePlugin {
	/**
	 * this instruments the classes so that
	 * they notify the recorder
	 */
	public void instrumentClasses();
	
	public I_CoverageRecorder createRecorder(String scope);
}