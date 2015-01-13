package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;

/**
 * This class provides a way to swap 
 * out most of the Test4j run,
 * so that mock running of Tests4j can be accomplished.
 * 
 * @diagram_sync on 1/8/2014 with Overview.seq
 *    Tests4J_Processor implements I_Tests4J_Delegate
 * @author scott
 *
 */
public interface I_Tests4J_Delegate {
	/**
	 * @diagram_sync on 1/8/2014 with Overview.seq
	 * @param listener
	 * @param params
	 * 
	 * @return if the setup method has determined, that a run() can occur.
	 */
	public boolean setup(I_Tests4J_Listener listener, I_Tests4J_Params params);
	/**
	 * @diagram_sync on 1/8/2014 with Overview.seq
	 * 
	 * if the java environment run this 
	 * on another thread, so that the controls will 
	 * kill it real fast.
	 */
	public void runOnAnotherThreadIfAble();
	/**
	 * @diagram_sync on 1/8/2014 with Overview.seq
	 * @return
	 */
	public I_Tests4J_Controls getControls();
}
