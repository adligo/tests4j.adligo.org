package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_Trial;

public class CoveragePluginWrapper implements I_CoveragePlugin {
	private I_CoveragePlugin delegate;

	public CoveragePluginWrapper(I_CoveragePlugin p) {
		delegate = p;
	}
	
	@Override
	public synchronized List<Class<? extends I_AbstractTrial>> instrumentClasses(
			List<Class<? extends I_AbstractTrial>> trials) {
		return delegate.instrumentClasses(trials);
	}

	public synchronized boolean canSubRecord() {
		return delegate.canSubRecord();
	}

	public synchronized I_CoverageRecorder createRecorder(String scope) {
		return delegate.createRecorder(scope);
	}

	@Override
	public synchronized void setReporter(I_Tests4J_Reporter p) {
		delegate.setReporter(p);
	}

	
	
}
