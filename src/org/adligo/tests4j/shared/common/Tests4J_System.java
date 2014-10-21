package org.adligo.tests4j.shared.common;

import java.io.PrintStream;


public class Tests4J_System {
	public static final I_System SYSTEM = new DelegateSystem(new DefaultSystem());

	public static void println(String p) {
		SYSTEM.println(p);
	}

	public static void exitJvm(int p) {
		SYSTEM.exitJvm(p);
	}

	public static long getTime() {
		return SYSTEM.getTime();
	}

	public static String lineSeperator() {
		return SYSTEM.lineSeperator();
	}

	public static String getCurrentThreadName() {
		return SYSTEM.getCurrentThreadName();
	}

	public static String getJseVersion() {
		return SYSTEM.getJseVersion();
	}

	public static PrintStream getOut() {
		return SYSTEM.getOut();
	}

	public static boolean isMainSystem() {
		return SYSTEM.isMainSystem();
	}

}
