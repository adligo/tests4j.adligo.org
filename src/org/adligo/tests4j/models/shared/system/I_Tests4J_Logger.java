package org.adligo.tests4j.models.shared.system;

import java.io.PrintStream;

public interface I_Tests4J_Logger {
	public boolean isEnabled();
	public void log(String p);
	public PrintStream getOutput();
}
