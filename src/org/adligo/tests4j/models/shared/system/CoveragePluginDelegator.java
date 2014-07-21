package org.adligo.tests4j.models.shared.system;

import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

public class CoveragePluginDelegator implements I_CoveragePlugin {
	public static final String COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_TESTS4J_REPORTER = "CoveragePluginDelegate requires a I_Tests4J_Reporter.";
	public static final String COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_COVERAGE_PLUGIN = "CoveragePluginDelegate requires a I_CoveragePlugin.";
	private I_CoveragePlugin delegate;
	private I_Tests4J_Logger reporter;
	
	public CoveragePluginDelegator(I_CoveragePlugin p, I_Tests4J_Logger pReporter) {
		if (p == null) {
			throw new IllegalArgumentException(COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_COVERAGE_PLUGIN);
		}
		delegate = p;
		
		if (pReporter == null) {
			throw new IllegalArgumentException(COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_TESTS4J_REPORTER);
		}
		reporter = pReporter;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends I_AbstractTrial>> instrumentClasses(
			List<Class<? extends I_AbstractTrial>> trials) {
		try {
			return delegate.instrumentClasses(trials);
		} catch (Throwable t) {
			reporter.onError(t);
		}
		return (List<Class<? extends I_AbstractTrial>>) Collections.EMPTY_LIST;
	}

	public boolean canThreadGroupLocalRecord() {
		try {
			return delegate.canThreadGroupLocalRecord();
		} catch (Throwable t) {
			reporter.onError(t);
		}
		return false;
	}

	public I_CoverageRecorder createRecorder() {
		try {
			return delegate.createRecorder();
		} catch (Throwable t) {
			reporter.onError(t);
		}
		return null;
	}

	
	
}
