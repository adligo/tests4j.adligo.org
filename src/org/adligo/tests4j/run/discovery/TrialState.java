package org.adligo.tests4j.run.discovery;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.adligo.tests4j.models.shared.system.I_Tests4J_ProcessInfo;
import org.adligo.tests4j.models.shared.system.I_Tests4J_TrialProgress;
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
	private AtomicInteger tests = new AtomicInteger(0);
	private AtomicInteger testCompleted = new AtomicInteger(0);
	
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

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.discovery.I_TrialState#getTrialName()
	 */
	@Override
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

	public synchronized int getTests() {
		return tests.get();
	}

	public synchronized void setTests(int p) {
		tests.set(p);
	}
	
	public synchronized void addTestCompleted() {
		testCompleted.addAndGet(1);
	}
	
	public double getPctDone() {
		double total = tests.get();
		double done = testCompleted.get();
		if (total <= 0.0) {
			return 0.0;
		}
		return done/total * 100.0;
	}
}
