package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.common.I_System;

public class JseSystem implements I_System {
	@Override
	public void println(String p) {
		System.out.println(p);
	}

	@Override
	public void exitJvm(int p) {
		System.exit(p);
	}

	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public String getLineSeperator() {
		return System.lineSeparator();
	}

	@Override
	public String getCurrentThreadName() {
		return Thread.currentThread().getName();
	}

}
