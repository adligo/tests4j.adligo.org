package org.adligo.tests4j.system.shared;

import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class Tests4J_DelegateProgressMonitor implements I_Tests4J_ProgressMonitor {
	private I_Tests4J_ProgressMonitor delegate;
	private I_Tests4J_Log log;
	private long cachedSleepTime = 250;
	private long cachedSetupTime = 500;
	private long cachedTrialsTime = 1500;
	private long cachedRemotesTime = 1000;
	
	public Tests4J_DelegateProgressMonitor(I_Tests4J_Log logIn, I_Tests4J_ProgressMonitor delegateIn) {
		log = logIn;
		delegate = delegateIn;
	}
	
	@Override
	public long getSleepTime() {
		try {
			cachedSleepTime = delegate.getSleepTime();
		} catch (Exception e) {
			log.onThrowable(e);
		}
		return cachedSleepTime;
	}

	@Override
	public long getNextNotifyTime(String phase) {
		
		try {
			switch (phase) {
				case I_Tests4J_ProgressMonitor.SETUP:
					cachedSetupTime = delegate.getNextNotifyTime(phase);
					return cachedSetupTime;
				case I_Tests4J_ProgressMonitor.TRIALS:
					cachedTrialsTime = delegate.getNextNotifyTime(phase);
					return cachedTrialsTime;
				case I_Tests4J_ProgressMonitor.REMOTES:
					cachedRemotesTime = delegate.getNextNotifyTime(phase);
					return cachedRemotesTime;
			}
		} catch (Exception e) {
			log.onThrowable(e);
		}
		switch (phase) {
			case I_Tests4J_ProgressMonitor.SETUP:
				return cachedSetupTime;
			case I_Tests4J_ProgressMonitor.TRIALS:
				return cachedTrialsTime;
			default:
		}
		return cachedRemotesTime;
	}

}
