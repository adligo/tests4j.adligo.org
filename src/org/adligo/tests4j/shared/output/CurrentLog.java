package org.adligo.tests4j.shared.output;

public class CurrentLog implements I_Tests4J_Log {
	private I_Tests4J_LogObtainer obtainer;
	
	public CurrentLog(I_Tests4J_LogObtainer pObtainer) {
		obtainer = pObtainer;
	}
	
	@Override
	public void log(String p) {
		obtainer.getLog().log(p);
	}

	@Override
	public void onThrowable(Throwable p) {
		obtainer.getLog().onThrowable(p);
	}

	@Override
	public boolean isLogEnabled(Class<?> clazz) {
		return obtainer.getLog().isLogEnabled(clazz);
	}

	@Override
	public boolean isMainLog() {
		return obtainer.getLog().isMainLog();
	}

	@Override
	public String getLineSeperator() {
		return obtainer.getLog().getLineSeperator();
	}

}
