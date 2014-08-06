package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.common.I_System;

public interface I_Tests4J_Delegate {
	/**
	 * 
	 * @param listener
	 * @param params
	 * 
	 * @return if the setup method has determined, that a run() can occur.
	 */
	public boolean setup(I_Tests4J_Listener listener, I_Tests4J_Params params);
	public void run();
	public I_Tests4J_Controls getControls();
	
	public void setSystem(I_System system);
}
