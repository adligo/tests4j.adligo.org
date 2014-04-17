package org.adligo.tests4j.run;

import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;

public interface I_Tests4J {

	public abstract void run(Tests4J_Params pParams, I_TrialRunListener pProcessor);
	public abstract void run(Tests4J_Params pParams);

}