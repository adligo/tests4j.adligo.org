package org.adligo.tests4j.shared.output;

public class CurrentLog implements I_Tests4J_Log {
	private I_Tests4J_LogObtainer obtainer;
	
	public CurrentLog(I_Tests4J_LogObtainer pObtainer) {
		obtainer = pObtainer;
	}
	
	@Override
	public void log(String p) {
		I_Tests4J_Log log = obtainer.getLog();
		log.log(p);
	}

	@Override
	public void onThrowable(Throwable p) {
		I_Tests4J_Log log = obtainer.getLog();
		log.onThrowable(p);
	}

	@Override
	public boolean isLogEnabled(Class<?> clazz) {
		I_Tests4J_Log log = obtainer.getLog();
		return log.isLogEnabled(clazz);
	}

	@Override
	public boolean isMainLog() {
		I_Tests4J_Log log = obtainer.getLog();
		return log.isMainLog();
	}

	@Override
	public String getLineSeperator() {
		I_Tests4J_Log log = obtainer.getLog();
		return log.getLineSeperator();
	}

}
