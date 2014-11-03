package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PhaseStateMutant implements I_PhaseState {
  private String processName_;
  private int threadCount_;
  private int count_;
  private int doneCount_;
  private int runnablesStarted_;
  private int runnablesFinished_;
  private boolean hasStartedAll_;
  private boolean hasFinishedAll_;
  private List<I_Tests4J_TrialProgress> trials_ = new ArrayList<I_Tests4J_TrialProgress>();
  private double percentDone_;
  
  public PhaseStateMutant() {
    
  }
  
  public PhaseStateMutant(I_PhaseState info) {
    processName_ = info.getProcessName();
    threadCount_ = info.getThreadCount();
    count_ = info.getCount();
    doneCount_ = info.getDoneCount();
    runnablesStarted_ = info.getRunnablesStarted();
    runnablesFinished_ = info.getRunnablesFinished();
    hasStartedAll_ = info.hasStartedAll();
    hasFinishedAll_ = info.hasFinishedAll();
    setTrials(info.getTrials());
   percentDone_ = info.getPercentDone();
  }
  public String getProcessName() {
    return processName_;
  }
  public void setProcessName(String processName) {
    this.processName_ = processName;
  }
  public int getThreadCount() {
    return threadCount_;
  }
  public void setThreadCount(int threadCount) {
    this.threadCount_ = threadCount;
  }
  public int getCount() {
    return count_;
  }
  public void setCount(int count) {
    this.count_ = count;
  }
  public int getDoneCount() {
    return doneCount_;
  }
  public void setDoneCount(int doneCount) {
    this.doneCount_ = doneCount;
  }
  public int getRunnablesStarted() {
    return runnablesStarted_;
  }
  public void setRunnablesStarted(int runnablesStarted) {
    this.runnablesStarted_ = runnablesStarted;
  }
  public int getRunnablesFinished() {
    return runnablesFinished_;
  }
  public void setRunnablesFinished(int runnablesFinished) {
    this.runnablesFinished_ = runnablesFinished;
  }
  public boolean hasStartedAll() {
    return hasStartedAll_;
  }
  public void setHasStartedAll(boolean hasStartedAll) {
    this.hasStartedAll_ = hasStartedAll;
  }
  public boolean hasFinishedAll() {
    return hasFinishedAll_;
  }
  public void setHasFinishedAll(boolean hasFinishedAll) {
    this.hasFinishedAll_ = hasFinishedAll;
  }
  
  public List<I_Tests4J_TrialProgress> getTrials() {
    return trials_;
  }
  public void setTrials(List<I_Tests4J_TrialProgress> trials) {
    trials_.clear();
    if (trials != null) {
      trials_.addAll(trials);
    }
  }
  public double getPercentDone() {
    return percentDone_;
  }
  public void setPercentDone(double percentDone) {
    this.percentDone_ = percentDone;
  }
}
