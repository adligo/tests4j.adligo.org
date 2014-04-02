package org.adligo.jtests.run;

import org.adligo.jtests.models.shared.system.I_TrialRunListener;
import org.adligo.jtests.models.shared.system.RunParameters;

public interface I_JTests {

	public abstract void run(RunParameters pParams, I_TrialRunListener pProcessor);
	public abstract void run(RunParameters pParams);

}