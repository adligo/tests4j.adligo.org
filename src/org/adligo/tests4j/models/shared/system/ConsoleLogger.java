package org.adligo.tests4j.models.shared.system;

import java.io.PrintStream;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;

public class ConsoleLogger implements I_Tests4J_Logger {
	private static PrintStream OUT = System.out;
	private boolean enabled = true;
	private String prefix = "Tests4J: ";
	
	public ConsoleLogger(boolean pEnabled) {
		enabled = pEnabled;
	}
	
	public ConsoleLogger(String pPrefix, boolean pEnabled) {
		enabled = pEnabled;
		prefix = pPrefix;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void log(String p) {
		if (!enabled) {
			return;
		}
		OUT.println(prefix + p);
	}

	@Override
	public PrintStream getOutput() {
		return OUT;
	}

	@Override
	public void log(Throwable p) {
		if (!enabled) {
			return;
		}
		p.printStackTrace(OUT);
		Throwable cause = p.getCause();
		while (cause != null) {
			cause.printStackTrace(OUT);
			cause = cause.getCause();
		}
	}

	
}
