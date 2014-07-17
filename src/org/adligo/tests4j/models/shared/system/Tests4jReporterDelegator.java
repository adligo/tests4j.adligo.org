package org.adligo.tests4j.models.shared.system;


public class Tests4jReporterDelegator extends TrialRunListenerDelegator implements I_Tests4J_Reporter {
	private I_Tests4J_Reporter delegate;
	
	public Tests4jReporterDelegator(I_Tests4J_Reporter p) {
		super(p,p);
		delegate = p;
	}

	public void log(String p) {
		delegate.log(p);
	}

	public void onError(Throwable p) {
		delegate.onError(p);
	}

	public boolean isLogEnabled(Class<?> clazz) {
		return delegate.isLogEnabled(clazz);
	}

	public boolean isSnare() {
		return delegate.isSnare();
	}

	public boolean isRedirect() {
		return delegate.isRedirect();
	}

	public void setLogOn(Class<?> clazz) {
		delegate.setLogOn(clazz);
	}

	public void setLogOff(Class<?> clazz) {
		delegate.setLogOff(clazz);
	}

	@Override
	public void setListRelevantClassesWithoutTrials(boolean p) {
		delegate.setListRelevantClassesWithoutTrials(p);
	}

}
