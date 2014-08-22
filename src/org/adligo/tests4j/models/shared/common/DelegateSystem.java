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
public class DelegateSystem implements I_System {
	private I_System delegate;
	private final MethodBlocker methodBlocker = getMethodBlocker();
	
	private MethodBlocker getMethodBlocker() {
		List<String> allowedCallers = new ArrayList<String>();
		allowedCallers.add("org.adligo.tests4j.run.Tests4J");
		allowedCallers.add("org.adligo.tests4j_tests.models.shared.common.mocks.ThreadLocalSystemMock");
		
		return new MethodBlocker(DelegateSystem.class, "setDelegate", allowedCallers);
	}
	
	/**
	 * only for mock
	 */
	protected DelegateSystem() {
	}
	
	/**
	 * only for Tests4J_System
	 * @param p
	 */
	protected DelegateSystem(I_System p) {
		delegate = p;
	}
	
	public I_System getDelegate() {
		return delegate;
	}

	public synchronized void setDelegate(I_System pDelegate) {
		methodBlocker.checkAllowed();
		if (pDelegate != null) {
			this.delegate = pDelegate;
		}
	}
	

	public void println(String p) {
		delegate.println(p);
	}

	public void exitJvm(int p) {
		delegate.exitJvm(p);
	}

	public long getTime() {
		return delegate.getTime();
	}

	public String getLineSeperator() {
		return delegate.getLineSeperator();
	}

	@Override
	public String getCurrentThreadName() {
		return delegate.getCurrentThreadName();
	}

	@Override
	public PrintStream getOut() {
		return delegate.getOut();
	}
}
