package org.adligo.tests4j.run;

import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.JTestParameters;

public interface I_JTests {

	public abstract void run(JTestParameters pParams, I_TrialRunListener pProcessor);
	public abstract void run(JTestParameters pParams);

}