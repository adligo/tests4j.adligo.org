package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.system.I_Tests4J_ProcessInfo;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Runnable;
import org.adligo.tests4j.models.shared.system.I_Tests4J_TrialProgress;

public class Tests4J_ProcessInfo implements I_Tests4J_ProcessInfo {
	private String processName;
	private int threadCount;
	private int count;
	private AtomicInteger runnablesStarted = new AtomicInteger(0);
	private AtomicInteger done = new AtomicInteger(0);
	private AtomicInteger runnablesFinished = new AtomicInteger(0);
	private CopyOnWriteArrayList<I_Tests4J_Runnable> runnables = new CopyOnWriteArrayList<I_Tests4J_Runnable>();
	
	public Tests4J_ProcessInfo(String pProcessName, int pThreadCount, int pCount) {
		processName = pProcessName;
		threadCount = pThreadCount;
		count = pCount;
	}
	
	@Override
	public synchronized String getProcessName() {
		return processName;
	}

	@Override
	public synchronized int getThreadCount() {
		return threadCount;
	}

	@Override
	public synchronized int getCount() {
		return count;
	}

	@Override
	public synchronized int getDone() {
		return done.get();
	}

	@Override
	public synchronized int getRunnablesStarted() {
		return runnablesStarted.get();
	}

	@Override
	public synchronized int getRunnablesFinished() {
		return runnablesFinished.get();
	}

	public synchronized void addDone() {
		done.addAndGet(1);
	}

	public synchronized void addRunnableStarted() {
		runnablesStarted.addAndGet(1);
	}

	public synchronized void addRunnableFinished() {
		runnablesFinished.addAndGet(1);
	}

	@Override
	public synchronized boolean hasStartedAll() {
		if (count == done.get()) {
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean hasFinishedAll() {
		if (hasStartedAll()) {
			if (runnablesStarted.get() == runnablesFinished.get()) {
				return true;
			}
		}
		return false;
	}
	
	public synchronized void addRunnable(I_Tests4J_Runnable p) {
		runnables.add(p);
	}

	@Override
	public synchronized List<I_Tests4J_TrialProgress> getTrials() {
		List<I_Tests4J_TrialProgress> toRet = new ArrayList<I_Tests4J_TrialProgress>();
		for (I_Tests4J_Runnable runnable: runnables) {
			I_Tests4J_TrialProgress state = runnable.getTrial();
			if ( state != null) {
				toRet.add(state);
			}
		}
		
		return toRet;
	}

	@Override
	public synchronized double getPercentDone() {
		double doneD = done.get();
		double countD = count;
		return doneD/countD * 100;
	}
}
