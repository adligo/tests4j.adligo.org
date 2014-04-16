package org.adligo.jtests.run;

import org.adligo.jtests.models.shared.system.I_TrialRunListener;
import org.adligo.jtests.models.shared.system.JTestParameters;

public interface I_JTests {

	public abstract void run(JTestParameters pParams, I_TrialRunListener pProcessor);
	public abstract void run(JTestParameters pParams);

}