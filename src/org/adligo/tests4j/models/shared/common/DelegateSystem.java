package org.adligo.tests4j.models.shared.common;

import java.util.Collections;

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

	protected DelegateSystem() {
		
	}
	protected DelegateSystem(I_System p) {
		delegate = p;
	}
	
	public I_System getDelegate() {
		return delegate;
	}

	public void setDelegate(I_System delegate) {
		new MethodBlocker(DelegateSystem.class, "setDelegate", 
				Collections.singleton("org.adligo.tests4j_tests.models.shared.common.mocks.ThreadlocalSystemMock"));
		this.delegate = delegate;
	}

	public void println(String p) {
		delegate.println(p);
	}

	public void doSystemExit(int p) {
		delegate.doSystemExit(p);
	}

	public long getTime() {
		return delegate.getTime();
	}

	public String getLineSeperator() {
		return delegate.getLineSeperator();
	}
}
