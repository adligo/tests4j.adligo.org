package org.adligo.tests4j.models.shared.system;

import java.io.PrintStream;

/**
 * just stubbing for the default System library,
 * to make testing easy with out dependencies.
 * 
 * @author scott
 *
 */
public class DefaultSystem implements I_Tests4J_System {
	private static final PrintStream OUT = System.out;

	@Override
	public void println(String p) {
		OUT.println(p);
	}

	@Override
	public void doSystemExit(int p) {
		System.exit(p);
	}

	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}
}
