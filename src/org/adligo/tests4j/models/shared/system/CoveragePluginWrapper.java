package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.I_Trial;

public class CoveragePluginWrapper implements I_CoveragePlugin {
	private I_CoveragePlugin delegate;

	public CoveragePluginWrapper(I_CoveragePlugin p) {
		delegate = p;
	}
	
	public List<Class<? extends I_Trial>> instrumentClasses(
			I_Tests4J_Params params) {
		return delegate.instrumentClasses(params);
	}

	public boolean canSubRecord() {
		return delegate.canSubRecord();
	}

	public I_CoverageRecorder createRecorder(String scope) {
		return new SynchronizedCoverageRecorder(delegate.createRecorder(scope));
	}
	
}
