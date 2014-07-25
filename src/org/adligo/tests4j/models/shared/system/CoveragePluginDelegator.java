package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

public class CoveragePluginDelegator implements I_Tests4J_CoveragePlugin {
	public static final String COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_TESTS4J_REPORTER = "CoveragePluginDelegate requires a I_Tests4J_Reporter.";
	public static final String COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_COVERAGE_PLUGIN = "CoveragePluginDelegate requires a I_CoveragePlugin.";
	private I_Tests4J_CoveragePlugin delegate;
	private I_Tests4J_Logger reporter;
	
	public CoveragePluginDelegator(I_Tests4J_CoveragePlugin p, I_Tests4J_Logger pReporter) {
		if (p == null) {
			throw new IllegalArgumentException(COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_COVERAGE_PLUGIN);
		}
		delegate = p;
		
		if (pReporter == null) {
			throw new IllegalArgumentException(COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_TESTS4J_REPORTER);
		}
		reporter = pReporter;
	}
	
	@Override
	public Class<? extends I_AbstractTrial> instrument(
			Class<? extends I_AbstractTrial> trial) {
		try {
			return delegate.instrument(trial);
		} catch (Throwable t) {
			reporter.onException(t);
		}
		return null;
	}

	public boolean canThreadGroupLocalRecord() {
		try {
			return delegate.canThreadGroupLocalRecord();
		} catch (Throwable t) {
			reporter.onException(t);
		}
		return false;
	}

	public I_Tests4J_CoverageRecorder createRecorder() {
		try {
			return delegate.createRecorder();
		} catch (Throwable t) {
			reporter.onException(t);
		}
		return null;
	}

	
	
}
