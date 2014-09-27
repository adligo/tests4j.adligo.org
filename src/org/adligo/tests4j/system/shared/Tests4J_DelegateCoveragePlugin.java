package org.adligo.tests4j.system.shared;

import java.io.IOException;

import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * this class protects tests4j from unexpected exceptions
 * that could occur in the plug-in, so that it has known
 * reactions to exceptions and errors.
 * @author scott
 *
 */
public class Tests4J_DelegateCoveragePlugin implements I_Tests4J_CoveragePlugin {
	public static final String COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_TESTS4J_REPORTER = "CoveragePluginDelegate requires a I_Tests4J_Reporter.";
	public static final String COVERAGE_PLUGIN_DELEGATE_REQUIRES_A_I_COVERAGE_PLUGIN = "CoveragePluginDelegate requires a I_CoveragePlugin.";
	private I_Tests4J_CoveragePlugin delegate;
	private I_Tests4J_Log reporter;
	
	public Tests4J_DelegateCoveragePlugin(I_Tests4J_CoveragePlugin p, I_Tests4J_Log pReporter) {
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
	public I_Tests4J_CoverageTrialInstrumentation instrument(
			Class<? extends I_AbstractTrial> trial)  throws IOException {
		try {
			return delegate.instrument(trial);
		} catch (IOException x) {
			throw x;
		} catch (Exception t) {
			reporter.onThrowable(t);
		}
		return null;
	}

	public boolean isCanThreadGroupLocalRecord() {
		try {
			return delegate.isCanThreadGroupLocalRecord();
		} catch (Exception t) {
			reporter.onThrowable(t);
		}
		return false;
	}

	public I_Tests4J_CoverageRecorder createRecorder() {
		try {
			return delegate.createRecorder();
		} catch (Exception t) {
			reporter.onThrowable(t);
		}
		return null;
	}

	public void instrumentationComplete() {
		try {
			delegate.instrumentationComplete();
		} catch (Exception t) {
			reporter.onThrowable(t);
		}
	}

	@Override
	public double getInstrumentProgress(Class<? extends I_AbstractTrial> trial) {
		try {
			return delegate.getInstrumentProgress(trial);
		} catch (Exception t) {
			reporter.onThrowable(t);
		}
		return 0.0;
	}

}
