package org.adligo.tests4j.models.shared.system.console;

import java.io.PrintStream;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

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
		OUT.println(prefix + p);
	}

	@Override
	public PrintStream getOutput() {
		return OUT;
	}

	
}
