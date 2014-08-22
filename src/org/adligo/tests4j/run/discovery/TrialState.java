package org.adligo.tests4j.run.discovery;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

/**
 * @diagram_sync with Tests4J_TrialStates.state on 
 * @author scott
 *
 */
public class TrialState implements I_TrialStateNameIdKey {
	private final String trialName;
	private final Class<? extends I_AbstractTrial> trialClass;
	private final TrialStateNameIdKey key;
	private AtomicInteger id = new AtomicInteger();
	private TrialDescription desc;
	private AtomicBoolean approvedForInstrumentation = new AtomicBoolean(false);
	private AtomicBoolean approvedForRun = new AtomicBoolean(false);
	private AtomicBoolean readyForRun = new AtomicBoolean(false);
	private AtomicBoolean finishedRun = new AtomicBoolean(false);
	
	public TrialState(String trialNameIn, Class<? extends I_AbstractTrial> trialClassIn) {
		trialName = trialNameIn;
		trialClass = trialClassIn;
		key = new TrialStateNameIdKey(getTrialName(), getId());
	}
	
	public TrialDescription getTrialDescription() {
		return desc;
	}
	
	public synchronized void setDescApprovedForInstrumentation(TrialDescription p) {
		desc = p;
		approvedForInstrumentation.set(true);
	}
	
	public synchronized void setDescApprovedForRun(TrialDescription p) {
		desc = p;
		approvedForRun.set(true);
	}
	
	/**
	 * this method should always be called, even when
	 * the TrialDescription isRunnable() = false, it makes sure 
	 * everything is ready to run in a easier manner.
	 * 
	 * @param p
	 */
	public synchronized void setReadyToRun(TrialDescription p) {
		desc = p;
		readyForRun.set(true);
	}
	
	public synchronized void setFinishedRun() {
		finishedRun.set(true);
	}

	protected synchronized boolean getApprovedForInstrumentation() {
		return approvedForInstrumentation.get();
	}

	protected synchronized boolean getApprovedForRun() {
		return approvedForRun.get();
	}

	protected synchronized boolean getReadyForRun() {
		return readyForRun.get();
	}

	protected synchronized boolean getFinishedRun() {
		return finishedRun.get();
	}

	public String getTrialName() {
		return trialName;
	}

	public int getId() {
		return id.get();
	}

	protected synchronized void setId(int pid) {
		if (id.get() != 0) {
			throw new IllegalStateException("The id can only be set once!");
		}
		id.set(pid);
	}

	public Class<? extends I_AbstractTrial> getTrialClass() {
		return trialClass;
	}

	public TrialStateNameIdKey getKey() {
		return key;
	}
	
	public int hashCode() {
		return key.hashCode();
	}
	
	public boolean equals(Object other) {
		try {
			TrialState otherState = (TrialState) other;
			if (key.equals(otherState.key)) {
				return true;
			}
		} catch (ClassCastException x) {
			//do nothing
		}
		return true;
	}
}
