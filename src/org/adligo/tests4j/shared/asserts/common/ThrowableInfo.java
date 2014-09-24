package org.adligo.tests4j.shared.asserts.common;


public class ThrowableInfo  implements I_ThrowableInfo {
	private ThrowableInfoMutant mutant;
	
	public ThrowableInfo() {
		mutant = new ThrowableInfoMutant();
	}
	
	public ThrowableInfo(I_ThrowableInfo p) {
		mutant = new ThrowableInfoMutant(p);
		I_ThrowableInfo cause = p.getCause();
		if (cause != null) {
			ThrowableInfo immutable = new ThrowableInfo(cause);
			mutant.setCause(immutable);
		}
	}

	public ThrowableInfo(I_ExpectedThrownData p) {
		mutant = new ThrowableInfoMutant(p);
	}
	
	public ThrowableInfo(Throwable p) {
		mutant = new ThrowableInfoMutant(p);
	}
	
	public String getClassName() {
		return mutant.getClassName();
	}

	public String getMessage() {
		return mutant.getMessage();
	}

	public String getStacktrace() {
		return mutant.getStacktrace();
	}

	public I_ThrowableInfo getCause() {
		return mutant.getCause();
	}
}
