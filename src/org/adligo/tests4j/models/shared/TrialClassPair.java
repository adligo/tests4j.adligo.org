package org.adligo.tests4j.models.shared;

public class TrialClassPair {
	/**
	 * this is the trial class to be run,
	 * which may have been instrumented by the 
	 * I_CoveragePlugin.
	 * 
	 */
	private Class<? extends AbstractTrial> trialClass;
	/**
	 * this provides runtime support
	 * so that the coverage plugin 
	 * can map back the instrumented java byte code
	 * stack to the original java byte code
	 * 
	 * may be null if the was no instrumentation
	 */
	private Class<? extends AbstractTrial> nonInstrumentedTrialClass;
	
	public TrialClassPair(Class<? extends AbstractTrial> pTrialClass, 
			Class<? extends AbstractTrial> pNonInstrumentedTrialClass) {
		trialClass = pTrialClass;
		nonInstrumentedTrialClass = pNonInstrumentedTrialClass;
	}

	public Class<? extends AbstractTrial> getTrialClass() {
		return trialClass;
	}

	public Class<? extends AbstractTrial> getNonInstrumentedTrialClass() {
		return nonInstrumentedTrialClass;
	}
	
	
}
