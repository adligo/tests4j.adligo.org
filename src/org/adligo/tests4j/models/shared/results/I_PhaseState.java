package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface provides information on the 
 * current state of a tests4j run.  This creates
 * isolation of the various counts, and provides a way 
 * to pass it to the display/report layer.  This helps to diagnose
 * process hanging (when a tests4j thread run gets stuck somewhere).
 * 
 * @author scott
 *
 */
public interface I_PhaseState {
	public static final String SETUP = "setup";
	public static final String TRIALS = "trials";
	public static final String REMOTES = "remotes";
	
	/**
	 * the name of the process, see the constants in this class
	 * @return
	 */
	public String getProcessName();
	/**
	 * the actual number of setup threads 
	 * to be created by the processor
	 * @return
	 */
	public int getThreadCount();
	/**
	 * the total number of things that will get done
	 * @return
	 */
	public int getCount();
	/**
	 * the number of things that are done
	 * @return
	 */
	public int getDoneCount();
	/**
	 * The number of runnables that actually started
	 * @return
	 */
	public int getRunnablesStarted();
	/**
	 * The number of runnables that actually finished
	 * @return
	 */
	public int getRunnablesFinished();
	
	/**
	 * if everything has started
	 * @return
	 */
	public boolean hasStartedAll();
	
	/**
	 * if everything has finished everything in 
	 * the phase
	 * @return
	 */
	public boolean hasFinishedAll();
	
	/**
	 * the current list of what is being worked on,
	 * mostly for diagnosis/fixing of process hanging issues.
	 * @return
	 */
	public List<I_Tests4J_TrialProgress> getTrials();
	
	/**
	 * @return the percent done as a integer i.e.
	 * 0.00 - 100.00
	 */
	public double getPercentDone();
	
}
