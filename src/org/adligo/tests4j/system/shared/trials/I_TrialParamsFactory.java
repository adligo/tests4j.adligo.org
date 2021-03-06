package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.shared.xml.I_XML_Producer;

public interface I_TrialParamsFactory extends I_XML_Producer {
	/**
	 * get the next parameters for your trial
	 * @param trialInstanceName includes which one
	 *  with a [x] suffix.
	 *  
	 * @return
	 */
	public I_TrialParams next(String trialInstanceName);
}
