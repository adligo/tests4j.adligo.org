package org.adligo.tests4j.models.shared.system.report;

import org.adligo.tests4j.models.shared.system.TrialRunListenerDelegate;

public class Tests4jReporterDelegate extends TrialRunListenerDelegate implements I_Tests4J_Reporter {
	private I_Tests4J_Reporter delegate;
	
	public Tests4jReporterDelegate(I_Tests4J_Reporter p) {
		super(p);
		delegate = p;
	}

	public synchronized void log(String p) {
		delegate.log(p);
	}

	public synchronized void onError(Throwable p) {
		delegate.onError(p);
	}

	public synchronized boolean isLogEnabled(Class<?> clazz) {
		return delegate.isLogEnabled(clazz);
	}

	public synchronized boolean isSnare() {
		return delegate.isSnare();
	}

	public synchronized boolean isRedirect() {
		return delegate.isRedirect();
	}

	public void setLogOn(Class<?> clazz) {
		delegate.setLogOn(clazz);
	}

	public void setLogOff(Class<?> clazz) {
		delegate.setLogOff(clazz);
	}

}
