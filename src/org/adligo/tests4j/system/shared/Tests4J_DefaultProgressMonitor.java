package org.adligo.tests4j.system.shared;

import org.adligo.tests4j.shared.common.I_System;

public class Tests4J_DefaultProgressMonitor implements I_Tests4J_ProgressMonitor {
	private I_System system;
	private long sleepTime = 250;
	private long setup = 500;
	private long maxSetup = 1000;
	private long trials = 1500;
	private long maxTrials = 3000;
	
	public Tests4J_DefaultProgressMonitor(I_System systemIn) {
		system = systemIn;
	}
	
	@Override
	public long getSleepTime() {
		return sleepTime;
	}

	@Override
	public long getNextNotifyTime(String phase) {
		long now = system.getTime();
		switch (phase) {
			case I_Tests4J_ProgressMonitor.SETUP:
					if (setup < maxSetup) {
						return now + setup++;
					}
					return now + maxSetup;
			case I_Tests4J_ProgressMonitor.TRIALS:
				if (trials < maxTrials) {
					return now + trials++;
				}
				return now + maxTrials;
			default:
		}
		return now + 1000;
	}

	public long getSetup() {
		return setup;
	}

	public long getMaxSetup() {
		return maxSetup;
	}

	public long getTrials() {
		return trials;
	}

	public long getMaxTrials() {
		return maxTrials;
	}

	public void setSleepTime(long sleepTimeIn) {
		if (sleepTimeIn <= 0) {
			throw new IllegalArgumentException("<= 0!");
		}
		this.sleepTime = sleepTimeIn;
	}

	public void setSetup(long setupIn) {
		if (setupIn <= 0) {
			throw new IllegalArgumentException("<= 0!");
		}
		this.setup = setupIn;
	}

	public void setMaxSetup(long maxSetupIn) {
		if (maxSetupIn <= 0) {
			throw new IllegalArgumentException("<= 0!");
		}
		this.maxSetup = maxSetupIn;
	}

	public void setTrials(long trialsIn) {
		if (trialsIn <= 0) {
			throw new IllegalArgumentException("<= 0!");
		}
		this.trials = trialsIn;
	}

	public void setMaxTrials(long maxTrialsIn) {
		if (maxTrialsIn <= 0) {
			throw new IllegalArgumentException("<= 0!");
		}
		this.maxTrials = maxTrialsIn;
	}

}
