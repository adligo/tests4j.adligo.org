package org.adligo.tests4j.models.shared.common;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * a tricky way to have a constant,
 * that allows it to be swapped out 
 * by a trial, on the trial and test threads.
 * 
 * @author scott
 *
 */
public class SystemWithPrintStreamDelegate implements I_System {
	private I_System delegate;
	private PrintStream out;
	/**
	 * only for Tests4J_System
	 * @param p
	 */
	public SystemWithPrintStreamDelegate(I_System p, PrintStream pOut) {
		delegate = p;
		out = pOut;
	}
	
	public I_System getDelegate() {
		return delegate;
	}

	public void println(String p) {
		out.println(p);
	}

	public void exitJvm(int p) {
		delegate.exitJvm(p);
	}

	public long getTime() {
		return delegate.getTime();
	}

	public String lineSeperator() {
		return delegate.lineSeperator();
	}

	@Override
	public String getCurrentThreadName() {
		return delegate.getCurrentThreadName();
	}

	@Override
	public PrintStream getOut() {
		return out;
	}

	@Override
	public String getJseVersion() {
		return delegate.getJseVersion();
	}
}
