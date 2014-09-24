package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.shared.common.I_System;

public interface I_Tests4J_Delegate {
	/**
	 * 
	 * @param listener
	 * @param params
	 * 
	 * @return if the setup method has determined, that a run() can occur.
	 */
	public boolean setup(I_Tests4J_Listener listener, I_Tests4J_Params params);
	/**
	 * @diagram_sync with Overview.seq on 8/20/2014
	 * 
	 * if the java environment run this 
	 * on another thread, so that the controls will 
	 * kill it real fast.
	 */
	public void runOnAnotherThreadIfAble();
	public I_Tests4J_Controls getControls();
}
