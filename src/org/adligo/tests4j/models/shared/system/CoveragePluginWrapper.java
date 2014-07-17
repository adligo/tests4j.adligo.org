package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

public class CoveragePluginWrapper implements I_CoveragePlugin {
	private I_CoveragePlugin delegate;

	public CoveragePluginWrapper(I_CoveragePlugin p) {
		delegate = p;
	}
	
	@Override
	public List<Class<? extends I_AbstractTrial>> instrumentClasses(
			List<Class<? extends I_AbstractTrial>> trials) {
		return delegate.instrumentClasses(trials);
	}

	public boolean canThreadGroupLocalRecord() {
		return delegate.canThreadGroupLocalRecord();
	}

	public I_CoverageRecorder createRecorder() {
		return delegate.createRecorder();
	}

	@Override
	public void setReporter(I_Tests4J_Reporter p) {
		delegate.setReporter(p);
	}

	
	
}
