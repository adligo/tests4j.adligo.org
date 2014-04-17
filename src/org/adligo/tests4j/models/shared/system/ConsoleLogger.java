package org.adligo.tests4j.models.shared.system;

import java.io.PrintStream;

public class ConsoleLogger implements I_Tests4J_Logger {
	private static PrintStream OUT = System.out;
	private boolean enabled = true;
	
	public ConsoleLogger(boolean pEnabled) {
		enabled = pEnabled;
	}
	
	@Override
	public boolean enabled() {
		return enabled;
	}

	@Override
	public void log(String p) {
		OUT.println("Tests4J: " + p);
	}

}
