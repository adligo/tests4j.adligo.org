package org.adligo.tests4j.shared.asserts.common;


public class ThrowableInfo  implements I_ThrowableInfo {
	private ThrowableInfoMutant mutant_;
	
	public ThrowableInfo() {
		mutant_ = new ThrowableInfoMutant();
	}
	
	public ThrowableInfo(I_ThrowableInfo p) {
		mutant_ = new ThrowableInfoMutant(p);
		I_ThrowableInfo cause = p.getCause();
		if (cause != null) {
			ThrowableInfo immutable = new ThrowableInfo(cause);
			mutant_.setCause(immutable);
		}
	}

	public ThrowableInfo(I_ExpectedThrowable p) {
		mutant_ = new ThrowableInfoMutant(p);
	}
	
	public ThrowableInfo(Throwable p) {
		mutant_ = new ThrowableInfoMutant(p);
	}
	
	public String getClassName() {
		return mutant_.getClassName();
	}

	public String getMessage() {
		return mutant_.getMessage();
	}

	public String getStacktrace() {
		return mutant_.getStacktrace();
	}

	public I_ThrowableInfo getCause() {
		return mutant_.getCause();
	}

  @Override
  public I_MatchType getMatchType() {
    return mutant_.getMatchType();
  }
}
