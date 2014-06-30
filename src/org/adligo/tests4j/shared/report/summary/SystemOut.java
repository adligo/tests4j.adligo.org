package org.adligo.tests4j.shared.report.summary;

import java.io.PrintStream;

public class SystemOut implements I_LineOut {
	private static final PrintStream OUT = System.out;

	@Override
	public void println(String p) {
		OUT.println(p);
	}
}
