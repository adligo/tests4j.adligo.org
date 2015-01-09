package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.PhaseState;
import org.adligo.tests4j.models.shared.results.PhaseStateMutant;
import org.adligo.tests4j.shared.common.I_Time;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Runnable;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
 * @author scott
 *
 */
public class Tests4J_PhaseOverseer {
	private String processName_;
	private int threadCount_;
	private int count_;
	
	private AtomicInteger runnablesStarted_ = new AtomicInteger(0);
	private AtomicInteger doneCount_ = new AtomicInteger(0);
	private AtomicInteger runnablesFinished_ = new AtomicInteger(0);
	private AtomicLong lastNotificationTime_ = new AtomicLong(0);
	private final long notificationInterval_;
	private final I_Time time_;
	private final CopyOnWriteArrayList<I_Tests4J_Runnable> runnables_ = new CopyOnWriteArrayList<I_Tests4J_Runnable>();
	private final AtomicReference<I_PhaseState> lastPhaseState_ = new AtomicReference<I_PhaseState>();
	/**
	 * this should never grow more than three or so
	 * but 100 is just incase
	 */
	private final ArrayBlockingQueue<I_PhaseState> nextPhaseStates_ = new ArrayBlockingQueue<I_PhaseState>(100);
	private final AtomicBoolean countDoneCountMatch_ = new AtomicBoolean(false);
	private Semaphore phaseStateSemaphor_ = new Semaphore(1);
	
	public Tests4J_PhaseOverseer(Tests4J_PhaseInfoParamsMutant in) {
		processName_ = in.getProcessName();
		threadCount_ = in.getThreadCount();
		count_ = in.getCount();
		notificationInterval_ = in.getNotificationInterval();
		time_ = in.getTime();
	}
	

	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	public void addDone() {
		doneCount_.incrementAndGet();
		if (doneCount_.get() == count_) {
      countDoneCountMatch_.set(true);
      addPhaseState();
    } else {
    
  		long now = time_.getTime();
  		if (lastNotificationTime_.get() + notificationInterval_ < now ) {
  		  boolean acquired = false;
  		  try {
  		    phaseStateSemaphor_.acquire();
  		    acquired = true;
  		    lastNotificationTime_.set(now);
  		    addPhaseState();
  		  } catch (InterruptedException x) {
  		    //do nothing several threads may try to obtain the single semaphore lock
  		  } finally {
  		    if (acquired) {
  		      phaseStateSemaphor_.release();
  		    }
  		  }
  		}
		}
		
	}

	public void addRunnableStarted() {
		runnablesStarted_.incrementAndGet();
	}

	public void addRunnableFinished() {
		runnablesFinished_.incrementAndGet();
	}

	 public boolean isCountDoneCountMatch() {
	    return countDoneCountMatch_.get();
	  }
	 
	private boolean hasStartedAll() {
		if (count_ == doneCount_.get()) {
			return true;
		}
		return false;
	}

	private boolean hasFinishedAll() {
		if (hasStartedAll()) {
			if (runnablesStarted_.get() == runnablesFinished_.get()) {
				return true;
			}
		}
		return false;
	}
	
	public void addRunnable(I_Tests4J_Runnable p) {
		runnables_.add(p);
	}

	public I_PhaseState pollPhaseState() throws InterruptedException {
    I_PhaseState next = nextPhaseStates_.poll(notificationInterval_, TimeUnit.MILLISECONDS);
    if (next != null) {
      lastPhaseState_.set(next);
      return next;
    }
    return lastPhaseState_.get();
	}
	
	public synchronized I_PhaseState getIntialPhaseState() {
    PhaseStateMutant psm = new PhaseStateMutant();
    psm.setCount(count_);
    psm.setDoneCount(doneCount_.get());
    psm.setHasFinishedAll(hasFinishedAll());
    psm.setHasStartedAll(hasStartedAll());
    
    psm.setPercentDone(getPercentDone());
    psm.setProcessName(processName_);
    
    psm.setRunnablesFinished(runnablesFinished_.get());
    psm.setRunnablesStarted(runnablesStarted_.get());
    psm.setThreadCount(threadCount_);
    psm.setTrials(getTrials());
    
    PhaseState toRet = new PhaseState(psm);
    lastPhaseState_.set(toRet);
    return toRet;
  }
	
	private List<I_Tests4J_TrialProgress> getTrials() {
		List<I_Tests4J_TrialProgress> toRet = new ArrayList<I_Tests4J_TrialProgress>();
		for (I_Tests4J_Runnable runnable: runnables_) {
			I_Tests4J_TrialProgress state = runnable.getTrial();
			if ( state != null && state.getPctDone() < 100.0) {
				toRet.add(state);
			}
		}
		
		return toRet;
	}

	private double getPercentDone() {
		double doneD = doneCount_.get();
		double countD = count_;
		return doneD/countD * 100;
	}

	private void addPhaseState() {
	  PhaseStateMutant psm = new PhaseStateMutant();
	  psm.setCount(count_);
	  psm.setDoneCount(doneCount_.get());
	  psm.setHasFinishedAll(hasFinishedAll());
    psm.setHasStartedAll(hasStartedAll());
    
    psm.setPercentDone(getPercentDone());
	  psm.setProcessName(processName_);
	  
	  psm.setRunnablesFinished(runnablesFinished_.get());
	  psm.setRunnablesStarted(runnablesStarted_.get());
	  psm.setThreadCount(threadCount_);
	  psm.setTrials(getTrials());
	  
	  nextPhaseStates_.add(new PhaseState(psm));
	}
}
