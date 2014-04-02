package org.adligo.jtests.run;

import org.adligo.jtests.models.shared.system.I_TestRunListener;
import org.adligo.jtests.models.shared.system.RunParameters;

public interface I_JTests {

	public abstract void run(RunParameters pParams, I_TestRunListener pProcessor);

	public abstract void run(RunParameters pParams);

}